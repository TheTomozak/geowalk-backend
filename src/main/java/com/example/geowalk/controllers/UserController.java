package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.ObjectMapperUtils;
import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.dto.responses.UserDetailsResDto;
import com.example.geowalk.models.dto.responses.UserResDto;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
        List<User> users = userService.getUsers();
        logger.info("Getting users success");
        return ObjectMapperUtils.mapAll(users, UserResDto.class);
    }

    @GetMapping("/{userId}")
    public UserDetailsResDto getUser(@PathVariable("userId") long userId) {
        logger.info(String.format("UserController GET[api/users/%s] Getting user with id >>\t%s\t<<", userId, userId));
        User user = userService.getUser(userId);
        logger.info("Getting user success");
        return ObjectMapperUtils.map(user, UserDetailsResDto.class);
    }

    @GetMapping("byEmail/{email}")
    public UserDetailsResDto getUser(@PathVariable("email") String email) {
        logger.info(String.format("UserController GET[api/users/%s] Getting user with email >>\t%s\t<<", email, email));
        User user = userService.getUser(email);
        logger.info("Getting user success");
        return mapper.map(user, UserDetailsResDto.class);
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
