package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.NotAcceptableException;
import com.example.geowalk.exceptions.base.NotFoundException;
import com.example.geowalk.models.dto.requests.UserReqDto;
import com.example.geowalk.models.dto.responses.UserResDto;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public List<UserResDto> getUsers() {
        List<UserResDto> result = new ArrayList<>();
        for (User user : userRepo.findAll()) {
            if(user.isVisible()) {
                result.add(mapper.map(user, UserResDto.class));
            }
        }
        return result;
    }

    public UserResDto getUser(long userId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        if(!user.get().isVisible()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }

        return mapper.map(user.get(), UserResDto.class);
    }

    public void createUser(UserReqDto request) {
        if(!isEmailUnique(request.getEmail())) {
            throw new NotAcceptableException(EMAIL_ALREADY_IN_USE);
        }
        User user = mapper.map(request, User.class);
        userRepo.save(user);
    }

    public void updateUser(long userId, UserReqDto request) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        if(!isEmailUnique(request.getEmail())) {
            throw new NotAcceptableException(EMAIL_ALREADY_IN_USE);
        }
        user.get().setFirstName(request.getFirstName());
        user.get().setLastName(request.getLastName());
        user.get().setEmail(request.getEmail());
        user.get().setPassword(request.getPassword());
    }

    public void deleteUser(long id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isEmpty()) {
            throw new NotFoundException(USER_NOT_FOUND);
        }
        user.get().setVisible(false);
    }

    public boolean isEmailUnique(String email) {
        return userRepo.isEmailUnique(email);
    }
}
