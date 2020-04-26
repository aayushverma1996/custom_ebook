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

    public List<BookComponents> getRequestedBookComp(String[] requestbookCompId)
    {
        List<BookComponents> generate_bookComponentsList=new ArrayList<BookComponents>();
        for(int i=0;i<requestbookCompId.length;i++)
        {
            generate_bookComponentsList.add(bookComponentsRepository.findById(Integer.parseInt(requestbookCompId[i])).get());
        }

        for(int i=0;i<generate_bookComponentsList.size();i++)
        {
            System.out.println(generate_bookComponentsList.get(i));
        }
        return generate_bookComponentsList;
    }




}

