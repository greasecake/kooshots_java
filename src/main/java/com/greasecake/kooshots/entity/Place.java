package com.greasecake.kooshots.entity;

import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name = "places")
public class Place extends AbstractEntity {
    private String name;
    private String description;
    private String address;
    private Double latitude;
    private Double longitude;
    private String photoUrl;
    private MapLink mapLink;
    private Set<Tag> tags;

    @Column(unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photo) {
        this.photoUrl = photo;
    }

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    public MapLink getMapLink() {
        return mapLink;
    }

    public void setMapLink(MapLink links) {
        this.mapLink = links;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "places_tags",
            joinColumns = @JoinColumn(name = "place_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
