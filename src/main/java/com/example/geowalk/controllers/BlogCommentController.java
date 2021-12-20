package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogCommentReqDto;
import com.example.geowalk.models.dto.responses.BlogCommentResponse;
import com.example.geowalk.models.entities.BlogComment;
import com.example.geowalk.services.BlogCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/comment")
public class BlogCommentController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final BlogCommentService blogCommentService;

    @Autowired
    public BlogCommentController(BlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @GetMapping("/{blogPostId}")
    public List<BlogCommentResponse> getBlogComments(@PathVariable("blogPostId") long blogId) {
        logger.info(String.format("BlogCommentController GET[api/comment/%s] Getting comments related to blogPost with id >>\t%s\t<<", blogId, blogId));
        return blogCommentService.getBlogComments(blogId);
    }

    @PostMapping("/{blogPostId}")
    public void createBlogComment(@PathVariable("blogPostId") long blogPostId, @RequestBody BlogCommentReqDto request) {
        logger.info(String.format("BlogCommentController POST[api/comment/%s] Creating comment related to blogPost with id >>\t%s\t<<", blogPostId, blogPostId));
        blogCommentService.createBlogComment(blogPostId, request);
    }

    @PutMapping("/{blogCommentId}")
    public void updateBlogComment(@PathVariable("blogCommentId") long blogCommentId, @RequestBody BlogCommentReqDto request) {
        logger.info(String.format("BlogCommentController PUT[api/comment/%s] Updating comment with id >>\t%s\t<<", blogCommentId, blogCommentId));
        blogCommentService.updateBlogComment(blogCommentId, request);
    }

    @DeleteMapping("/{blogCommentId}")
    public void deleteBlogComment(@PathVariable("blogCommentId") long blogCommentId) {
        logger.info(String.format("BlogCommentController DELETE[api/comment/%s] Deleting comment with id >>\t%s\t<<", blogCommentId, blogCommentId));
        blogCommentService.deleteBlogComment(blogCommentId);
    }
}
