package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.exceptions.ForbiddenException;
import com.example.geowalk.exceptions.NotAcceptableException;
import com.example.geowalk.exceptions.NotFoundException;
import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.enums.Role;
import com.example.geowalk.models.repositories.UserRepo;
import com.example.geowalk.utils.ISessionUtil;
import com.example.geowalk.utils.messages.MessagesUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.example.geowalk.utils.messages.MessageKeys.*;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final ISessionUtil sessionUtil;
    private final MessagesUtil dict;

    private final UserRepo userRepo;

    @Autowired
    public UserService(ModelMapper mapper, UserRepo userRepo,
                       PasswordEncoder passwordEncoder,
                       ISessionUtil sessionUtil,
                       MessagesUtil messagesUtil) {
        this.mapper = mapper;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.sessionUtil = sessionUtil;
        this.dict = messagesUtil;
    }

    public List<User> getUsers() {
        return userRepo.findAllByVisibleIsTrue();
    }

    public User getUser(Long userId) {
        return userRepo.findByIdAndVisibleIsTrue(userId)
                .orElseThrow(() -> {
                    logger.error("{}{}", dict.getDict().get(LOGGER_GET_USER_FAILED), dict.getDict().get(USER_NOT_FOUND_ID));
                    return new NotFoundException(dict.getDict().get(USER_NOT_FOUND_ID));
                });
    }

    public User getUser(String email) {
        return userRepo.findByEmailAndVisibleIsTrue(email.trim())
                .orElseThrow(() -> {
                    logger.error("{}{}", dict.getDict().get(LOGGER_GET_USER_FAILED), dict.getDict().get(USER_NOT_FOUND_EMAIL));
                    return new NotFoundException(dict.getDict().get(USER_NOT_FOUND_EMAIL));
                });
    }

    public void createUser(UserReqDto request) {
        if (request.getFirstName() == null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_NULL));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_NULL));
        }
        if (request.getFirstName().isBlank()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_BLANK));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_BLANK));
        }

        if (request.getLastName() == null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_NULL));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_NULL));
        }
        if (request.getLastName().isBlank()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_BLANK));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_BLANK));
        }

        if (request.getEmail() == null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_NULL));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_NULL));
        }
        if (!isEmailUnique(request.getEmail())) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(EMAIL_ALREADY_IN_USE));
            throw new NotAcceptableException(dict.getDict().get(EMAIL_ALREADY_IN_USE));
        }
        if (request.getEmail().isBlank()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_BLANK));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_BLANK));
        }

        if (request.getPassword() == null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_NULL));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_NULL));
        }
        if (request.getPassword().isBlank()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_USER_FAILED), dict.getDict().get(FIELD_CANNOT_BE_BLANK));
            throw new BadRequestException(dict.getDict().get(FIELD_CANNOT_BE_BLANK));
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = mapper.map(request, User.class);
        userRepo.save(user);
        logger.info("Creating user success");
    }

    public void updateUser(long userId, UserReqDto request) {
        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(sessionUtil.getLoggedUserUsername());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_USER_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        Optional<User> userToUpdate = userRepo.findByIdAndVisibleIsTrue(userId);
        if (userToUpdate.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_USER_FAILED), dict.getDict().get(USER_NOT_FOUND_ID));
            throw new NotFoundException(dict.getDict().get(USER_NOT_FOUND_ID));
        }

        if(!userToUpdate.get().isVisible()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_USER_FAILED), dict.getDict().get(USER_NOT_FOUND_ID));
            throw new NotFoundException(dict.getDict().get(USER_NOT_FOUND_ID));
        }

        if (!userToUpdate.get().equals(loggedUser.get())) {
            if (!(loggedUser.get().getRole().equals(Role.ADMIN) || loggedUser.get().getRole().equals(Role.MODERATOR))) {
                logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_USER_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
                throw new ForbiddenException(dict.getDict().get(USER_NOT_AUTHORIZED));
            }
        }

        if (!request.getFirstName().equals(userToUpdate.get().getFirstName())) {
            if (!request.getFirstName().isBlank()) {
                userToUpdate.get().setFirstName(request.getFirstName());
            }
        }

        if (!request.getLastName().equals(userToUpdate.get().getLastName())) {
            if (!request.getLastName().isBlank()) {
                userToUpdate.get().setLastName(request.getLastName());
            }
        }

        if (!request.getEmail().equals(userToUpdate.get().getEmail())) {
            if (!isEmailUnique(request.getEmail())) {
                logger.error("{}{}", dict.getDict().get(LOGGER_UPDATE_USER_FAILED), dict.getDict().get(EMAIL_ALREADY_IN_USE));
                throw new NotAcceptableException(dict.getDict().get(EMAIL_ALREADY_IN_USE));
            }
            if (!request.getEmail().isBlank()) {
                userToUpdate.get().setEmail(request.getEmail());
            }
        }

        if (request.getPassword() != null) {
            userToUpdate.get().setPassword(passwordEncoder.encode(request.getPassword()));
        }

        logger.info("Updating user success");
    }

    public void deleteUser(long userId) {
        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(sessionUtil.getLoggedUserUsername());
        if (loggedUser.isEmpty()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_DELETE_USER_FAILED), dict.getDict().get(USER_BLOCKED_OR_DELETED));
            throw new NotFoundException(dict.getDict().get(USER_BLOCKED_OR_DELETED));
        }

        if (loggedUser.get().getRole().equals(Role.USER) && loggedUser.get().getId() != userId) {
            logger.error("{}{}", dict.getDict().get(LOGGER_DELETE_USER_FAILED), dict.getDict().get(USER_NOT_AUTHORIZED));
            throw new ForbiddenException(dict.getDict().get(USER_NOT_AUTHORIZED));
        }

        getUser(userId).setVisible(false);
        logger.info("Deleting user success");
    }

    public boolean isEmailUnique(String email) {
        logger.info("Checking if email is unique finished");
        return userRepo.isEmailUnique(email);
    }
}
