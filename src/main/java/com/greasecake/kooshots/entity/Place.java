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

    @Column(name = "name", unique = true)
    public String getName() { return name; }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    @Column(name = "photo_url")
    public String getPhotoUrl() {
        return photoUrl;
    }

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    public MapLink getMapLink() {
        return mapLink;
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

    public void setMapLink(MapLink links) {
        this.mapLink = links;
    }

    public void setPhotoUrl(String photo) {
        this.photoUrl = photo;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }
}
