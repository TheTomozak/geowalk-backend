package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.models.dto.requests.TravelStopReqDto;
import com.example.geowalk.models.entities.TravelStop;
import com.example.geowalk.models.repositories.TravelStopRepo;
import com.example.geowalk.utils.messages.MessagesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.geowalk.utils.messages.MessageKeys.*;

@Service
@Transactional
public class TravelStopService {

    private static final Logger logger = LoggerFactory.getLogger(TravelStopService.class);
    private final MessagesUtil dict;
    private final TravelStopRepo travelStopRepo;

    public TravelStopService(MessagesUtil dict, TravelStopRepo travelStopRepo) {
        this.dict = dict;
        this.travelStopRepo = travelStopRepo;
    }

    public List<TravelStop> getAllTravelStopByLocation(String country, String city, String street){
        if(country == null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_TRAVEL_STOP_FAILED), dict.getDict().get(MISSING_COUNTRY_VALUE));
            throw new BadRequestException(dict.getDict().get(MISSING_COUNTRY_VALUE));
        }

        if(country.isBlank()) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_TRAVEL_STOP_FAILED), dict.getDict().get(MISSING_COUNTRY_VALUE));
            throw new BadRequestException(dict.getDict().get(MISSING_COUNTRY_VALUE));
        }

        if(city == null && street != null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_GET_TRAVEL_STOP_FAILED), dict.getDict().get(MISSING_CITY_VALUE));
            throw new BadRequestException(dict.getDict().get(MISSING_CITY_VALUE));
        }

        if(city == null) {
            return travelStopRepo.findAllByVisibleIsTrueAndCountry(country);
        }

        if(street == null){
            return travelStopRepo.findAllByVisibleIsTrueAndCountryAndCity(country, city);
        }

        return travelStopRepo.findAllByVisibleIsTrueAndCountryAndCityAndStreet(country, city, street);
    }

    public List<TravelStop> getOrCreateTravelStopsByLocation(List<TravelStopReqDto> request){
        List<TravelStop> newTravelStops = new ArrayList<>();
        List<TravelStop> existsTravelStops = new ArrayList<>();

        request.forEach(travelStopReq -> {
            String country = travelStopReq.getCountry();
            String city = travelStopReq.getCity();
            String street = travelStopReq.getStreet();

            if(city == null && street != null) {
                logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_TRAVEL_STOP_FAILED), dict.getDict().get(MISSING_CITY_VALUE));
                throw new BadRequestException(dict.getDict().get(MISSING_CITY_VALUE));
            }

            Optional<TravelStop> travelStop = travelStopRepo.findByVisibleIsTrueAndCountryAndCityAndStreet(country, city, street);
            if(travelStop.isPresent()){
                existsTravelStops.add(travelStop.get());
            } else {
                newTravelStops.add(new TravelStop(
                        travelStopReq.getName(),
                        travelStopReq.getLatitude(),
                        travelStopReq.getLongitude(),
                        travelStopReq.getCountry(),
                        travelStopReq.getCity(),
                        travelStopReq.getStreet()
                ));
            }
        });

        travelStopRepo.saveAll(newTravelStops);
        existsTravelStops.addAll(newTravelStops);
        return existsTravelStops;
    }

    public TravelStop getOrCreateTravelStop(TravelStopReqDto request) {
        String country = request.getCountry();
        String city = request.getCity();
        String street = request.getStreet();

        if (city == null && street != null) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_TRAVEL_STOP_FAILED), dict.getDict().get(MISSING_CITY_VALUE));
            throw new BadRequestException(dict.getDict().get(MISSING_CITY_VALUE));
        }

        Optional<TravelStop> travelStop = travelStopRepo.findByVisibleIsTrueAndCountryAndCityAndStreet(country, city, street);
        if (travelStop.isPresent()) {
            return travelStop.get();
        } else {
            TravelStop newTravelStop = new TravelStop(
                    request.getName(),
                    request.getLatitude(),
                    request.getLongitude(),
                    country,
                    city,
                    street
            );
            travelStopRepo.save(newTravelStop);
            return newTravelStop;
        }
    }
}