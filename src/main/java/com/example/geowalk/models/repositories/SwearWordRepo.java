package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.SwearWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SwearWordRepo extends JpaRepository<SwearWord, Long> {
}
