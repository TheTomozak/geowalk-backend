package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.ForbiddenException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.exceptions.UnauthorizedException;
import com.example.geowalk.models.dto.requests.BlogCommentReqDto;
import com.example.geowalk.models.dto.requests.BlogCommentVerificationReqDto;
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
import com.example.geowalk.utils.SwearWordsFilter;
import com.example.geowalk.utils.messages.MessagesUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.example.geowalk.utils.messages.MessageKeys.*;

@Service
@Transactional
public class BlogCommentService {

    private static final Logger logger = LoggerFactory.getLogger(BlogCommentService.class);

    private final ModelMapper mapper;
    private final ISessionUtil sessionUtil;
    private final SwearWordsFilter swearWordsFilter;
    private final MessagesUtil dict;

    private final BlogCommentRepo blogCommentRepo;
    private final BlogPostRepo blogPostRepo;
    private final UserRepo userRepo;

    @Autowired
    public BlogCommentService(ModelMapper mapper,
                              BlogCommentRepo blogCommentRepo,
                              BlogPostRepo blogPostRepo,
                              UserRepo userRepo,
                              ISessionUtil sessionUtil,
                              SwearWordsFilter swearWordsFilter,
                              MessagesUtil dict) {
        this.mapper = mapper;
        this.blogCommentRepo = blogCommentRepo;
        this.blogPostRepo = blogPostRepo;
        this.userRepo = userRepo;
        this.sessionUtil = sessionUtil;
        this.swearWordsFilter = swearWordsFilter;
        this.dict = dict;
    }

