package com.iiitb.custom_ebook.ebook.donwloadFile;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;

import java.util.*;
@RestController
public class DownloadController {

    @Autowired
    private DownloadService downloadService;


    @GetMapping("/file")
//    public void getDownloadPDF(HttpServletRequest request, HttpServletResponse response,
//                               @RequestParam("ids") String ids,@RequestParam("toc") int toc) throws IOException {
    public String getDownloadPDF(@RequestParam("ids") String ids,@RequestParam("toc") int toc) throws IOException {


        String[] ids_generated = ids.split(",");

        List<BookComponents> requested_Components = downloadService.getBookComponents(ids_generated);
        String file_name = downloadService.generate_ebook(requested_Components);

        System.out.println(file_name);
        File file = new File(file_name);
        String final_file=file.getPath();
        if (toc == 1) {
            String toc_generated = downloadService.generate_index(requested_Components);
            final_file=downloadService.merge_toc_main(new File(file_name),new File(toc_generated));
        }


        return final_file;

//        if (file.exists()) {
//
//            if(toc==1)
//            {
//                downloadService.generate_index(requested_Components,no_pages,file);
//            }
//
//            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//            if (mimeType == null) {
//                //unknown mimetype so set the mimetype to application/octet-stream
//                mimeType = "application/octet-stream";
//            }
//            response.setContentType(mimeType);
//
//            response.setHeader("Content-Disposition", String.format("attachment; file_name=\"" + file.getName() + "\""));
//
//            response.setContentLength((int) file.length());
//
//            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
//
//            FileCopyUtils.copy(inputStream, response.getOutputStream());

        }






    }
