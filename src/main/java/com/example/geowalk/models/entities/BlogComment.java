package com.example.geowalk.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BlogComment extends EntityBase {

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime creationDateTime = LocalDateTime.now();

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private boolean needToVerify = false;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "blog_post_id")
    private BlogPost blogPost;

    public BlogComment() {
    }

    public BlogComment(String content, int rating) {
        this.content = content;
        this.rating = rating;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public boolean isNeedToVerify() {
        return needToVerify;
    }

    public void setNeedToVerify(boolean needToVerify) {
        this.needToVerify = needToVerify;
    }
}
