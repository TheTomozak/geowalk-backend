package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.BlogCommentReqDto;
import com.example.geowalk.models.dto.requests.BlogCommentVerificationReqDto;
import com.example.geowalk.models.dto.responses.BlogCommentResDto;
import com.example.geowalk.models.dto.responses.BlogPostResDto;
import com.example.geowalk.services.BlogCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/comments")
public class BlogCommentController {

    private static final Logger logger = LoggerFactory.getLogger(BlogCommentController.class);
    private final BlogCommentService blogCommentService;

    @Autowired
    public BlogCommentController(BlogCommentService blogCommentService) {
        this.blogCommentService = blogCommentService;
    }

    @GetMapping("/{blogPostId}")
    public List<BlogCommentResDto> getBlogComments(@PathVariable("blogPostId") long blogId) {
        logger.info("GET[api/comments/{}] Getting comments related to blogPost with id {}", blogId, blogId);
        return blogCommentService.getBlogComments(blogId);
    }

    @GetMapping("/latest")
    @PreAuthorize("hasAuthority('ADMIN')")
    public BlogCommentResDto getLatestBlogComment() {
        logger.info("GET[api/comments/latest] Getting latest comment");
        return blogCommentService.getLatestBlogComment();
    }

    @PostMapping("/{blogPostId}")
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    public void createBlogComment(@PathVariable("blogPostId") long blogPostId, @RequestBody BlogCommentReqDto request) {
        logger.info("POST[api/comments/{}] Creating comment related to blogPost with id {}", blogPostId, blogPostId);
        blogCommentService.createBlogComment(blogPostId, request);
    }

    @PutMapping("/{blogCommentId}")
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    public void updateBlogComment(@PathVariable("blogCommentId") long blogCommentId, @RequestBody BlogCommentReqDto request) {
        logger.info("PUT[api/comments/{}] Updating comment with id {}", blogCommentId, blogCommentId);
        blogCommentService.updateBlogComment(blogCommentId, request);
    }

    @DeleteMapping("/{blogCommentId}")
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    public void deleteBlogComment(@PathVariable("blogCommentId") long blogCommentId) {
        logger.info("DELETE[api/comments/{}] Deleting comment with id {}", blogCommentId, blogCommentId);
        blogCommentService.deleteBlogComment(blogCommentId);
    }

    /*
     ***********************
     * Moderator endpoints *
     ***********************
     */

    @GetMapping("/verify")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public List<BlogCommentResDto> getBlogCommentsToVerify() {
        logger.info("GET[api/comments/verify] Getting comments to verify");
        return blogCommentService.getBlogCommentsToVerify();
    }

    @PostMapping("/verify")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public void verifyBlogComment(@RequestBody BlogCommentVerificationReqDto request) {
        logger.info("POST[api/comments/verify] Verifying comment with id {}", request.getBlogCommentId());
        blogCommentService.verifyBlogComment(request);
    }
}
