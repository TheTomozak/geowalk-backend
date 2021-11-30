package com.example.geowalk.models.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BlogPostRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long userId;

    private List<TravelRouteRequest> travelRouteRequest;

    private List<TravelStopRequest> travelStopRequest;



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

    public List<TravelRouteRequest> getTravelRouteRequest() {
        return travelRouteRequest;
    }

    public void setTravelRouteRequest(List<TravelRouteRequest> travelRouteRequest) {
        this.travelRouteRequest = travelRouteRequest;
    }

    public List<TravelStopRequest> getTravelStopRequest() {
        return travelStopRequest;
    }

    public void setTravelStopRequest(List<TravelStopRequest> travelStopRequest) {
        this.travelStopRequest = travelStopRequest;
    }
}