package com.greasecake.kooshots.repository;

import com.greasecake.kooshots.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query("select " +
                "new com.greasecake.kooshots.entity.Place(p, " +
                "sqrt((p.latitude - :latitude)*(p.latitude - :latitude) + " +
                    "(p.longitude - :longitude)*(p.longitude - :longitude))*110.574) " +
            "from places p " +
            "where sqrt((p.latitude - :latitude)*(p.latitude - :latitude) + " +
                        "(p.longitude - :longitude)*(p.longitude - :longitude))*110.574 < :maxDistance " +
            "order by sqrt((p.latitude - :latitude)*(p.latitude - :latitude) + " +
                            "(p.longitude - :longitude)*(p.longitude - :longitude))*110.574")
    Page<Place> getPlacesByCoordinates(Double latitude, Double longitude, Double maxDistance, Pageable pageable);
}
