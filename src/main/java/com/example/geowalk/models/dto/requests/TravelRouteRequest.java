package com.example.geowalk.models.dto.requests;

import com.example.geowalk.models.enums.RouteDifficulty;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class TravelRouteRequest {
    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RouteDifficulty difficulty;

    @NotNull
    private Set<TravelStopRequest> travelStopList;

    @NotBlank
    private String description;

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

    public Set<TravelStopRequest> getTravelStopList() {
        return travelStopList;
    }

    public void setTravelStopList(Set<TravelStopRequest> travelStopList) {
        this.travelStopList = travelStopList;
    }
}
