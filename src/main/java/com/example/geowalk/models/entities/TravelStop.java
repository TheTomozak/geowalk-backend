package com.example.geowalk.models.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelStop extends EntityBase {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private String Country;

    @Column(nullable = false)
    private String City;

    @Column(nullable = false)
    private String Street;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "blog_posts_travel_stop",
            joinColumns = {@JoinColumn(name = "travel_stop_id")},
            inverseJoinColumns = {@JoinColumn(name = "blog_post_id")})
    private List<BlogPost> blogPosts = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "travel_stop_travel_route",
            joinColumns = {@JoinColumn(name = "travel_stop_id")},
            inverseJoinColumns = {@JoinColumn(name = "travel_route_id")})
    private List<TravelRoute> travelRoutes = new ArrayList<>();

    public TravelStop() {
    }

    public TravelStop(String name, double latitude, double longitude, String country, String city, String street) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        Country = country;
        City = city;
        Street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public List<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(List<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public List<TravelRoute> getTravelRoutes() {
        return travelRoutes;
    }

    public void setTravelRoutes(List<TravelRoute> travelRoutes) {
        this.travelRoutes = travelRoutes;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }
}
