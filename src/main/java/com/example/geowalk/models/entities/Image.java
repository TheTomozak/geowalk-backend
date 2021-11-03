package com.example.geowalk.models.entities;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class Image extends EntityBase {

    @Column(nullable = false)
    private String name;

    @Lob
    private Blob image;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private BlogPost blogPost;

    public Image() {
    }

    public Image(String name, Blob image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }
}
