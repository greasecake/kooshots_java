package com.greasecake.kooshots.model;

public class PlacesRequest {
    Double latitude;
    Double longitude;
    Integer pageIndex;
    Integer pageSize;

    public PlacesRequest() {}

    public PlacesRequest(Double latitude, Double longitude, Integer pageIndex, Integer pageSize) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
