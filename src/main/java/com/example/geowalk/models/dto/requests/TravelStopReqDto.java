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
    private String Country;

    @NotBlank
    private String City;

    @NotBlank
    private String Street;

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
