package com.example.geowalk.services;

import com.example.geowalk.models.dto.responses.TagResDto;
import com.example.geowalk.models.entities.Tag;
import com.example.geowalk.models.repositories.TagRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagService {

    private static final Logger logger = LoggerFactory.getLogger(TagService.class);
    private final TagRepo tagRepo;

    private final ModelMapper mapper;

    public TagService(TagRepo tagRepo, ModelMapper mapper) {
        this.mapper = mapper;
        this.tagRepo = tagRepo;
    }

    public Tag getOrCreateTag(String name, List<Tag> oldTags) {
        Optional<Tag> tag = tagRepo.findByNameIgnoreCase(name);
        if (tag.isEmpty()) {
            Tag newTag = new Tag(name);
            tagRepo.save(newTag);
            logger.info("Created new tag with name - {}", name);
            return newTag;
        } else {
            if(!oldTags.contains(tag.get())){
                tag.get().setOccurrenceNumber(tag.get().getOccurrenceNumber()+1);
            }
            return tag.get();
        }
    }

    public List<TagResDto> getAllTags() {
        List<Tag> sortedTags = tagRepo.findAllByOrderByOccurrenceNumberDesc();
        return sortedTags.stream()
                .map(tag -> mapper.map(tag, TagResDto.class))
                .collect(Collectors.toList());
    }
}
