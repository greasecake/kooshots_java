package com.greasecake.kooshots.repository;

import com.greasecake.kooshots.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("select t from tags t where t.name = ?1")
    Tag findByName(String name);
}
