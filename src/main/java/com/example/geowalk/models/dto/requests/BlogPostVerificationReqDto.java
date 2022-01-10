package com.example.geowalk.models.dto.requests;

public class BlogPostVerificationReqDto {

    private Long blogPostId;
    private String result;

    public BlogPostVerificationReqDto() {
    }

    public BlogPostVerificationReqDto(Long blogPostId, String result) {
        this.blogPostId = blogPostId;
        this.result = result;
    }

    public Long getBlogPostId() {
        return blogPostId;
    }

    public void setBlogPostId(Long blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
