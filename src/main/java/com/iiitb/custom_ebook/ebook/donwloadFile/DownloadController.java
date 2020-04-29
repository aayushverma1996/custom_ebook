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
                               @RequestParam("ids") String ids, @RequestParam("toc") int toc) throws IOException {
        //   public String getDownloadPDF(@RequestParam("ids") String ids,@RequestParam("toc") int toc) throws IOException {


        String[] ids_generated = ids.split(",");

        List<BookComponents> requested_Components = downloadService.getBookComponents(ids_generated);
        String file_name = downloadService.generate_ebook(requested_Components);

        System.out.println(file_name);
        File file = new File(file_name);
        String final_file = file.getPath();
        if (toc == 1) {
            String toc_generated = downloadService.generate_index(requested_Components);
            final_file = downloadService.merge_toc_main(new File(file_name), new File(toc_generated));
        }
        File final_file_response=new File(final_file);

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
