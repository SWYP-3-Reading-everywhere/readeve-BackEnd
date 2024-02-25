package com.book_everywhere.domain.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    // content를 통해 가져옴
    @Query("SELECT tag FROM Tag tag WHERE tag.name = :name")
    Tag mFindTagByName(@Param("name") String name);
}
