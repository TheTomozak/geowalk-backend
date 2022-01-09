package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {

    Optional<Tag> findByNameIgnoreCase(String name);
}
