package com.example.geowalk.models.entities;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Image extends EntityBase {

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String url;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private BlogPost blogPost;

    public Image() {
    }

    public Image(String name) {
        this.name = name;
    }

    public Image(String name, String url) {
        this.name = name;
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }
}
