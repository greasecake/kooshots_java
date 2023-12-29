package com.greasecake.kooshots.repository;

import com.greasecake.kooshots.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Modifying
    @Query("update places p set p.name = ?1 where upper(p.name) = upper(?2)")
    int updateNameByNameAllIgnoreCase(String name, String name1);
}
