package com.example.geowalk.models.dto.responses;

import com.example.geowalk.models.entities.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class BlogPostShortcutResponse {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime creationDateTime;

    private Double rateAverage;

    private Long numberOfVisits;

    private UserResDto user;

//    private List<Tag> tagList;


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
}
