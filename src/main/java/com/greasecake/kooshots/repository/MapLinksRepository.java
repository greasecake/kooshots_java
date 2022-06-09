package com.greasecake.kooshots.repository;

import com.greasecake.kooshots.entity.MapLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MapLinksRepository extends JpaRepository<MapLink, Long> {
}
