package com.example.geowalk.models.dto.requests;

public class BlogCommentVerificationReqDto {

    private Long blogCommentId;
    private String result;

    public BlogCommentVerificationReqDto() {
    }

    public BlogCommentVerificationReqDto(Long blogCommentId, String result) {
        this.blogCommentId = blogCommentId;
        this.result = result;
    }

    public Long getBlogCommentId() {
        return blogCommentId;
    }

    public void setBlogCommentId(Long blogCommentId) {
        this.blogCommentId = blogCommentId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
