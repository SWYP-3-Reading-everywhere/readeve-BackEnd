package com.book_everywhere.domain.tagged;

import com.book_everywhere.domain.pin.Pin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
// Tagged 엔티티의 Repository 인터페이스 내에 메소드 추가
    Page<Tagged> findByPinIdOrderByCountDesc(Long pinId, Pageable pageable);

    @Query("SELECT t FROM Tagged t WHERE t.pin.id = :pinId ORDER BY t.count DESC")
    List<Tagged> mFindPinWithTagCount(@Param("pinId") Long pinId, Pageable pageable);

    //isPrivate 추가
    @Query("SELECT t FROM Tagged t WHERE t.pin.id = :pinId AND t.pin.visit.isPinPrivate = :isPrivate ORDER BY t.count DESC")
    List<Tagged> mFindPinAndIsPrivateWithTagCount(@Param("pinId") Long pinId, @Param("isPrivate") boolean isPrivate, Pageable pageable);


    @Modifying
    @Query("DELETE FROM Tagged WHERE id = :taggedId")
    int mDeleteTagged(@Param("taggedId") Long taggedId);

    //핀에 해당하는 모든 태그 조회하기 - WebDataService에서 사용하는 용도
     List<Tagged> findAllByPinId(Long pinId);
}
