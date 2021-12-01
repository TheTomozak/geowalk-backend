package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.ForbiddenException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.exceptions.UnauthorizedException;
import com.example.geowalk.models.dto.requests.BlogCommentReqDto;
import com.example.geowalk.models.dto.responses.BlogCommentResponse;
import com.example.geowalk.models.dto.responses.UserResDto;
import com.example.geowalk.models.entities.BlogComment;
import com.example.geowalk.models.entities.BlogPost;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.enums.Role;
import com.example.geowalk.models.repositories.BlogCommentRepo;
import com.example.geowalk.models.repositories.BlogPostRepo;
import com.example.geowalk.models.repositories.UserRepo;
import com.example.geowalk.utils.ISessionUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BlogCommentService {

    private static final Logger logger = LoggerFactory.getLogger(BlogCommentService.class);
    private final BlogCommentRepo blogCommentRepo;
    private final BlogPostRepo blogPostRepo;
    private final UserRepo userRepo;
    private ModelMapper mapper;
    private final ISessionUtil sessionUtil;

    private final String BLOG_COMMENT_NOT_FOUND = "Blog comment with given id not found";
    private final String USER_NOT_AUTHORIZED = "User is not authenticated";
    private final String USER_BLOCKED_OR_DELETED = "User is deleted/blocked";
    private final String COMMENT_NOT_WRITTEN_BY_THIS_USER = "User cannot delete comment others users";
    private final String BLOGPOST_NOT_FOUND = "BlogPost with given id not found";
    private final String INVALID_RATING_VALUE = "Rating value is invalid";

    @Autowired
    public BlogCommentService(BlogCommentRepo blogCommentRepo, BlogPostRepo blogPostRepo, UserRepo userRepo, ISessionUtil sessionUtil) {
        this.blogCommentRepo = blogCommentRepo;
        this.blogPostRepo = blogPostRepo;
        this.userRepo = userRepo;
        this.sessionUtil = sessionUtil;
        mapper = new ModelMapper();
    }

    public List<BlogCommentResponse> getBlogComments(long blogPostId) {
        Optional<BlogPost> blogPost = blogPostRepo.findById(blogPostId);
        if(blogPost.isEmpty()) {
            throw new NotFoundException(BLOGPOST_NOT_FOUND);
        }
        List<BlogCommentResponse> result = new ArrayList<>();
        for (BlogComment blogComment : blogPost.get().getBlogComments()) {
            if(blogComment.isVisible()) {
                UserResDto blogCommentUser = mapper.map(blogComment.getUser(), UserResDto.class);
                BlogCommentResponse blogCommentResponse = mapper.map(blogComment, BlogCommentResponse.class);
                blogCommentResponse.setUser(blogCommentUser);
                result.add(blogCommentResponse);
            }
        }

        return result;
    }

    public void createBlogComment(long blogPostId, BlogCommentReqDto request) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if(loggedUserUsername.isEmpty()) {
            logger.error("Creating comment failed:\t"+USER_NOT_AUTHORIZED);
            throw new UnauthorizedException(USER_NOT_AUTHORIZED);
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if(loggedUser.isEmpty()) {
            logger.error("Creating comment failed:\t"+USER_BLOCKED_OR_DELETED);
            throw new NotFoundException(USER_BLOCKED_OR_DELETED);
        }

        Optional<BlogPost> blogPost = blogPostRepo.findByIdAndVisibleTrue(blogPostId);
        if(blogPost.isEmpty()) {
            logger.error("Creating comment failed:\t"+BLOGPOST_NOT_FOUND);
            throw new NotFoundException(BLOGPOST_NOT_FOUND);
        }

        if(request.getRating() < 1 || request.getRating() > 5) {
            logger.error("Creating comment failed:\t"+INVALID_RATING_VALUE);
            throw new BadRequestException(INVALID_RATING_VALUE);
        }
        BlogComment blogComment = mapper.map(request, BlogComment.class);
        blogComment.setUser(loggedUser.get());
        blogComment.setBlogPost(blogPost.get());
        blogCommentRepo.save(blogComment);
        logger.info("Creating comment success");
    }

    public void deleteBlogComment(long blogCommentId) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if(loggedUserUsername.isEmpty()) {
            logger.error("Deleting comment failed:\t"+USER_NOT_AUTHORIZED);
            throw new UnauthorizedException(USER_NOT_AUTHORIZED);
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if(loggedUser.isEmpty()) {
            logger.error("Deleting comment failed:\t"+USER_BLOCKED_OR_DELETED);
            throw new NotFoundException(USER_BLOCKED_OR_DELETED);
        }

        Optional<BlogComment> blogComment = blogCommentRepo.findById(blogCommentId);
        if(blogComment.isEmpty()) {
            logger.error("Deleting comment failed:\t"+BLOG_COMMENT_NOT_FOUND);
            throw new NotFoundException(BLOG_COMMENT_NOT_FOUND);
        }

        if(!blogComment.get().getUser().equals(loggedUser.get()) || !loggedUser.get().getRole().equals(Role.ADMIN) || !loggedUser.get().getRole().equals(Role.MODERATOR)) {
            logger.error("Deleting comment failed:\t"+COMMENT_NOT_WRITTEN_BY_THIS_USER);
            throw new ForbiddenException(COMMENT_NOT_WRITTEN_BY_THIS_USER);
        }

        blogComment.get().setVisible(false);
        logger.info("Deleting comment success");
    }
}
