package com.greasecake.kooshots.entity;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name = "places")
public class Place {
    private String id;
    private String name;
    private String description;
    private String address;
    private Double latitude;
    private Double longitude;
    private String photoUrl;
    private MapLink mapLink;
    private Set<Tag> tags;
    private Double distance;

    public Place() {}

    public Place(Place place, Integer distance) {
        this(place, Double.valueOf(distance));
    }

    public Place(Place place, Double distance) {
        this.name = place.name;
        this.description = place.description;
        this.address = place.address;
        this.latitude = place.latitude;
        this.longitude = place.longitude;
        this.photoUrl = place.photoUrl;
        this.mapLink = place.mapLink;
        this.tags = place.tags;
        this.distance = distance;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public String getId() { return id; }

    @Column(name = "name", nullable = false)
    public String getName() { return name; }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "address", nullable = false)
    public String getAddress() {
        return address;
    }

    @Column(name = "latitude", nullable = false)
    public Double getLatitude() {
        return latitude;
    }

    @Column(name = "longitude", nullable = false)
    public Double getLongitude() {
        return longitude;
    }

    @Column(name = "photo_url", nullable = false)
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

    @Transient
    public Double getDistance() {
        return distance;
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

    public void setId(String id) {
        this.id = id;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
