package com.example.geowalk.models.dto.responses;

import com.example.geowalk.models.entities.BlogPost;
import com.example.geowalk.models.entities.TravelStop;
import com.example.geowalk.models.enums.RouteDifficulty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class TravelRouteResponse {

    private String name;

    private RouteDifficulty difficulty;

    private String description;

    private List<TravelStopResponse> travelStops;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RouteDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(RouteDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TravelStopResponse> getTravelStops() {
        return travelStops;
    }

    public void setTravelStops(List<TravelStopResponse> travelStops) {
        this.travelStops = travelStops;
    }
}
