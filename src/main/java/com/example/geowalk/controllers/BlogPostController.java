package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogPostReqDto;
import com.example.geowalk.models.dto.requests.BlogPostVerificationReqDto;
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
    public Page<BlogPostShortResDto> getBlogPosts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "3") int size) {
        logger.info("GET[api/blogs] Getting {} blog posts on page {}", size, page);
        return blogPostService.getBlogPosts(page, size);
    }

    @GetMapping("/top-rated")
    public Page<BlogPostShortResDto> getBlogPostsTopRated(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        logger.info("GET[api/blogs/top-rated] Getting {} top rated blog posts on page {}", size, page);
        return blogPostService.getBlogPostsTopRated(page, size);
    }

    @GetMapping("/travel-stop")
    public Page<BlogPostShortResDto> getBlogPostsRelatedToTravelStop(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "5") int size,
                                                                     @RequestParam String country,
                                                                     @RequestParam(required = false) String city,
                                                                     @RequestParam(required = false) String street) {
        logger.info("GET[api/blogs/travel-stop] Getting {} blog posts on page {} related to travel stop {}", size, page, country);
        return blogPostService.getBlogPostsRelatedToTravelStop(country, city, street, page, size);
    }

    @GetMapping("/travel-routes")
    public Page<BlogPostShortResDto> getBlogPostsRelatedToTravelRouteWithTravelStop(@RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "5") int size,
                                                                                    @RequestParam String country,
                                                                                    @RequestParam(required = false) String city,
                                                                                    @RequestParam(required = false) String street) {
        logger.info("GET[api/blogs/travel-routes] Getting {} blog posts on page {} related to travel route with travel stop {}", size, page, country);
        return blogPostService.getBlogPostsRelatedToTravelRouteWithTravelStop(country, city, street, page, size);
    }


    @GetMapping("/search")
    public Page<BlogPostShortResDto> getBlogPostsBySearchParam(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size,
                                                               @RequestParam String searchValue) {
        logger.info("GET[api/blogs/search] Getting {} blog posts on page {} found by keyword {}", size, page, searchValue);
        return blogPostService.getBlogPostsBySearchParam(page, size, searchValue);
    }

    @GetMapping("/latest")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BlogPostShortResDto getLatestBlogPost() {
        logger.info("GET[api/blogs/latest] Getting latest blog post");
        return blogPostService.getLatestBlogPost();
    }

    @GetMapping("/{blogPostId}")
    public BlogPostResDto getBlogPost(@PathVariable("blogPostId") Long blogPostId) {
        logger.info("GET[api/blogs/{}] Getting blog post with id {}", blogPostId, blogPostId);
        return blogPostService.getBlogPost(blogPostId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    public void createBlogPost(@RequestBody BlogPostReqDto request) {
        logger.info("POST[api/blogs] Creating blog post with title {}", request.getTitle());
        blogPostService.createBlogPost(request);
    }

    /*
     ***********************
     * Moderator endpoints *
     ***********************
     */
    @GetMapping("/verify")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Page<BlogPostShortResDto> getBlogPostsToVerify(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "3") int size) {
        logger.info("GET[api/blogs/verify] Getting posts to verify");
        return blogPostService.getBlogPostsToVerify(page, size);
    }

    @PostMapping("/verify")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public void verifyBlogPost(@RequestBody BlogPostVerificationReqDto request) {
        logger.info("POST[api/blogs/verify] Verifying post with id {}", request.getBlogPostId());
        blogPostService.verifyBlogPost(request);
    }
}
