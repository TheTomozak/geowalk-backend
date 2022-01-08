package com.example.geowalk.models.dto.responses;

import java.time.LocalDateTime;

public class BlogCommentResDto {

    private long id;

    private String content;

    private LocalDateTime creationDateTime;

    private int rating;

    private UserResDto user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public UserResDto getUser() {
        return user;
    }

    public void setUser(UserResDto user) {
        this.user = user;
    }
}
