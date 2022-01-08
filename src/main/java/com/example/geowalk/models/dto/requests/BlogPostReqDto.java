package com.example.geowalk.models.dto.requests;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BlogPostReqDto {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private List<TravelRouteReqDto> travelRouteReqDtoList;

    private List<TravelStopReqDto> travelStopReqDtoList;

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

    public List<TravelRouteReqDto> getTravelRouteRequestList() {
        return travelRouteReqDtoList;
    }

    public void setTravelRouteRequestList(List<TravelRouteReqDto> travelRouteReqDtoList) {
        this.travelRouteReqDtoList = travelRouteReqDtoList;
    }

    public List<TravelStopReqDto> getTravelStopRequestList() {
        return travelStopReqDtoList;
    }

    public void setTravelStopRequestList(List<TravelStopReqDto> travelStopReqDtoList) {
        this.travelStopReqDtoList = travelStopReqDtoList;
    }
}