    public List<BlogCommentResponse> getBlogComments(long blogPostId) {
        Optional<BlogPost> blogPost = blogPostRepo.findById(blogPostId);
        if (blogPost.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_COMMENT_FAILED), dict.getDict().get(BLOG_POST_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_POST_NOT_FOUND));
        }

        List<BlogCommentResponse> result = new ArrayList<>();
        for (BlogComment blogComment : blogPost.get().getBlogComments()) {
            if (blogComment.isVisible() && !blogComment.isNeedToVerify()) {
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
        if (loggedUserUsername.isEmpty()) {
            logger.error("{}{}",dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
            throw new UnauthorizedException(dict.getDict().get(USER_NOT_AUTHORIZED));
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        Optional<BlogPost> blogPost = blogPostRepo.findByIdAndVisibleTrue(blogPostId);
        if (blogPost.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(BLOG_POST_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_POST_NOT_FOUND));
        }

        if(request.getContent() == null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(FIELD_CANNOT_BE_NULL));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_NULL));
        }

        if(request.getContent().isBlank()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(FIELD_CANNOT_BE_BLANK));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_BLANK));
        }

        if (request.getRating() == null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(FIELD_CANNOT_BE_NULL));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_NULL));
        }

        if (request.getRating() < 1 || request.getRating() > 5) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_INVALID_RATING_VALUE));
            throw new BadRequestException(dict.getDict().get(BLOG_COMMENT_INVALID_RATING_VALUE));
        }

        BlogComment blogComment = mapper.map(request, BlogComment.class);
        blogComment.setUser(loggedUser.get());
        blogComment.setBlogPost(blogPost.get());

        if (swearWordsFilter.hasSwearWord(request.getContent())) {
            blogComment.setNeedToVerify(true);
        }

        blogCommentRepo.save(blogComment);
        logger.info(blogComment.isNeedToVerify() ? dict.getDict().get(SWEAR_WORDS_FILTER_MESSAGE_COMMENT) : "Creating comment success");
    }

    public void updateBlogComment(long blogCommentId, BlogCommentReqDto request) {
        Optional<String> loggedUserUserName = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUserName.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_COMMENT_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
            throw new UnauthorizedException(dict.getDict().get(USER_NOT_AUTHORIZED));
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUserName.get());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_COMMENT_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        Optional<BlogComment> blogComment = blogCommentRepo.findByIdAndVisibleTrue(blogCommentId);
        if (blogComment.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_COMMENT_NOT_FOUND));
        }

        if (!blogComment.get().getUser().equals(loggedUser.get())) {
            if (!(loggedUser.get().getRole().equals(Role.ADMIN) || loggedUser.get().getRole().equals(Role.MODERATOR))) {
                logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER));
                throw new ForbiddenException(dict.getDict().get(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER));
            }
        }

        if (request.getContent() != null) {
            if (!request.getContent().isBlank()) {
                blogComment.get().setContent(request.getContent());
            }
        }

        if (request.getRating() != null) {
            if (request.getRating() < 1 || request.getRating() > 5) {
                logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_INVALID_RATING_VALUE));
                throw new BadRequestException(dict.getDict().get(BLOG_COMMENT_INVALID_RATING_VALUE));
            }
            blogComment.get().setRating(request.getRating());
        }

        if (swearWordsFilter.hasSwearWord(request.getContent())) {
            blogComment.get().setNeedToVerify(true);
        }
        blogCommentRepo.save(blogComment.get());
        logger.info(blogComment.get().isNeedToVerify() ? dict.getDict().get(SWEAR_WORDS_FILTER_MESSAGE_COMMENT) : "Updating comment success");
    }

    public void deleteBlogComment(long blogCommentId) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_DELETE_COMMENT_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
            throw new UnauthorizedException(dict.getDict().get(USER_NOT_AUTHORIZED));
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_DELETE_COMMENT_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        Optional<BlogComment> blogComment = blogCommentRepo.findById(blogCommentId);
        if (blogComment.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_DELETE_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_COMMENT_NOT_FOUND));
        }

        if (!blogComment.get().getUser().equals(loggedUser.get())) {
            if (!(loggedUser.get().getRole().equals(Role.ADMIN) || loggedUser.get().getRole().equals(Role.MODERATOR))) {
                logger.error("{}{}", dict.getDict().get(LOGGER_DELETE_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER));
                throw new ForbiddenException(dict.getDict().get(BLOG_COMMENT_NOT_WRITTEN_BY_THIS_USER));
            }
        }

        blogComment.get().setVisible(false);
        logger.info("Deleting comment success");
    }

    /*
         ***********************
         *  Moderator methods  *
         ***********************
    */

    public List<BlogCommentResponse> getBlogCommentsToVerify() {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_COMMENT_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
            throw new UnauthorizedException(dict.getDict().get(USER_NOT_AUTHORIZED));
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        if(!loggedUser.get().getRole().equals(Role.MODERATOR)) {
            if(!loggedUser.get().getRole().equals(Role.ADMIN)) {
                logger.error("{}{}", dict.getDict().get(LOGGER_GET_COMMENT_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
                throw new ForbiddenException(dict.getDict().get(USER_NOT_AUTHORIZED));
            }
        }

        List<BlogCommentResponse> result = new ArrayList<>();
        for (BlogComment blogComment : blogCommentRepo.findBlogCommentsByNeedToVerifyTrue()) {
            BlogCommentResponse blogCommentResponse = mapper.map(blogComment, BlogCommentResponse.class);
            result.add(blogCommentResponse);
        }

        return result;
    }

    public void verifyBlogComment(BlogCommentVerificationReqDto request) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_COMMENT_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
            throw new UnauthorizedException(dict.getDict().get(USER_NOT_AUTHORIZED));
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_COMMENT_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        if(!loggedUser.get().getRole().equals(Role.MODERATOR)) {
            if(!loggedUser.get().getRole().equals(Role.ADMIN)) {
                logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_COMMENT_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
                throw new ForbiddenException(dict.getDict().get(USER_NOT_AUTHORIZED));
            }
        }

        Optional<BlogComment> blogComment = blogCommentRepo.findByIdAndVisibleTrueAndNeedToVerifyTrue(request.getBlogCommentId());

        if(blogComment.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_COMMENT_FAILED), dict.getDict().get(BLOG_COMMENT_NOT_FOUND));
            throw new NotFoundException(dict.getDict().get(BLOG_COMMENT_NOT_FOUND));
        }

        if(!request.getResult().equals("REJECTED")) {
            if(!request.getResult().equals("ACCEPTED")) {
                logger.error("{}{}", dict.getDict().get(LOGGER_VERIFY_COMMENT_FAILED), dict.getDict().get(INVALID_RESULT_VALUE));
                throw new BadRequestException(dict.getDict().get(INVALID_RESULT_VALUE));
            }
        }

        if(request.getResult().equals("REJECTED")) {
            blogComment.get().setVisible(false);
        }

        blogComment.get().setNeedToVerify(false);
        logger.info("Verification comment success");
    }
}
