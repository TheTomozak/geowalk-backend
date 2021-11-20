package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.NotAcceptableException;
import com.example.geowalk.exceptions.base.NotFoundException;
import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private ModelMapper mapper = new ModelMapper();
    private final String USER_NOT_FOUND = "User with given id not found";
    private final String EMAIL_ALREADY_IN_USE = "Email is already in use";

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
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
        User user = mapper.map(request, User.class);
        userRepo.save(user);
    }

    public void updateUser(long userId, UserReqDto request) {
        User user = getUser(userId);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
    }

    public void deleteUser(long userId) {
        getUser(userId).setVisible(false);
    }

    public boolean isEmailUnique(String email) {
        return userRepo.isEmailUnique(email);
    }
}
