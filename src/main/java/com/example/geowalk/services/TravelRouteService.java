package com.example.geowalk.services;

import com.example.geowalk.exceptions.base.NotFoundException;
import com.example.geowalk.models.dto.requests.TravelRouteRequest;
import com.example.geowalk.models.entities.TravelRoute;
import com.example.geowalk.models.entities.TravelStop;
import com.example.geowalk.models.repositories.TravelRouteRepo;
import com.example.geowalk.models.repositories.TravelStopRepo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TravelRouteService {


    private final TravelStopService travelStopService;
    private final TravelRouteRepo travelRouteRepository;
    private final TravelStopRepo travelStopRepository;
    private final String TRAVEL_ROUTE_NOT_FOUND = "TravelRoute with given id not found";


    public TravelRouteService(TravelStopService travelStopService, TravelRouteRepo travelRouteRepository, TravelStopRepo travelStopRepository) {
        this.travelStopService = travelStopService;
        this.travelRouteRepository = travelRouteRepository;
        this.travelStopRepository = travelStopRepository;
    }

    public List<TravelRoute> getAllTravelRoute(){
        return travelRouteRepository.findAll();
    }

    public TravelRoute getTravelRouteById(Long TravelRouteId) {
        return travelRouteRepository.findById(TravelRouteId)
                .orElseThrow(() -> new NotFoundException(TRAVEL_ROUTE_NOT_FOUND));
    }

    public TravelRoute createTravelRoute(TravelRouteRequest travelRouteRequest){
        TravelRoute travelRoute = new TravelRoute(
            travelRouteRequest.getName(),
            travelRouteRequest.getDifficulty(),
            travelRouteRequest.getDescription()
        );

        List<TravelStop> travelStopList = new ArrayList<>(travelStopService.getOrCreateTravelStopsByLocation(
                new ArrayList<>(travelRouteRequest.getTravelStopList()))
        );
        travelRoute.setTravelStops(travelStopList);
        return travelRoute;
    }
}