package com.iiitb.custom_ebook.ebook.Book.Keywords;

import com.iiitb.custom_ebook.ebook.Book.Keywords.Keywords;
import com.iiitb.custom_ebook.ebook.Book.Keywords.KeywordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class KeywordsService {

    @Autowired
    private KeywordsRepository keywordsRepository;

    public List<Keywords> saveKeywords(List<String> keywordsbyname)
    {
       List<Keywords> keywords=new ArrayList<Keywords>();
       for(int i=0;i<keywordsbyname.size();i++)
       {
           Keywords tempkeyword= new Keywords();
           tempkeyword.setKeyword(keywordsbyname.get(i));
           keywords.add( tempkeyword);

       }
       //add here method to map list of keywords to book

       List<Keywords> response= (List<Keywords>)keywordsRepository.saveAll(keywords);
        return response;
    }

    public Keywords getSpecificKeyword(String keywordname)
    {
        Keywords response_keyword=keywordsRepository.findByKeyword(keywordname);
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
