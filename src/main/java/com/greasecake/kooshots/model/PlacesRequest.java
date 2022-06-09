package com.greasecake.kooshots.model;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

public class PlacesRequest {
    Pageable pageable;
    Double latitude;
    Double longitude;
    Integer distance;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
