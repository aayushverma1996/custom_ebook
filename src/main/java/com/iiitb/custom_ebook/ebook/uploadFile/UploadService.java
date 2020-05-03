package com.iiitb.custom_ebook.ebook.uploadFile;

import com.iiitb.custom_ebook.ebook.Book.*;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponentsService;
import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import com.iiitb.custom_ebook.ebook.Book.Keywords.KeywordsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;

@Service
public class UploadService {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookComponentsService bookComponentsService;

    @Autowired
    private KeywordsService keywordsService;

    //Save the uploaded file to this folder
    @Value("docs")
    private String DOCS_FOLDER;

    @Value("uploaded")
    private String UPLOADED_FOLDER;


    public Book getBook(int id) {
        return bookService.getBookbyId(id);
    }


    public BookComponents addNewBookComponents(Book book, String doc_path, String topic, List<Keywords> keywords,double price) {
        BookComponents newbookComponents = new BookComponents();
        newbookComponents.setComponent_name(topic);
        newbookComponents.setLocation(doc_path);
        newbookComponents.setBook(book);
        newbookComponents.setKeywordsList(keywords);
        newbookComponents.setPrice(price);
        return bookComponentsService.insertNewEntry(newbookComponents);

    }

    public String uploadNewFile(MultipartFile file, Book book) {


        String upload_book_path = DOCS_FOLDER;
        try {
            // Get the file and save it by creating docs/uploaded/bookname path
            byte[] bytes = file.getBytes();

            Path path = null;

            String current_dir_path = Paths.get("").toAbsolutePath().toString();

            upload_book_path = current_dir_path + File.separator
                    + DOCS_FOLDER + File.separator
                    + UPLOADED_FOLDER + File.separator
                    + book.getBook_name() + File.separator;

            System.out.println("Upload Book Path String : " + upload_book_path);
            File create_directory = new File(upload_book_path);
            if (!create_directory.exists()) {
                if (create_directory.mkdirs()) {
                    path = Paths.get(upload_book_path + file.getOriginalFilename());
                    System.out.println("New Directory Created, File Created At Path :" + path);
                    Files.write(path, bytes);
                } else {
                    System.out.println("Failed to create directory!");
                    System.out.println("So Must not write the file");
                    return "failure";
                }
            } else {
                path = Paths.get(upload_book_path + file.getOriginalFilename());
                System.out.println("Directory already existed, File Created At Path :" + path);
                Files.write(path, bytes);
            }

        } catch (IOException e) {


            e.printStackTrace();
            return "failure";
        }

        return upload_book_path + file.getOriginalFilename();

    }

    public List<Keywords> addKeywords(String keywords) {
        String[] keywords_generated = keywords.split(",");

        List<Keywords> response = keywordsService.saveKeywords(keywords_generated);
        return response;

    }
}



