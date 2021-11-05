package com.example.geowalk.services;

import com.example.geowalk.models.dto.responses.UserResDto;
import com.example.geowalk.models.entities.User;
import com.example.geowalk.models.repositories.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepo userRepo;
    private ModelMapper mapper = new ModelMapper();

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserResDto> getUsers() {
        List<UserResDto> result = new ArrayList<>();
        for (User user : userRepo.findAll()) {
            result.add(mapper.map(user, UserResDto.class));
        }
        return result;
    }
}
