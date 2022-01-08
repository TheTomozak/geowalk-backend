package com.example.geowalk.models.dto.responses;

import com.example.geowalk.models.enums.RouteDifficulty;

import java.util.List;

public class TravelRouteResDto {

    private String name;

    private RouteDifficulty difficulty;

    private String description;

    private List<TravelStopResDto> travelStops;

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

    public List<TravelStopResDto> getTravelStops() {
        return travelStops;
    }

    public void setTravelStops(List<TravelStopResDto> travelStops) {
        this.travelStops = travelStops;
    }
}
