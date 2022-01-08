package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogPostReqDto;
import com.example.geowalk.models.dto.responses.BlogPostResDto;
import com.example.geowalk.models.dto.responses.BlogPostShortResDto;
import com.example.geowalk.services.BlogPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/blogs")
public class BlogPostController {

    private static final Logger logger = LoggerFactory.getLogger(BlogPostController.class);
    private final BlogPostService blogPostService;

    @Autowired
    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }


    @GetMapping
    public Page<BlogPostShortResDto> getBlogPostsByPageAndSort(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int howManyRecord,
                                                               @RequestParam(defaultValue = "creationDateTime") String column) {
        logger.info("GET[api/blogs] Getting {} blog posts on page {} and sorted by column {}", howManyRecord, page, column);
        return blogPostService.getBlogPostsByPageAndSort(page, howManyRecord, column);
    }

    @GetMapping("/top-rated")
    public Page<BlogPostShortResDto> showBlogPostsTopRated(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "3") int howManyRecord) {
        logger.info("GET[api/blogs/top-rated] Getting {} top rated blog posts on page {}", howManyRecord, page);
        return blogPostService.getTopRatedBlogPosts(page, howManyRecord);
    }

    @GetMapping("/travel-stop")
    public Page<BlogPostShortResDto> showBlogPostsAboutTravelStop(@RequestParam(defaultValue = "0") int page,
                                                                  @RequestParam(defaultValue = "5") int howManyRecord,
                                                                  @RequestParam String country,
                                                                  @RequestParam(required = false) String city,
                                                                  @RequestParam(required = false) String street) {
        logger.info("GET[api/blogs/travel-stop] Getting {} blog posts on page {} related to travel stop {}", howManyRecord, page, country);
        return blogPostService.getBlogPostsAboutTravelStopByName(country, city, street, page, howManyRecord);
    }

    @GetMapping("/travel-routes")
    public Page<BlogPostShortResDto> showAllBlogPostsAboutTravelRouteByTravelStop(@RequestParam(defaultValue = "0") int page,
                                                                                  @RequestParam(defaultValue = "5") int howManyRecord,
                                                                                  @RequestParam String country,
                                                                                  @RequestParam(required = false) String city,
                                                                                  @RequestParam(required = false) String street) {
        logger.info("GET[api/blogs/travel-routes] Getting {} blog posts on page {} related to travel route with travel stop {}", howManyRecord, page, country);
        return blogPostService.getAllBlogPostAboutTravelRouteByTravelStopLocationName(country, city, street, page, howManyRecord);
    }


    @GetMapping("/titleAndTag")
    public Page<BlogPostShortResDto> showAllBlogPostsByTitle(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "5") int howManyRecord,
                                                             @RequestParam(required = false) List<String> listTags,
                                                             @RequestParam(required = false) String title) {
        logger.info("GET[api/blogs/titleAndTag] Getting {} blog posts on page {} related to title {} or tags {}", howManyRecord, page, title, listTags);
        return blogPostService.getAllBlogPostByTitleAndTags(page, howManyRecord, listTags, title);
    }

    @GetMapping("/{blogPostId}")
    public BlogPostResDto showBlogPostsById(@PathVariable("blogPostId") Long blogPostId) {
        logger.info("GET[api/blogs/{}] Getting blog post with id {}", blogPostId, blogPostId);
        return blogPostService.getBlogPostById(blogPostId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    public void createBlogPost(@RequestBody BlogPostReqDto request) {
        logger.info("POST[api/blogs] Creating blog post with title {}", request.getTitle());
        blogPostService.createBlogPost(request);
    }
}
