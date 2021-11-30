package com.example.geowalk.models.dto.responses;

import com.example.geowalk.models.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BlogPostResponse {

    private String content;

    private LocalDateTime creationDateTime;

    private LocalDateTime lastEditDateTime;

    private UserResDto user;

    private List<BlogCommentResponse> blogComments;

    private List<TravelStopResponse> travelStops;
//
    private List<TravelRouteResponse> travelRoutes;

//    private List<Image> images;
//    private List<Tag> tags;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getLastEditDateTime() {
        return lastEditDateTime;
    }

    public void setLastEditDateTime(LocalDateTime lastEditDateTime) {
        this.lastEditDateTime = lastEditDateTime;
    }

    public UserResDto getUser() {
        return user;
    }

    public void setUser(UserResDto user) {
        this.user = user;
    }

    public List<BlogCommentResponse> getBlogComments() {
        return blogComments;
    }

    public void setBlogComments(List<BlogCommentResponse> blogComments) {
        this.blogComments = blogComments;
    }

    public List<TravelStopResponse> getTravelStops() {
        return travelStops;
    }

    public void setTravelStops(List<TravelStopResponse> travelStops) {
        this.travelStops = travelStops;
    }

    public List<TravelRouteResponse> getTravelRoutes() {
        return travelRoutes;
    }

    public void setTravelRoutes(List<TravelRouteResponse> travelRoutes) {
        this.travelRoutes = travelRoutes;
    }
}
