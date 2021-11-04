package com.example.geowalk.models.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TravelStopRequest {

    @NotBlank
    private String name;

    @NotNull
    private double latitude;

    @NotNull
    private double longitude;

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
}
