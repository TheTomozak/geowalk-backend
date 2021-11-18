package com.example.geowalk.controllers;

import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.dto.responses.UserResDto;
import com.example.geowalk.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserResDto getUser(@PathVariable("userId") long userId) {
        logger.info("User with id: "+ userId +" has been shown");
        return userService.getUser(userId);
    }

    @GetMapping("/{email}")
    public UserResDto getUser(@PathVariable("email") String email) {
        logger.info("User with mail: "+ email +" has been shown");
        return userService.getUser(email);
    }



    @PostMapping
    public void createUser(@RequestBody UserReqDto request) {
        logger.info("User has been created");
        userService.createUser(request);
    }

    @PutMapping("/{userId}")
    public void updateUser(@PathVariable("userId") long userId, @RequestBody UserReqDto request) {
        logger.info("User with id: "+ userId +" has been updated");
        userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable long userId) {
        logger.info("User with id: "+ userId +" has been deleted");
        userService.deleteUser(userId);
    }

    @PostMapping("/emailCheck")
    public boolean isEmailUnique(@RequestBody String email) {
        return userService.isEmailUnique(email);
    }
}
