package com.example.geowalk.models.dto.responses;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPostShortResDto {

    private Long id;

    private String title;

    private String shortDescription;

    private String content;

    private LocalDateTime creationDateTime;

    private Double rateAverage;

    private Long numberOfVisits;

    private UserResDto user;

    private List<TagResDto> tags;

    private Long imageId;

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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

    public UserResDto getUser() {
        return user;
    }

    public void setUser(UserResDto user) {
        this.user = user;
    }

    public Double getRateAverage() {
        return rateAverage;
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

    public void setRateAverage(Double rateAverage) {
        this.rateAverage = rateAverage;
    }

    public List<TagResDto> getTags() {
        return tags;
    }

    public void setTags(List<TagResDto> tags) {
        this.tags = tags;
    }
}
