package com.example.geowalk.models.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class BlogPost extends EntityBase {

    @Column(nullable = false, length = 200)
    private String shortDescription;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime creationDateTime = LocalDateTime.now();

    @Column(nullable = true)
    private LocalDateTime lastEditDateTime;

    @Column(nullable = false)
    private boolean needToVerify = false;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "blogPost", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<BlogComment> blogComments = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_posts_travel_stop",
            joinColumns = {@JoinColumn(name = "blog_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "travel_stop_id")})
    private List<TravelStop> travelStops = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_posts_travel_routes",
            joinColumns = {@JoinColumn(name = "blog_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "travel_route_id")})
    private List<TravelRoute> travelRoutes = new ArrayList<>();

    @OneToMany(mappedBy = "blogPost", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Image> images = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_posts_tags",
            joinColumns = {@JoinColumn(name = "blog_post_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")})
    private List<Tag> tags = new ArrayList<>();

    private Long numberOfVisits;

    public BlogPost(String content, String title, String shortDescription) {
        this.content = content;
        this.title = title;
        this.shortDescription = shortDescription;
        numberOfVisits = 0L;
    }

    public BlogPost() {
        super();
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public LocalDateTime getLastEditDateTime() {
        return lastEditDateTime;
    }

    public void setLastEditDateTime(LocalDateTime lastEditDateTime) {
        this.lastEditDateTime = lastEditDateTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<BlogComment> getBlogComments() {
        return blogComments;
    }

    public void setBlogComments(List<BlogComment> blogComments) {
        this.blogComments = blogComments;
    }

    public List<TravelStop> getTravelStops() {
        return travelStops;
    }

    public void setTravelStops(List<TravelStop> travelStops) {
        this.travelStops = travelStops;
    }

    public List<TravelRoute> getTravelRoutes() {
        return travelRoutes;
    }

    public void setTravelRoutes(List<TravelRoute> travelRoutes) {
        this.travelRoutes = travelRoutes;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Long getNumberOfVisits() {
        return numberOfVisits;
    }

    public void setNumberOfVisits(Long numberOfVisits) {
        this.numberOfVisits = numberOfVisits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isNeedToVerify() {
        return needToVerify;
    }

    public void setNeedToVerify(boolean needToVerify) {
        this.needToVerify = needToVerify;
    }

    public Double getAverageRate() {
        double sum = blogComments.stream().map(BlogComment::getRating).mapToInt(Integer::intValue).sum();
        double values = (double) blogComments.stream().map(BlogComment::getRating).count();
        return sum / values;
    }


}
