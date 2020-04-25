package com.iiitb.custom_ebook.ebook.Book.BookComponents;


import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


@Service
public class BookComponentsService {

    @Autowired
    private BookComponentsRepository bookComponentsRepository;
    @Value("generated")
    private String GENERATED_FOLDER;
    public BookComponents insertNewEntry(BookComponents bookComponents)
    {
        return bookComponentsRepository.save(bookComponents);
    }

    public List<BookComponents> getAllBookComponents()
    {
        Iterable<BookComponents> r=bookComponentsRepository.findAll();
        List<BookComponents> response=new ArrayList<BookComponents>();
        for(BookComponents b:r)
        {
            response.add(b);
        }
        return  response;
    }

    public BookComponents getBookComponentbyId(int id)
    {
        return bookComponentsRepository.findById(id).get();
    }

    public List<BookComponents> getAllBookComponentsKeyword(Keywords keyword)
    {
        return keyword.getBookComponentsList();
    }



    public String clubDocuments(List<BookComponents> bookComponents)
    {
        //to check if existing book is present

        String dir_path = Paths.get("").toAbsolutePath().toString();
        String file_name=Integer.toString(bookComponents.get(0).getId());

        for(int i=1;i<bookComponents.size();i++)
        {
            file_name+="_"+bookComponents.get(i).getId();
        }
        file_name=dir_path + File.separator + GENERATED_FOLDER + File.separator + file_name+".pdf";

        System.out.println(file_name);

        File f = new File(file_name);
        if(f.exists())
        {
            //System.out.println("here");
            return f.getAbsolutePath();
        }

        //merging logic

        PDFMergerUtility merge=new PDFMergerUtility();
        merge.setDestinationFileName(file_name);

        try {
            for (int i = 0; i < bookComponents.size(); i++) {

                File f1 = new File(bookComponents.get(i).getLocation());
                merge.addSource(f1);


            }
            merge.mergeDocuments(null);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //System.out.println("merge successfully");
        generate_page_numbers(file_name);
        return file_name;
    }

    public void generate_page_numbers(String file_name)
    {
        File load_file=new File(file_name);
        try {
            PDDocument doc = PDDocument.load(load_file);
            int page_number=1;
            for (PDPage page:doc.getPages())
            {
                PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.APPEND, true, false);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER, 10);
                PDRectangle pageSize = page.getCropBox();
                float x = pageSize.getLowerLeftX();
                float y = pageSize.getLowerLeftY();
                contentStream.newLineAtOffset(x+ pageSize.getWidth()-60, y+20);
                //System.out.println("page"+page_number);
                contentStream.showText(Integer.toString(page_number));
                contentStream.endText();
                contentStream.close();
                ++page_number;
            }

            doc.save(load_file);

        }catch(IOException e)
        {
            e.printStackTrace();
        }
    }


}

