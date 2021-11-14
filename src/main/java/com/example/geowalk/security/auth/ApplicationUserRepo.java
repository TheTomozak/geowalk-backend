package com.example.geowalk.security.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ApplicationUserRepo implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    public ApplicationUserRepo(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByEmail(String email) {
        return Optional.empty();
    }
}
