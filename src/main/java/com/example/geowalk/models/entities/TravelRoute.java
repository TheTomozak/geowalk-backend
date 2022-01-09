package com.example.geowalk.models.entities;

import com.example.geowalk.models.enums.RouteDifficulty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelRoute extends EntityBase {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RouteDifficulty difficulty;

    @Column(nullable = false, length = 300)
    private String description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_posts_travel_routes",
            joinColumns = {@JoinColumn(name = "travel_route_id")},
            inverseJoinColumns = {@JoinColumn(name = "blog_post_id")})
    private List<BlogPost> blogPosts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(name = "travel_stop_travel_route",
            joinColumns = {@JoinColumn(name = "travel_route_id")},
            inverseJoinColumns = {@JoinColumn(name = "travel_stop_id")})
    private List<TravelStop> travelStops = new ArrayList<>();

    public TravelRoute() {
    }

    public TravelRoute(String name, RouteDifficulty difficulty, String description) {
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RouteDifficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(RouteDifficulty difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public List<TravelStop> getTravelStops() {
        return travelStops;
    }

    public void setTravelStops(List<TravelStop> travelStops) {
        this.travelStops = travelStops;
    }
}
