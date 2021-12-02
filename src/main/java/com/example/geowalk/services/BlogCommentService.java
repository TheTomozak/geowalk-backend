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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BlogCommentService {

    private static final Logger logger = LoggerFactory.getLogger(BlogCommentService.class);
    private final ModelMapper mapper;
    private final ISessionUtil sessionUtil;

    private final BlogCommentRepo blogCommentRepo;
    private final BlogPostRepo blogPostRepo;
    private final UserRepo userRepo;

    private final String LOGGER_GET_COMMENT_FAILED = "Getting comments failed:\t";
    private final String LOGGER_CREATE_COMMENT_FAILED = "Creating comment failed:\t";
    private final String LOGGER_UPDATE_COMMENT_FAILED = "Updating comment failed:\t";
    private final String LOGGER_DELETE_COMMENT_FAILED = "Deleting comment failed:\t";

    private final String BLOG_COMMENT_NOT_FOUND = "Blog comment with given id not found";
    private final String BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER = "User cannot delete comment others users";
    private final String BLOG_POST_NOT_FOUND = "BlogPost with given id not found";
    private final String BLOG_POST_INVALID_RATING_VALUE = "Rating value is invalid";
    private final String USER_NOT_AUTHORIZED = "User is not authenticated/authorized";
    private final String USER_BLOCKED_OR_DELETED = "User is deleted/blocked";

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
        if (blogPost.isEmpty()) {
            logger.error(LOGGER_GET_COMMENT_FAILED + BLOG_POST_NOT_FOUND);
            throw new NotFoundException(BLOG_POST_NOT_FOUND);
        }

        List<BlogCommentResponse> result = new ArrayList<>();
        for (BlogComment blogComment : blogPost.get().getBlogComments()) {
            if (blogComment.isVisible()) {
                UserResDto blogCommentUser = mapper.map(blogComment.getUser(), UserResDto.class);
                BlogCommentResponse blogCommentResponse = mapper.map(blogComment, BlogCommentResponse.class);
                blogCommentResponse.setUser(blogCommentUser);
                result.add(blogCommentResponse);
            }
        }
        logger.info("Getting comments success");
        return result;
    }

    public void createBlogComment(long blogPostId, BlogCommentReqDto request) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error(LOGGER_CREATE_COMMENT_FAILED + USER_NOT_AUTHORIZED);
            throw new UnauthorizedException(USER_NOT_AUTHORIZED);
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error(LOGGER_CREATE_COMMENT_FAILED + USER_BLOCKED_OR_DELETED);
            throw new NotFoundException(USER_BLOCKED_OR_DELETED);
        }

        Optional<BlogPost> blogPost = blogPostRepo.findByIdAndVisibleTrue(blogPostId);
        if (blogPost.isEmpty()) {
            logger.error(LOGGER_CREATE_COMMENT_FAILED + BLOG_POST_NOT_FOUND);
            throw new NotFoundException(BLOG_POST_NOT_FOUND);
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            logger.error(LOGGER_CREATE_COMMENT_FAILED + BLOG_POST_INVALID_RATING_VALUE);
            throw new BadRequestException(BLOG_POST_INVALID_RATING_VALUE);
        }
        BlogComment blogComment = mapper.map(request, BlogComment.class);
        blogComment.setUser(loggedUser.get());
        blogComment.setBlogPost(blogPost.get());
        blogCommentRepo.save(blogComment);
        logger.info("Creating comment success");
    }

    public void updateBlogComment(long blogCommentId, BlogCommentReqDto request) {
        Optional<String> loggedUserUserName = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUserName.isEmpty()) {
            logger.error(LOGGER_UPDATE_COMMENT_FAILED + USER_NOT_AUTHORIZED);
            throw new UnauthorizedException(USER_NOT_AUTHORIZED);
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUserName.get());
        if (loggedUser.isEmpty()) {
            logger.error(LOGGER_UPDATE_COMMENT_FAILED + USER_BLOCKED_OR_DELETED);
            throw new NotFoundException(USER_BLOCKED_OR_DELETED);
        }

        Optional<BlogComment> blogComment = blogCommentRepo.findByIdAndVisibleTrue(blogCommentId);
        if (blogComment.isEmpty()) {
            logger.error(LOGGER_UPDATE_COMMENT_FAILED + BLOG_COMMENT_NOT_FOUND);
            throw new NotFoundException(BLOG_COMMENT_NOT_FOUND);
        }

        if (!blogComment.get().getUser().equals(loggedUser.get())) {
            if (!(loggedUser.get().getRole().equals(Role.ADMIN) || loggedUser.get().getRole().equals(Role.MODERATOR))) {
                logger.error(LOGGER_UPDATE_COMMENT_FAILED + BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER);
                throw new ForbiddenException(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER);
            }
        }

        if (request.getContent() != null) {
            if (!request.getContent().isBlank()) {
                blogComment.get().setContent(request.getContent());
            }
        }

        if (request.getRating() != null) {
            if (request.getRating() < 1 || request.getRating() > 5) {
                logger.error(LOGGER_UPDATE_COMMENT_FAILED + BLOG_POST_INVALID_RATING_VALUE);
                throw new BadRequestException(BLOG_POST_INVALID_RATING_VALUE);
            }
            blogComment.get().setRating(request.getRating());
        }

        blogCommentRepo.save(blogComment.get());
        logger.info("Updating comment success");
    }

    public void deleteBlogComment(long blogCommentId) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error(LOGGER_DELETE_COMMENT_FAILED + USER_NOT_AUTHORIZED);
            throw new UnauthorizedException(USER_NOT_AUTHORIZED);
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error(LOGGER_DELETE_COMMENT_FAILED + USER_BLOCKED_OR_DELETED);
            throw new NotFoundException(USER_BLOCKED_OR_DELETED);
        }

        Optional<BlogComment> blogComment = blogCommentRepo.findById(blogCommentId);
        if (blogComment.isEmpty()) {
            logger.error(LOGGER_DELETE_COMMENT_FAILED + BLOG_COMMENT_NOT_FOUND);
            throw new NotFoundException(BLOG_COMMENT_NOT_FOUND);
        }

        if (!blogComment.get().getUser().equals(loggedUser.get())) {
            if (!(loggedUser.get().getRole().equals(Role.ADMIN) || loggedUser.get().getRole().equals(Role.MODERATOR))) {
                logger.error(LOGGER_DELETE_COMMENT_FAILED + BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER);
                throw new ForbiddenException(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER);
            }
        }

        blogComment.get().setVisible(false);
        logger.info("Deleting comment success");
    }
}
