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
import org.springframework.security.access.prepost.PreAuthorize;
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
    public UserController(UserService userService, ModelMapper mapper) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public List<UserResDto> getUsers() {
        logger.info("GET[api/users] Getting all users");
        List<User> users = userService.getUsers();
        return ObjectMapperUtils.mapAll(users, UserResDto.class);
    }

    @GetMapping("/{userId}")
    public UserDetailsResDto getUser(@PathVariable("userId") long userId) {
        logger.info("GET[api/users/{}] Getting user with id {}", userId, userId);
        User user = userService.getUser(userId);
        return ObjectMapperUtils.map(user, UserDetailsResDto.class);
    }

    @GetMapping("byEmail/{email}")
    public UserDetailsResDto getUser(@PathVariable("email") String email) {
        logger.info("GET[api/users/byEmail/{}] Getting user with email {}", email, email);
        User user = userService.getUser(email);
        return mapper.map(user, UserDetailsResDto.class);
    }

    @PostMapping
    public void createUser(@RequestBody UserReqDto request) {
        logger.info("POST[api/users] Creating new user with email {}", request.getEmail());
        userService.createUser(request);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    public void updateUser(@PathVariable("userId") long userId, @RequestBody UserReqDto request) {
        logger.info("PUT[api/users/{}] Updating user with id {}", userId, userId);
        userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'MODERATOR', 'ADMIN')")
    public void deleteUser(@PathVariable long userId) {
        logger.info("DELETE[api/users/{}] Deleting user with id {}", userId, userId);
        userService.deleteUser(userId);
    }

    @PostMapping("/emailCheck")
    public boolean isEmailUnique(@RequestBody String email) {
        logger.info("POST[api/users/emailCheck] Checking if email {} is unique", email);
        return userService.isEmailUnique(email);
    }

    @PostMapping("/{userId}/assignRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void assignRole(@PathVariable("userId") Long userId, @RequestParam(defaultValue = "user") String role) {
        logger.info("POST[api/users/{}/assignRole] Assigning role {} to user with id {}", userId, role, userId);
        userService.assignRole(userId, role);
    }
}
