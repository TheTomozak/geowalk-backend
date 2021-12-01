package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogCommentReqDto;
import com.example.geowalk.models.entities.BlogComment;
import com.example.geowalk.services.BlogCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comment")
public class BlogCommentController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final BlogCommentService blogCommentService;

    @Autowired
    public BlogCommentController(BlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @GetMapping("/{blogPostId}")
    public List<BlogComment> getBlogComments(@PathVariable("blogPostId") long blogId) {
        return null;
    }

    @GetMapping("/{blogCommentId}")
    public BlogComment getBlogComment(@PathVariable("blogCommentId") long blogCommentId) {
        return null;
    }

    @PostMapping("/{blogPostId}")
    public void createBlogComment(@PathVariable("blogPostId") long blogPostId, @RequestBody BlogCommentReqDto request) {

    }

    @PutMapping("/{blogCommentId}")
    public void updateBlogComment(@PathVariable("blogCommentId") long blogCommentId, @RequestBody BlogCommentReqDto request) {

    }

    @DeleteMapping("/{blogCommentId}")
    public void deleteBlogComment(@PathVariable("blogCommentId") long blogCommentId) {
        logger.info(String.format("BlogCommentController DELETE[api/comment/%s] Deleting comment with id >>\t%s\t<<", blogCommentId, blogCommentId));
        blogCommentService.deleteBlogComment(blogCommentId);
    }
}
