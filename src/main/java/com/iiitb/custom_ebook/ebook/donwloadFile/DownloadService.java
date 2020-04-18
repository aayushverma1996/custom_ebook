package com.iiitb.custom_ebook.ebook.donwloadFile;

import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponents;
import com.iiitb.custom_ebook.ebook.Book.BookComponents.BookComponentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class DownloadService {

    @Autowired
    private BookComponentsService bookComponentsService;

    public List<BookComponents> getRequestedBookComp(String[] requestbookCompId)
    {
        List<BookComponents> generate_bookComponentsList=new ArrayList<BookComponents>();
        for(int i=0;i<requestbookCompId.length;i++)
        {
            generate_bookComponentsList.add(bookComponentsService.getBookComponentbyId(Integer.parseInt(requestbookCompId[i])));
        }

        for(int i=0;i<generate_bookComponentsList.size();i++)
        {
            System.out.println(generate_bookComponentsList.get(i));
        }
        return generate_bookComponentsList;
    }
        public String generate_ebook(List<BookComponents> bookComponents)
        {
            return bookComponentsService.clubDocuments(bookComponents);
        }
}
