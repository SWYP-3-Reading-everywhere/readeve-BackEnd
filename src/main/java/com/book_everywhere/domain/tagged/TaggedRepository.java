package com.book_everywhere.domain.tagged;

import com.book_everywhere.domain.pin.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaggedRepository extends JpaRepository<Tagged,Long> {
    // 선택한 태그에 1번이라도 Tag당한 핀 모두 호출
    @Query("SELECT pin FROM Pin pin JOIN pin.tags tagged WHERE tagged.tag.name = :name")
    List<Pin> mFindTaggedPin(@Param("name") String name);


    @Query("SELECT t FROM Tagged t WHERE t.tag.id = :tagId AND t.pin.id = :pinId")
    Tagged mFindTagged(@Param("tagId") Long tagId, @Param("pinId") Long pinId);
}
