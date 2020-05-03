package com.iiitb.custom_ebook.ebook.Book.Keywords;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KeywordsService {

    @Autowired
    private KeywordsRepository keywordsRepository;

    public List<Keywords> saveKeywords(String[] keywordsbyname)
    {

        Arrays.asList(keywordsbyname).parallelStream().filter(x->!keywordsRepository.findByKeyword(x).isPresent())
                .map(x->new Keywords(x.toLowerCase())).forEach(x->keywordsRepository.save(x));


        List<Keywords> response=Arrays.asList(keywordsbyname).parallelStream().map(x->keywordsRepository.findByKeyword(x).
                get()).collect(Collectors.toList());

        return response;

    }
         public List<Keywords> getAllKeywords()
        {

            List<Keywords> response=new ArrayList<Keywords>();
            Iterable<Keywords> itr=keywordsRepository.findAll();
                itr.forEach(response::add);
              return response;
        }

    public Keywords getSpecificKeyword(String keywordname)
    {

        return keywordsRepository.getComponents(keywordname).get();

    }


}
