package com.example.geowalk.services;

import com.example.geowalk.exceptions.BadRequestException;
import com.example.geowalk.models.dto.requests.TravelRouteReqDto;
import com.example.geowalk.models.entities.TravelRoute;
import com.example.geowalk.models.entities.TravelStop;
import com.example.geowalk.models.enums.RouteDifficulty;
import com.example.geowalk.models.repositories.TravelRouteRepo;
import com.example.geowalk.models.repositories.TravelStopRepo;
import com.example.geowalk.utils.messages.MessageKeys;
import com.example.geowalk.utils.messages.MessagesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.geowalk.utils.messages.MessageKeys.BLOG_POST_BAD_REQUEST;
import static com.example.geowalk.utils.messages.MessageKeys.LOGGER_CREATE_TRAVEL_ROUTE_FAILED;

@Service
@Transactional
public class TravelRouteService {

    private static final Logger logger = LoggerFactory.getLogger(TravelRouteService.class);
    private final MessagesUtil dict;

    private final TravelStopService travelStopService;
    private final TravelRouteRepo travelRouteRepo;

    public TravelRouteService(MessagesUtil dict,
                              TravelStopService travelStopService,
                              TravelRouteRepo travelRouteRepo) {
        this.dict = dict;
        this.travelStopService = travelStopService;
        this.travelRouteRepo = travelRouteRepo;
    }

    public TravelRoute createTravelRoute(TravelRouteReqDto request){
        if(!RouteDifficulty.getAllValues().contains(request.getDifficulty().toString())) {
            logger.error("{}{}", dict.getDict().get(LOGGER_CREATE_TRAVEL_ROUTE_FAILED), dict.getDict().get(BLOG_POST_BAD_REQUEST));
            throw new BadRequestException(dict.getDict().get(MessageKeys.BLOG_POST_BAD_REQUEST));
        }

        TravelRoute travelRoute = new TravelRoute(
                request.getName(),
                request.getDifficulty(),
                request.getDescription()
        );

        List<TravelStop> travelStops = travelStopService.getOrCreateTravelStopsByLocation(new ArrayList<>(request.getTravelStops()));
        travelRoute.setTravelStops(travelStops);
        travelRouteRepo.save(travelRoute);
        logger.info("Creating travel route success");
        return travelRoute;
    }

    public TravelRoute getOrCreateTravelRoute(TravelRouteReqDto request) {
        Optional<TravelRoute> travelRoute = travelRouteRepo.findByNameAndDifficultyAndDescription(request.getName(), request.getDifficulty(), request.getDescription());
        if (travelRoute.isEmpty()) {
            return createTravelRoute(request);
        } else {
            return travelRoute.get();
        }
    }
}