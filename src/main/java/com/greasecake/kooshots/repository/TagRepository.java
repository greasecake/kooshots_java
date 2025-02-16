package com.greasecake.kooshots.repository;

import com.greasecake.kooshots.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
