package com.greasecake.kooshots.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Table
@Entity(name = "map_links")
public class MapLink extends AbstractEntity {
    private Place place;
    private String yandex;
    private String google;

    @OneToOne
    @JoinColumn(name = "place_id")
    @JsonIgnore
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place placeId) {
        this.place = placeId;
    }

    public String getYandex() {
        return yandex;
    }

    public void setYandex(String yandex) {
        this.yandex = yandex;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }
}
