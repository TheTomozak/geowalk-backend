package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.NotAcceptableException;
import com.example.geowalk.exceptions.base.NotFoundException;
import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private ModelMapper mapper = new ModelMapper();
    private final String USER_NOT_FOUND = "User with given id not found";
    private final String EMAIL_ALREADY_IN_USE = "Email is already in use";
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepo.findAllByVisibleIsTrue();
    }

    public User getUser(Long userId) {
        return userRepo.findByIdAndVisibleIsTrue(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public User getUser(String email) {
        return userRepo.findByEmailAndVisibleIsTrue(email.trim())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    public void createUser(UserReqDto request) {
        if(!isEmailUnique(request.getEmail())) {
            throw new NotAcceptableException(EMAIL_ALREADY_IN_USE);
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = mapper.map(request, User.class);
        userRepo.save(user);
    }

    public void updateUser(long userId, UserReqDto request) {
        User user = getUser(userId);
        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if(request.getEmail() != null) {
            if(!isEmailUnique(request.getEmail())) {
                throw new NotAcceptableException(EMAIL_ALREADY_IN_USE);
            }
            user.setEmail(request.getEmail());
        }
        if(request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
    }

    public void deleteUser(long userId) {
        getUser(userId).setVisible(false);
    }

    public boolean isEmailUnique(String email) {
        return userRepo.isEmailUnique(email);
    }
}
