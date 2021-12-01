package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogPostRequest;
import com.example.geowalk.models.dto.responses.BlogPostResponse;
import com.example.geowalk.models.dto.responses.BlogPostShortcutResponse;
import com.example.geowalk.services.BlogPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/blog")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private static final Logger logger = LoggerFactory.getLogger(BlogPostController.class);


    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("/post")
    public void createBlogPost(@RequestBody BlogPostRequest toCreate){
        blogPostService.createBlogPost(toCreate);
        logger.info("User has created blog post");
    }

    //=======================================================================================================================================


    @GetMapping("{blogPostId}")
    public BlogPostResponse showBlogPostsById(@PathVariable Long blogPostId) {
        BlogPostResponse returnBlogPost = blogPostService.getBlogPostById(blogPostId);
        logger.info("User has shown blog post with id: " + blogPostId);
        return returnBlogPost;
    }

    @GetMapping("")
    public Page<BlogPostShortcutResponse> getBlogPostByPageAndSort(@RequestParam int offset, @RequestParam int pageSize, @RequestParam String column){
        Page<BlogPostShortcutResponse> returnList = blogPostService.getBlogPostByPageAndSort(offset, pageSize, column);
        logger.info("User has shown blog posts sorted by column: "+column);
        return returnList;
    }

    // TODO: 01.12.2021 Jak to połączyc z sortowaniem jeśli nie ma tej columny a jest to atrybut pochodny.
    @GetMapping("/top-rated")
    public List<BlogPostShortcutResponse> showBlogPostTopRated(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int howManyRecord){
        List<BlogPostShortcutResponse> returnList = blogPostService.getTopRatedBlogPost(page, howManyRecord);
        logger.info("User has shown top rated blog posts");
        return returnList;
    }

    @GetMapping("/travel-stops")
    public Set<BlogPostResponse> showBlogPostsAboutTravelStop(@RequestParam String country,
                                                              @RequestParam(required = false) String city,
                                                              @RequestParam(required = false) String street) {
        Set<BlogPostResponse> returnList = blogPostService.getBlogPostsAboutTravelStopByName(country, city, street);
        logger.info("User has shown blog posts about travelStop");
        return returnList;
    }

    @GetMapping("/travel-routes")
    public Set<BlogPostResponse> showAllBlogPostsAboutTravelRouteByTravelStop(@RequestParam String country,
                                                                              @RequestParam(required = false) String city,
                                                                              @RequestParam(required = false) String street) {
        Set<BlogPostResponse> returnList = blogPostService.getAllBlogPostAboutTravelRouteByTravelStopLocationName(country, city, street);
        logger.info("User has shown blog posts about travelRoute with travelStop");
        return returnList;
    }

    @GetMapping("/title")
    public Page<BlogPostResponse> showAllBlogPostsByTitle(@RequestParam int offset, @RequestParam int pageSize, @RequestParam String title){
        return blogPostService.getAllBlogPostByTitle(offset, pageSize, title);
    }

    @GetMapping("/tags")
    public Set<BlogPostResponse> showAllBlogPostsByTags(@RequestParam List<String> tags){
        return blogPostService.getAllBlogPostByTag(tags);
    }
}
