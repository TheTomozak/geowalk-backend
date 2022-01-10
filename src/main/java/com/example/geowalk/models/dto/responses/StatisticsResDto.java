package com.example.geowalk.models.dto.responses;

public class StatisticsResDto {
    private Integer postsTotalNumber;
    private Integer commentsTotalNumber;
    private Integer tagsTotalNumber;

    public StatisticsResDto() {
    }

    public StatisticsResDto(Integer postsTotalNumber, Integer commentsTotalNumber, Integer tagsTotalNumber) {
        this.postsTotalNumber = postsTotalNumber;
        this.commentsTotalNumber = commentsTotalNumber;
        this.tagsTotalNumber = tagsTotalNumber;
    }

    public Integer getPostsTotalNumber() {
        return postsTotalNumber;
    }

    public void setPostsTotalNumber(Integer postsTotalNumber) {
        this.postsTotalNumber = postsTotalNumber;
    }

    public Integer getCommentsTotalNumber() {
        return commentsTotalNumber;
    }

    public void setCommentsTotalNumber(Integer commentsTotalNumber) {
        this.commentsTotalNumber = commentsTotalNumber;
    }

    public Integer getTagsTotalNumber() {
        return tagsTotalNumber;
    }

    public void setTagsTotalNumber(Integer tagsTotalNumber) {
        this.tagsTotalNumber = tagsTotalNumber;
    }
}
