package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.TravelStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelStopRepo extends JpaRepository<TravelStop, Long> {
    Optional<TravelStop> findByName(String name);
}
