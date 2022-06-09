package com.greasecake.kooshots.repository;

import com.greasecake.kooshots.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query( "select p " +
            "from places p " +
            "where sqrt((p.latitude - ?1)*(p.latitude - ?1) + (p.longitude - ?2)*(p.longitude - ?2))*110.574 < ?3 order by sqrt((p.latitude - ?1)*(p.latitude - ?1) + (p.longitude - ?2)*(p.longitude - ?2))*110.574")
    Page<Place> getPlacesByCoordinates(Double latitude, Double longitude, Double maxDistance, Pageable pageable);
}
