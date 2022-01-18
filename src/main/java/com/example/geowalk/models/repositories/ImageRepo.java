package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {

    @Query(value = "SELECT max(id) FROM Image")
    Long max();
}
