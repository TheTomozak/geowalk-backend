package com.example.geowalk.utils;

import com.example.geowalk.models.repositories.SwearWordRepo;
import com.example.geowalk.services.BlogCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SwearWordsFilter {

    private static final Logger logger = LoggerFactory.getLogger(SwearWordsFilter.class);
    private final SwearWordRepo swearWordRepo;
    private final Map<String, Object> allSwearWords = new HashMap<>();

    public SwearWordsFilter(SwearWordRepo swearWordRepo) {
        this.swearWordRepo = swearWordRepo;
    }

    public SwearWordRepo getSwearWordRepo() {
        return swearWordRepo;
    }

    public Map<String, Object> getAllSwearWords() {
        return allSwearWords;
    }

    public void loadSwearWordsFromDatabase() {
        swearWordRepo.findAll().forEach(swearWord -> allSwearWords.put(swearWord.getValue(), null));
    }

    public boolean hasSwearWord(String content) {
        for (String swearWord : allSwearWords.keySet()) {
            if(content.toLowerCase().contains(swearWord)) {
                logger.info("Swear word found - {}", swearWord);
                return true;
            }
        }
        return false;
    }

}
