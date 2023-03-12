package com.greasecake.kooshots.docs;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseRow {
    private String id;
    private String hide;
    private String name;
    private String tags;
    private String description;
    private String address;
    private String coordinates;
    private String photo;
    @JsonProperty("google_link")
    private String googleLink;
    @JsonProperty("yandex_link")
    private String yandexLink;
    @JsonProperty("2gis_link")
    private String gisLink;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getGoogleLink() {
        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        this.googleLink = googleLink;
    }

    public String getYandexLink() {
        return yandexLink;
    }

    public void setYandexLink(String yandexLink) {
        this.yandexLink = yandexLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGisLink() {
        return gisLink;
    }

    public void setGisLink(String gisLink) {
        this.gisLink = gisLink;
    }

    public String getHide() {
        return hide;
    }

    public void setHide(String hide) {
        this.hide = hide;
    }
}
