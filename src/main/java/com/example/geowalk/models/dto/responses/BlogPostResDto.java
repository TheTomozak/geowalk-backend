package com.example.geowalk.models.dto.responses;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPostResDto {

    private Long id;

    private String title;

    private String shortDescription;

    private String content;

    private LocalDateTime creationDateTime;

    private LocalDateTime lastEditDateTime;

    private UserResDto user;

    private List<BlogCommentResDto> blogComments;

    private List<TravelStopResDto> travelStops;

    private List<TravelRouteResDto> travelRoutes;

    private Long numberOfVisits;

    private Double rateAverage;

//    private List<Image> images;
    private List<TagResDto> tags;


    public Double getRateAverage() {
        return rateAverage;
    }

    public void setRateAverage(Double rateAverage) {
        this.rateAverage = rateAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

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

    public List<BlogCommentResDto> getBlogComments() {
        return blogComments;
    }

    public void setBlogComments(List<BlogCommentResDto> blogComments) {
        this.blogComments = blogComments;
    }

    public List<TravelStopResDto> getTravelStops() {
        return travelStops;
    }

    public void setTravelStops(List<TravelStopResDto> travelStops) {
        this.travelStops = travelStops;
    }

    public List<TravelRouteResDto> getTravelRoutes() {
        return travelRoutes;
    }

    public void setTravelRoutes(List<TravelRouteResDto> travelRoutes) {
        this.travelRoutes = travelRoutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(Long numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public List<TagResDto> getTags() {
        return tags;
    }

    public void setTags(List<TagResDto> tags) {
        this.tags = tags;
    }
}
