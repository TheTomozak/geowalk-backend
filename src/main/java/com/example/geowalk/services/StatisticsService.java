package com.example.geowalk.services;

import com.example.geowalk.models.dto.responses.StatisticsResDto;
import com.example.geowalk.models.repositories.BlogCommentRepo;
import com.example.geowalk.models.repositories.BlogPostRepo;
import com.example.geowalk.models.repositories.TagRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StatisticsService {

    private final BlogPostRepo blogPostRepo;
    private final BlogCommentRepo blogCommentRepo;
    private final TagRepo tagRepo;

    public StatisticsService(BlogPostRepo blogPostRepo, BlogCommentRepo blogCommentRepo, TagRepo tagRepo) {
        this.blogPostRepo = blogPostRepo;
        this.blogCommentRepo = blogCommentRepo;
        this.tagRepo = tagRepo;
    }

    public StatisticsResDto getStatistics() {
        return new StatisticsResDto(blogPostRepo.countBlogPostsByVisibleTrue(),
                                    blogCommentRepo.countBlogCommentByVisibleTrue(),
                                    tagRepo.countTagsByVisibleTrue());
    }
}
