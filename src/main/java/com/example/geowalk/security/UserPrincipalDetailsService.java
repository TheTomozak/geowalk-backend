package com.example.geowalk.security;

import com.example.geowalk.models.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

    private final UserRepo userRepo;
    private static final String USER_NOT_FOUND = "User not found";

    public UserPrincipalDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserPrincipal(userRepo.getUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND)));
    }
}
