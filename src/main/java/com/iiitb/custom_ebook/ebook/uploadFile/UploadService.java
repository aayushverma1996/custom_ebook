package com.iiitb.custom_ebook.ebook.uploadFile;

import com.iiitb.custom_ebook.ebook.Book.*;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadService {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookComponentsService bookComponentsService;

    //Save the uploaded file to this folder
    @Value("docs")
    private String UPLOADED_FOLDER ;

    public Book getBook(int id){
        return bookService.getBookbyId(id);
    }


    public String addNewBookComponents(Book book,String doc_path,String topic)
    {
        BookComponents newbookComponents=new BookComponents();
        newbookComponents.setComponent_name(topic);
        newbookComponents.setLocation(doc_path);
        newbookComponents.setBook(book);
        bookComponentsService.insertNewEntry(newbookComponents);
        return "success";
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
}
