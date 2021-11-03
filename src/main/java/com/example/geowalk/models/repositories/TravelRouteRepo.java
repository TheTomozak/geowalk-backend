package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.TravelRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRouteRepo extends JpaRepository<TravelRoute, Long> {
}
