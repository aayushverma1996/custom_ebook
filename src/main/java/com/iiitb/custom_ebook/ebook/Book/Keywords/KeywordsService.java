package com.iiitb.custom_ebook.ebook.Book.Keywords;

import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import com.iiitb.custom_ebook.ebook.Book.Keywords.KeywordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Service
public class KeywordsService {

    @Autowired
    private KeywordsRepository keywordsRepository;

    public List<Keywords> saveKeywords(String[] keywordsbyname)
    {
       List<Keywords> keywords=new ArrayList<Keywords>();
       List<Keywords> final_response = new ArrayList<Keywords>();

       for(int i=0;i<keywordsbyname.length;i++)
       {
           Optional<Keywords> result=keywordsRepository.findByKeyword(keywordsbyname[i].toLowerCase());
           if(result.isPresent())
               {
                   final_response.add(result.get());
               }
        else {
               Keywords temp_keyword = new Keywords();
               temp_keyword.setKeyword(keywordsbyname[i].toLowerCase());
               keywords.add(temp_keyword);
           }
       }
        //add here method to map list of keywords to book


        List<Keywords>  response = (List<Keywords>) keywordsRepository.saveAll(keywords);

        final_response.addAll(response);

        return final_response;

    }

    public Keywords getSpecificKeyword(String keywordname)
    {
        Keywords response_keyword=keywordsRepository.findByKeyword(keywordname).get();
        return response_keyword;
    }

        public List<Keywords> getAllKeywords()
        {
            List<Keywords> response=new ArrayList<Keywords>();
            Iterable<Keywords> itr=keywordsRepository.findAll();
            for (Keywords k:itr)
            {
                response.add(k);
            }
            return response;
        }
}
