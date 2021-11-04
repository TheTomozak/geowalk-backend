package com.example.geowalk.services;

import com.example.geowalk.exceptions.NotFoundTravelStopException;
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

    public TravelStopService(TravelStopRepo travelStopRepository) {
        this.travelStopRepository = travelStopRepository;
    }

    public List<TravelStop> getAllTravelStop(){
        return travelStopRepository.findAll();
    }

    public TravelStop getTravelStopById(Long travelStopId) throws NotFoundTravelStopException {
        return travelStopRepository.findById(travelStopId)
                .orElseThrow(() ->
                        new NotFoundTravelStopException(String.format("Cannot find travelStop by id: %s", travelStopId))
                );
    }

    public void createTravelStop(TravelStopRequest travelStopRequest){
        TravelStop travelStop = new TravelStop(
                travelStopRequest.getName(),
                travelStopRequest.getLatitude(),
                travelStopRequest.getLongitude()
        );
        travelStopRepository.save(travelStop);
    }
}