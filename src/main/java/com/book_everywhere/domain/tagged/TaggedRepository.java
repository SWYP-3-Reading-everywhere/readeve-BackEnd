package com.book_everywhere.domain.tagged;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.web.dto.tag.TagCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaggedRepository extends JpaRepository<Tagged,Long> {
    // 선택한 태그에 1번이라도 Tag당한 핀 모두 호출
    @Query("SELECT pin FROM Pin pin JOIN pin.tags tagged WHERE tagged.tag.content = :content")
    List<Pin> mFindTaggedPin(@Param("content") String content);


    @Query("SELECT t FROM Tagged t WHERE t.tag.id = :tagId AND t.pin.id = :pinId")
    Tagged mFindTagged(@Param("tagId") Long tagId, @Param("pinId") Long pinId);

    //pin에 대한 모든 태그 개수 새기
    @Query("SELECT new com.book_everywhere.web.dto.tag.TagCountDto(t.tag.content, COUNT(t) AS count) FROM Tagged t GROUP BY t.tag.id ")
    List<TagCountDto> mFindPinTaggedCount();
}
