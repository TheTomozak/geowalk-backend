package com.example.geowalk.models.dto.requests;

import com.example.geowalk.models.entities.Tag;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class BlogPostRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private Long userId;

    private List<TravelRouteRequest> travelRouteRequestList;

    private List<TravelStopRequest> travelStopRequestList;

    private List<String> tagList;


    public List<String> getTagList() {
        return tagList;
    }

    public void setTagList(List<String> tagList) {
        this.tagList = tagList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public List<TravelRouteRequest> getTravelRouteRequestList() {
        return travelRouteRequestList;
    }

    public void setTravelRouteRequestList(List<TravelRouteRequest> travelRouteRequestList) {
        this.travelRouteRequestList = travelRouteRequestList;
    }

    public List<TravelStopRequest> getTravelStopRequestList() {
        return travelStopRequestList;
    }

    public void setTravelStopRequestList(List<TravelStopRequest> travelStopRequestList) {
        this.travelStopRequestList = travelStopRequestList;
    }
}