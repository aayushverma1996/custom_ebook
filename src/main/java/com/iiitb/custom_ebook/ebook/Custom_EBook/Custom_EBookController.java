package com.iiitb.custom_ebook.ebook.Custom_EBook;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URLConnection;
import java.util.*;
@CrossOrigin("*")
@RestController
public class Custom_EBookController {

    @Autowired
    private Custom_EBookService customEBookService;


    @GetMapping("/file")
    public String getDownloadPDF(@RequestParam("ids") String ids,
                               @RequestParam("toc") int toc,
                               @RequestParam(name="ebookname") String ebookname,
                               @RequestParam(name="generatedby") String generatedby,
                               @RequestParam("userid") int uid) throws IOException {



        String[] ids_generated = ids.split(",");
        List<BookComponents> requested_Components = customEBookService.getBookComponents(Arrays.asList(ids_generated));
//        String file_name=null;
//        File file = new File(file_name);
//        String final_file = file.getPath();
      //  if (toc != 1) {
//
//            file_name = customEBookService.generate_ebook(requested_Components,uid);
//            File file_temp = new File(file_name);
//            if(file_temp.exists()) {
               customEBookService.addToUser(uid, requested_Components,toc,ebookname,generatedby);

        //    }

//        //}
//        //else
//        //{
//            file_name = customEBookService.generate_ebook(requested_Components,uid);
//            String toc_generated = customEBookService.generate_index(requested_Components,ebookname,generatedby);
//            final_file = customEBookService.merge_toc_main(new File(file_name), new File(toc_generated));
//        }
//
//        File final_file_response=new File(final_file);
//
//        if (final_file_response.exists()) {
//
//
//            String mimeType = URLConnection.guessContentTypeFromName(final_file_response.getName());
//            if (mimeType == null) {
//                //unknown mimetype so set the mimetype to application/octet-stream
//                mimeType = "application/octet-stream";
//            }
//            response.setContentType(mimeType);
//
//            response.setHeader("Content-Disposition", String.format("attachment; file_name=\"" + final_file_response.getPath() + "\""));
//
//            response.setContentLength((int) final_file_response.length());
//
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(final_file_response));
//
//            FileCopyUtils.copy(inputStream, response.getOutputStream());
//
//        }

    return "Success";
    }

    @GetMapping("/purchase")
    public String purchase(@RequestParam("ebookid") int ebookId) throws IOException {
        Custom_EBook eBook=customEBookService.getEBookbyId(ebookId);
       return customEBookService.buildEBook(eBook);

    }

    @GetMapping("/download")
    public void Download(HttpServletRequest request, HttpServletResponse response,@RequestParam("ebookid") int ebookId) throws IOException {

        Custom_EBook eBook=customEBookService.getEBookbyId(ebookId);
        File final_file_response=new File(eBook.getLocation());

        if (final_file_response.exists()) {


            String mimeType = URLConnection.guessContentTypeFromName(final_file_response.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            response.setHeader("Content-Disposition", String.format("attachment; file_name=\"" + final_file_response.getPath() + "\""));

            response.setContentLength((int) final_file_response.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(final_file_response));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        }

    }

}
