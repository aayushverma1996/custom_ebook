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
    private String UPLOADED_FOLDER ;

    public Book getBook(int id){
        return bookService.getBookbyId(id);
    }


    public BookComponents addNewBookComponents(Book book,String doc_path,String topic,List<Keywords> keywords)
    {
        BookComponents newbookComponents=new BookComponents();
        newbookComponents.setComponent_name(topic);
        newbookComponents.setLocation(doc_path);
        newbookComponents.setBook(book);
        newbookComponents.setKeywordsList(keywords);
        return bookComponentsService.insertNewEntry(newbookComponents);

    }

    public String uploadNewFile(MultipartFile file) {

        String doc_path=UPLOADED_FOLDER;
        try {
            // Get the file and save it somewhere

            File dir = new File(UPLOADED_FOLDER);

            if (!dir.exists()) {
                dir.mkdir();
            }

            byte[] bytes = file.getBytes();
            String dir_path = Paths.get("").toAbsolutePath().toString();


            doc_path = dir_path + File.separator + dir + File.separator + file.getOriginalFilename();
            Path path = Paths.get(doc_path);

            System.out.println(doc_path);
            Files.write(path, bytes);

        } catch (IOException e) {


            e.printStackTrace();
            return "failure";
        }
        return doc_path;
    }

    public List<Keywords> addKeywords(String keywords)
    {
        String[] keywords_generated=keywords.split(",");

       List<Keywords> response= keywordsService.saveKeywords(keywords_generated);



       return response;

    }
}
