package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.responses.TagResDto;
import com.example.geowalk.services.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tags")
public class TagController {

    private static final Logger logger = LoggerFactory.getLogger(TagController.class);
    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagResDto> getAllTags() {
        logger.info("GET [api/tags] Getting all tags sorted by occurrence number");
        return tagService.getAllTags();
    }
}
