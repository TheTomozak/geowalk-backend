package com.example.geowalk.services;


import com.example.geowalk.exceptions.NotFoundTravelRouteException;
import com.example.geowalk.models.dto.requests.TravelRouteRequest;
import com.example.geowalk.models.entities.TravelRoute;
import com.example.geowalk.models.repositories.TravelRouteRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class TravelRouteService {

    private final TravelRouteRepo travelRouteRepository;

    public TravelRouteService(TravelRouteRepo travelRouteRepository) {
        this.travelRouteRepository = travelRouteRepository;
    }

    public List<TravelRoute> getAllTravelRoute(){
        return travelRouteRepository.findAll();
    }

    public TravelRoute getTravelRouteById(Long TravelRouteId) throws NotFoundTravelRouteException {
        return travelRouteRepository.findById(TravelRouteId)
                .orElseThrow(() ->
                        new NotFoundTravelRouteException(String.format("Cannot find TravelRoute by id: %s", TravelRouteId))
                );
    }

    public void createTravelRoute(TravelRouteRequest travelRouteRequest){
        TravelRoute travelRoute = new TravelRoute(
            travelRouteRequest.getName(),
            travelRouteRequest.getDifficulty(),
            travelRouteRequest.getDescription()
        );
        travelRouteRepository.save(travelRoute);
    }

}
