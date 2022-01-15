package com.example.geowalk.services;

import com.example.geowalk.controllers.BlogPostController;
import com.example.geowalk.models.entities.Tag;
import com.example.geowalk.models.repositories.TagRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);
    private final TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    public Tag getOrCreateTag(String name) {
        Optional<Tag> tag = tagRepo.findByNameIgnoreCase(name);
        if (tag.isEmpty()) {
            Tag newTag = new Tag(name);
            tagRepo.save(newTag);
            logger.info("Created new tag with name - {}", name);
            return newTag;
        } else {
            return tag.get();
        }
    }
}
