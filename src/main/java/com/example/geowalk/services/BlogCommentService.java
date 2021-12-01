package com.example.geowalk.services;

import com.example.geowalk.exceptions.ForbiddenException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.exceptions.UnauthorizedException;
import com.example.geowalk.models.entities.BlogComment;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.enums.Role;
import com.example.geowalk.models.repositories.BlogCommentRepo;
import com.example.geowalk.models.repositories.UserRepo;
import com.example.geowalk.utils.ISessionUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class BlogCommentService {

    private static final Logger logger = LoggerFactory.getLogger(BlogCommentService.class);
    private final BlogCommentRepo blogCommentRepo;
    private final UserRepo userRepo;
    private ModelMapper mapper;
    private final ISessionUtil sessionUtil;
    private final String BLOG_COMMENT_NOT_FOUND = "Blog comment with given id not found";
    private final String USER_NOT_AUTHORIZED = "User is not authenticated";
    private final String USER_BLOCKED_OR_DELETED = "User is deleted/blocked";
    private final String COMMENT_NOT_WRITTEN_BY_THIS_USER = "User cannot delete comment others users";

    @Autowired
    public BlogCommentService(BlogCommentRepo blogCommentRepo, UserRepo userRepo, ISessionUtil sessionUtil) {
        this.blogCommentRepo = blogCommentRepo;
        this.userRepo = userRepo;
        this.sessionUtil = sessionUtil;
        mapper = new ModelMapper();
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
