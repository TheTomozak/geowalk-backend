package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogPostRequest;
import com.example.geowalk.models.dto.responses.BlogPostResponse;
import com.example.geowalk.services.BlogPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/blog")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private static final Logger logger = LoggerFactory.getLogger(BlogPostController.class);


    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/travel-stops")
    public Set<BlogPostResponse> showBlogPostsAboutTravelStop(@RequestParam String location) {
        Set<BlogPostResponse> returnList = blogPostService.findBlogPostAboutTravelStopByName(location);
        logger.info("User has shown blog posts");
        return returnList;
    }

    @GetMapping("/travel-route")
    public Set<BlogPostResponse> showAllBlogPostsAboutTravelRouteByTravelStop(@RequestParam String location) {
        Set<BlogPostResponse> returnList = blogPostService.findAllBlogPostAboutTravelRouteByTravelStopLocationName(location);
        logger.info("User has shown blog posts");
        return returnList;
    }

    @PostMapping("/post")
    public void createBlogPost(@RequestBody BlogPostRequest toCreate){
        blogPostService.createBlogPost(toCreate);
        logger.info("User has created blog post");
    }
}
