package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.ObjectMapperUtils;
import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.dto.responses.UserDetailsResDto;
import com.example.geowalk.models.dto.responses.UserResDto;
import com.example.geowalk.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final ModelMapper mapper;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        mapper = new ModelMapper();
        this.userService = userService;
    }

    @GetMapping
    public List<UserResDto> getUsers() {
        logger.info("UserController GET[api/users] Getting all users");
        return ObjectMapperUtils.mapAll(userService.getUsers(), UserResDto.class);
    }

    @GetMapping("/{userId}")
    public UserDetailsResDto getUser(@PathVariable("userId") long userId) {
        logger.info(String.format("UserController GET[api/users/%s] Getting user with id >>\t%s\t<<", userId, userId));
        return ObjectMapperUtils.map(userService.getUser(userId), UserDetailsResDto.class);
    }

    @GetMapping("byEmail/{email}")
    public UserDetailsResDto getUser(@PathVariable("email") String email) {
        logger.info(String.format("UserController GET[api/users/%s] Getting user with email >>\t%s\t<<", email, email));
        return mapper.map(userService.getUser(email), UserDetailsResDto.class);
    }

    @PostMapping
    public void createUser(@RequestBody UserReqDto request) {
        logger.info(String.format("UserController POST[api/users] Created new user with email >>\t%s\t<<", request.getEmail()));
        userService.createUser(request);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") long userId, @RequestBody UserReqDto request) {
        logger.info(String.format("UserController PUT[api/users/%s] Updated user with id >>\t%s\t<<", userId, userId));
        userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        logger.info(String.format("UserController DELETE[api/users/%s] Deleted user with id >>\t%s\t<<", userId, userId));
        userService.deleteUser(userId);
    }

    @PostMapping("/emailCheck")
    public boolean isEmailUnique(@RequestBody String email) {
        logger.info(String.format("UserController POST[api/users/emailCheck] Checking if email is unique >>\t%s\t<<", email));
        return userService.isEmailUnique(email);
    }
}
