package com.example.geowalk.models.dto.requests;

public class BlogCommentReqDto {

    private String content;
    private Integer rating;

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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
