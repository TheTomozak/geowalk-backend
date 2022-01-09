package com.example.geowalk.models.dto.requests;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BlogPostReqDto {

    @NotBlank
    private String shortDescription;

    @NotBlank
    private String content;

    @NotBlank
    private String title;

    private List<TravelRouteReqDto> travelRoutes;

    private List<TravelStopReqDto> travelStops;

    private List<String> tagList;


    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

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

    public List<TravelRouteReqDto> getTravelRoutes() {
        return travelRoutes;
    }

    public void setTravelRoutes(List<TravelRouteReqDto> travelRoutes) {
        this.travelRoutes = travelRoutes;
    }

    public List<TravelStopReqDto> getTravelStops() {
        return travelStops;
    }

    public void setTravelStops(List<TravelStopReqDto> travelStops) {
        this.travelStops = travelStops;
    }
}