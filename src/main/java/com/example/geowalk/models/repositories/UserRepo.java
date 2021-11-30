package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select count(1)<1 from user where email = :email", nativeQuery = true)
    boolean isEmailUnique(@Param("email") String email);

    @Query(value = "select * from user where email = :email", nativeQuery = true)
    Optional<User> getUserByEmail(@Param("email") String email);
}