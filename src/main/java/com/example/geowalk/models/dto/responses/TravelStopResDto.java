package com.example.geowalk.models.dto.responses;

import javax.persistence.Column;

public class TravelStopResDto {

    private String name;

    private double latitude;

    private double longitude;

    private String country;

    private String city;

    private String street;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        street = street;
    }
}
