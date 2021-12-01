package com.example.geowalk.models.dto.requests;

public class BlogCommentReqDto {

    private String content;
    private int rating;

    public BlogCommentReqDto() {
    }

    public BlogCommentReqDto(String content, int rating) {
        this.content = content;
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
