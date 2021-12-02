package com.example.geowalk.services;

import com.example.geowalk.exceptions.*;
import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.enums.Role;
import com.example.geowalk.models.repositories.UserRepo;
import com.example.geowalk.utils.ISessionUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final ISessionUtil sessionUtil;

    private final UserRepo userRepo;

    private final String LOGGER_GET_USER_FAILED = "Get user failed:\t";
    private final String LOGGER_CREATE_USER_FAILED = "Create user failed:\t";
    private final String LOGGER_UPDATE_USER_FAILED = "Update user failed:\t";
    private final String LOGGER_DELETE_USER_FAILED = "Deleting user failed:\t";

    private final String USER_NOT_FOUND = "User with given id not found";
    private final String USER_NOT_AUTHORIZED = "User is not authenticated/authorized";
    private final String USER_BLOCKED_OR_DELETED = "User already deleted/blocked";
    private final String EMAIL_ALREADY_IN_USE = "Email is already in use";
    private final String FIELD_CANNOT_BE_BLANK = "Field cannot be empty";

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, ISessionUtil sessionUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.sessionUtil = sessionUtil;
        mapper = new ModelMapper();
    }

    public List<User> getUsers() {
        return userRepo.findAllByVisibleIsTrue();
    }

    public User getUser(Long userId) {
        return userRepo.findByIdAndVisibleIsTrue(userId)
                .orElseThrow(() -> {
                    logger.error(LOGGER_GET_USER_FAILED + USER_NOT_FOUND);
                    return new NotFoundException(USER_NOT_FOUND);
                });
    }

    public User getUser(String email) {
        return userRepo.findByEmailAndVisibleIsTrue(email.trim())
                .orElseThrow(() -> {
                    logger.error(LOGGER_GET_USER_FAILED + USER_NOT_FOUND);
                    return new NotFoundException(USER_NOT_FOUND);
                });
    }

    public void createUser(UserReqDto request) {
        if (!isEmailUnique(request.getEmail())) {
            logger.error(LOGGER_CREATE_USER_FAILED + EMAIL_ALREADY_IN_USE);
            throw new NotAcceptableException(EMAIL_ALREADY_IN_USE);
        }

        if (request.getFirstName() != null) {
            if (request.getFirstName().isBlank()) {
                logger.error(LOGGER_CREATE_USER_FAILED + FIELD_CANNOT_BE_BLANK);
                throw new BadRequestException(FIELD_CANNOT_BE_BLANK);
            }
        }

        if (request.getLastName() != null) {
            if (request.getLastName().isBlank()) {
                logger.error(LOGGER_CREATE_USER_FAILED + FIELD_CANNOT_BE_BLANK);
                throw new BadRequestException(FIELD_CANNOT_BE_BLANK);
            }
        }

        if (request.getEmail() != null) {
            if (!isEmailUnique(request.getEmail())) {
                logger.error(LOGGER_UPDATE_USER_FAILED + EMAIL_ALREADY_IN_USE);
                throw new NotAcceptableException(EMAIL_ALREADY_IN_USE);
            }
            if (request.getEmail().isBlank()) {
                logger.error(LOGGER_CREATE_USER_FAILED + FIELD_CANNOT_BE_BLANK);
                throw new BadRequestException(FIELD_CANNOT_BE_BLANK);
            }
        }

        if (request.getPassword() != null) {
            if (request.getPassword().isBlank()) {
                logger.error(LOGGER_CREATE_USER_FAILED + FIELD_CANNOT_BE_BLANK);
                throw new BadRequestException(FIELD_CANNOT_BE_BLANK);
            }
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User user = mapper.map(request, User.class);
        userRepo.save(user);
        logger.info("Create user success");
    }

    public void updateUser(long userId, UserReqDto request) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error(LOGGER_UPDATE_USER_FAILED + USER_NOT_AUTHORIZED);
            throw new UnauthorizedException(USER_NOT_AUTHORIZED);
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error(LOGGER_UPDATE_USER_FAILED + USER_BLOCKED_OR_DELETED);
            throw new NotFoundException(USER_BLOCKED_OR_DELETED);
        }

        Optional<User> userToUpdate = userRepo.findByIdAndVisibleIsTrue(userId);
        if (userToUpdate.isEmpty()) {
            logger.error(LOGGER_UPDATE_USER_FAILED + USER_NOT_FOUND);
            throw new NotFoundException(USER_NOT_FOUND);
        }

        if (!userToUpdate.get().equals(loggedUser.get())) {
            if (!(loggedUser.get().getRole().equals(Role.ADMIN) || loggedUser.get().getRole().equals(Role.MODERATOR))) {
                logger.error(LOGGER_UPDATE_USER_FAILED + USER_NOT_AUTHORIZED);
                throw new ForbiddenException(USER_NOT_AUTHORIZED);
            }
        }

        if (request.getFirstName() != null) {
            if (!request.getFirstName().isBlank()) {
                userToUpdate.get().setFirstName(request.getFirstName());
            }
        }

        if (request.getLastName() != null) {
            if (!request.getLastName().isBlank()) {
                userToUpdate.get().setLastName(request.getLastName());
            }
        }

        if (request.getEmail() != null) {
            if (!isEmailUnique(request.getEmail())) {
                logger.error(LOGGER_UPDATE_USER_FAILED + EMAIL_ALREADY_IN_USE);
                throw new NotAcceptableException(EMAIL_ALREADY_IN_USE);
            }
            if (!request.getEmail().isBlank()) {
                userToUpdate.get().setEmail(request.getEmail());
            }
        }

        if (request.getPassword() != null) {
            userToUpdate.get().setPassword(passwordEncoder.encode(request.getPassword()));
        }
    }

    public void deleteUser(long userId) {
        Optional<String> loggedUserUsername = Optional.ofNullable(sessionUtil.getLoggedUserUsername());
        if (loggedUserUsername.isEmpty()) {
            logger.error(LOGGER_DELETE_USER_FAILED + USER_NOT_AUTHORIZED);
            throw new UnauthorizedException(USER_NOT_AUTHORIZED);
        }

        Optional<User> loggedUser = userRepo.findByEmailAndVisibleIsTrue(loggedUserUsername.get());
        if (loggedUser.isEmpty()) {
            logger.error(LOGGER_DELETE_USER_FAILED + USER_BLOCKED_OR_DELETED);
            throw new NotFoundException(USER_BLOCKED_OR_DELETED);
        }

        if (!loggedUser.get().getRole().equals(Role.ADMIN)) {
            logger.error(LOGGER_DELETE_USER_FAILED + USER_NOT_AUTHORIZED);
            throw new ForbiddenException(USER_NOT_AUTHORIZED);
        }

        getUser(userId).setVisible(false);
        logger.info("Deleting user success");
    }

    public boolean isEmailUnique(String email) {
        logger.info("Checking success");
        return userRepo.isEmailUnique(email);
    }
}
