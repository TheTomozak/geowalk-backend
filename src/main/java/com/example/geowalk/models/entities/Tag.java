package com.example.geowalk.models.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag extends EntityBase {

    @Column(nullable = false)
    private String name;

    private long occurrenceNumber = 1;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_posts_tags",
            joinColumns = {@JoinColumn(name = "tag_id")},
            inverseJoinColumns = {@JoinColumn(name = "blog_post_id")})
    private List<BlogPost> blogPosts = new ArrayList<>();

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public long getOccurrenceNumber() {
        return occurrenceNumber;
    }

    public void setOccurrenceNumber(long occurrenceNumber) {
        this.occurrenceNumber = occurrenceNumber;
    }
}
