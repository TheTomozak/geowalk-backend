package com.example.geowalk.models.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class TravelStopReqDto {

    @NotBlank
    private String name;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
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
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
