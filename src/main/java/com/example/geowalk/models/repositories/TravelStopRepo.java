package com.example.geowalk.models.repositories;

import com.example.geowalk.models.entities.TravelStop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelStopRepo extends JpaRepository<TravelStop, Long> {
    @Query(value = "select * from travel_stop where country = :country and city = :city and street LIKE :street%", nativeQuery = true)
    List<TravelStop> findAllByVisibleIsTrueAndCountryAndCityAndStreet(String country, String city, String street);

    List<TravelStop> findAllByVisibleIsTrueAndCountryAndCity(String country, String city);

    List<TravelStop> findAllByVisibleIsTrueAndCountry(String country);

    Optional<TravelStop> findByVisibleIsTrueAndCountryAndLatitudeAndLongitude(String country, double latitude, double longitude);
}
