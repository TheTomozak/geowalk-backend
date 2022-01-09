package com.example.geowalk.services;

import com.example.geowalk.models.repositories.TagRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TagService {

    private final TagRepo tagRepo;

    public TagService(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }
}
