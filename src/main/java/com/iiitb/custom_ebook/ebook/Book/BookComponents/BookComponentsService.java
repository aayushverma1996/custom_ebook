package com.iiitb.custom_ebook.ebook.Book.BookComponents;


import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
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
        return file_name;
    }
}

