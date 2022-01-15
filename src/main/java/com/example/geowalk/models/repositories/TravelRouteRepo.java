package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.TravelRoute;
import com.example.geowalk.models.enums.RouteDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelRouteRepo extends JpaRepository<TravelRoute, Long> {

    Optional<TravelRoute> findByNameAndDifficultyAndDescription(String name, RouteDifficulty difficulty, String description);

}
