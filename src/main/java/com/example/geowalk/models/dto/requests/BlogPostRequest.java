package com.example.geowalk.models.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BlogPostRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long userId;

    private TravelRouteRequest travelRouteRequest;

    private TravelStopRequest travelStopRequest;



    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public TravelRouteRequest getTravelRouteRequest() {
        return travelRouteRequest;
    }

    public void setTravelRouteRequest(TravelRouteRequest travelRouteRequest) {
        this.travelRouteRequest = travelRouteRequest;
    }

    public TravelStopRequest getTravelStopRequest() {
        return travelStopRequest;
    }

    public void setTravelStopRequest(TravelStopRequest travelStopRequest) {
        this.travelStopRequest = travelStopRequest;
    }
}