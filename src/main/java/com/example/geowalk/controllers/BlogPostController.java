package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogPostRequest;
import com.example.geowalk.models.dto.responses.BlogPostResponse;
import com.example.geowalk.models.dto.responses.BlogPostShortcutResponse;
import com.example.geowalk.services.BlogPostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/blog")
public class BlogPostController {

    private final BlogPostService blogPostService;
    private static final Logger logger = LoggerFactory.getLogger(BlogPostController.class);


    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @PostMapping("/post")
    public void createBlogPost(@RequestBody BlogPostRequest toCreate) {
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
    public Page<BlogPostShortcutResponse> getBlogPostByPageAndSort(@RequestParam int page, @RequestParam int howManyRecord, @RequestParam String column) {
        Page<BlogPostShortcutResponse> returnList = blogPostService.getBlogPostByPageAndSort(page, howManyRecord, column);
        logger.info("User has shown blog posts sorted by column: " + column);
        return returnList;
    }

    @GetMapping("/top-rated")
    public Page<BlogPostShortcutResponse> showBlogPostTopRated(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "3") int howManyRecord) {
        Page<BlogPostShortcutResponse> returnList = blogPostService.getTopRatedBlogPost(page, howManyRecord);
        logger.info("User has shown top rated blog posts");
        return returnList;
    }

    @GetMapping("/travel-stops")
    public Page<BlogPostShortcutResponse> showBlogPostsAboutTravelStop(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int howManyRecord,
                                                                       @RequestParam String country,
                                                                       @RequestParam(required = false) String city,
                                                                       @RequestParam(required = false) String street) {
        Page<BlogPostShortcutResponse> returnList = blogPostService.getBlogPostsAboutTravelStopByName(country, city, street, page, howManyRecord);
        logger.info("User has shown blog posts about travelStop");
        return returnList;
    }

    @GetMapping("/travel-routes")
    public Page<BlogPostShortcutResponse> showAllBlogPostsAboutTravelRouteByTravelStop(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int howManyRecord,
                                                                                       @RequestParam String country,
                                                                                       @RequestParam(required = false) String city,
                                                                                       @RequestParam(required = false) String street) {
        Page<BlogPostShortcutResponse> returnList = blogPostService.getAllBlogPostAboutTravelRouteByTravelStopLocationName(country, city, street, page, howManyRecord);
        logger.info("User has shown blog posts about travelRoute with travelStop");
        return returnList;
    }





    @GetMapping("/titleAndTag")
    public Page<BlogPostShortcutResponse> showAllBlogPostsByTitle(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int howManyRecord,
                                                                  @RequestParam(required = false) List<String> listTags, @RequestParam(required = false) String title) {
        return blogPostService.getAllBlogPostByTitleAndTags(page, howManyRecord, listTags, title);
    }


}
