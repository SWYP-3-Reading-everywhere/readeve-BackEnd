package com.book_everywhere.domain.tagged;

import com.book_everywhere.domain.pin.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaggedRepository extends JpaRepository<Tagged,Integer> {
    // 선택한 태그에 1번이라도 Tag당한 핀 모두 호출
    @Query("SELECT pin FROM Pin pin JOIN pin.taggeds tagged WHERE tagged.tag.id = :tagId")
    List<Pin> mFindTaggedPin(@Param("tagId") Long tagId);

}
