package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select count(1)<1 from user where email = :email", nativeQuery = true)
    boolean isEmailUnique(@Param("email") String email);
}
