package com.greasecake.kooshots.entity;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name = "tags")
public class Tag extends AbstractEntity {
    private String name;
    private String description;
    private Set<Place> places;

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    @Column(name = "name", unique = true)
    public String getName() {
        return name;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    @ManyToMany
    @JoinTable(
            name = "places_tags",
            joinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "place_id", referencedColumnName = "id"))
    public Set<Place> getPlaces() {
        return places;
    }


    public void setPlaces(Set<Place> places) {
        this.places = places;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
