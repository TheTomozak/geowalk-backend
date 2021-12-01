package com.example.geowalk.services;

import com.example.geowalk.models.entities.Tag;
import com.example.geowalk.models.repositories.TagRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagService {

    private final TagRepo tagRepository;

    public TagService(TagRepo tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findTagByName(String name){
        return tagRepository.findByNameIgnoreCase(name);
    }

    public void saveAll(List<Tag> tagList){
        tagRepository.saveAll(tagList);
    }
}
