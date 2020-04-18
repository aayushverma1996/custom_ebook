package com.iiitb.custom_ebook.ebook.donwloadFile;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.*;
@RestController
public class DownloadController {

    @Autowired
    private DownloadService downloadService;


    @GetMapping("/file")
    public void getDownloadPDF(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam("ids") String ids) throws IOException {

        String[] ids_generated = ids.split(",");

        List<BookComponents> requested_Components = downloadService.getRequestedBookComp(ids_generated);
        String file_name = downloadService.generate_ebook(requested_Components);

        System.out.println(file_name);
        File file = new File(file_name);


        if (file.exists()) {


            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                //unknown mimetype so set the mimetype to application/octet-stream
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            response.setHeader("Content-Disposition", String.format("attachment; file_name=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());

        }


    }



}
