package com.greasecake.kooshots.model.callback;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Locale;

@Component
public class LocationCallback implements Serializable, Callback {
    Double latitude;
    Double longitude;
    Integer pageIndex;

    public LocationCallback() {}

    public LocationCallback(Double latitude, Double longitude, Integer pageIndex) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.pageIndex = pageIndex;
    }

    public static boolean isLocationCallback(String joinedData) {
        return joinedData.toLowerCase(Locale.ROOT)
                .startsWith(CallbackType.LOCATION.name().toLowerCase(Locale.ROOT) + ";");
    }

    public void setFields(String joinedData) {
        String[] data = joinedData.split(",");
        this.latitude = Double.valueOf(data[0]);
        this.longitude = Double.valueOf(data[1]);
        this.pageIndex = Integer.valueOf(data[2]);
    }

    public CallbackType getType() {
        return CallbackType.LOCATION;
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
        return getType().name() + ";" + latitude + "," + longitude + "," + pageIndex;
    }
}
