package com.iiitb.custom_ebook.ebook.uploadFile;
import com.iiitb.custom_ebook.ebook.Book.Book;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;



    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("id") int id,
                                   @RequestParam("topic") String topic,
                                   @RequestParam("keywords") String keywords
    ) {


            String doc_path=uploadService.uploadNewFile(file);

           if(!doc_path.equals("failure")) {
               Book temp_book = uploadService.getBook(id);

               List<Keywords> keywords_list=uploadService.addKeywords(keywords);

               BookComponents response= uploadService.addNewBookComponents(temp_book, doc_path, topic,keywords_list);


               return "success";

           }

        return "failure";
    }

}

