package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.NotFoundException;
import com.example.geowalk.models.dto.requests.TravelStopRequest;
import com.example.geowalk.models.entities.TravelStop;
import com.example.geowalk.models.repositories.TravelStopRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TravelStopService {

    private final TravelStopRepo travelStopRepository;

    private final String NOT_FOUND_BLOG_POST = "Travel stop with given id not found";

    public TravelStopService(TravelStopRepo travelStopRepository) {
        this.travelStopRepository = travelStopRepository;
    }

    public List<TravelStop> getAllTravelStop(){
        return travelStopRepository.findAll();
    }

    public TravelStop getTravelStopByName(String name){
        return travelStopRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BLOG_POST));
    }

    public TravelStop getTravelStopById(Long travelStopId) {
        return travelStopRepository.findById(travelStopId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_BLOG_POST));
    }

    public TravelStop createTravelStop(TravelStopRequest travelStopRequest){
        TravelStop travelStop = new TravelStop(
                travelStopRequest.getName(),
                travelStopRequest.getLatitude(),
                travelStopRequest.getLongitude(),
                travelStopRequest.getCountry(),
                travelStopRequest.getCity(),
                travelStopRequest.getStreet()
        );
        return travelStop;
    }
}