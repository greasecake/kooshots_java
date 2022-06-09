package com.greasecake.kooshots.model;

import java.io.*;

public class PlaceCallback implements Serializable {
    Double latitude;
    Double longitude;
    Integer pageIndex;

    public PlaceCallback() {}

    public PlaceCallback(Double latitude, Double longitude, Integer pageIndex) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.pageIndex = pageIndex;
    }

    public PlaceCallback(String joinedData) {
        String[] data = joinedData.split(":");
        this.latitude = Double.valueOf(data[0]);
        this.longitude = Double.valueOf(data[1]);
        this.pageIndex = Integer.valueOf(data[2]);

    }

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

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public String toString() {
        return latitude + ":" + longitude + ":" + pageIndex;
    }
}
