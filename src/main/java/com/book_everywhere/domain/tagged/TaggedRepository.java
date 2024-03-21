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
    @Query("SELECT t FROM Tagged t WHERE t.tag.id = :tagId AND t.pin.id = :pinId")
    Tagged mFindTagged(@Param("tagId") Long tagId, @Param("pinId") Long pinId);

    //pin에 tag들에 대한 count개수 상위 5개
    @Query("SELECT t.tag.content, COUNT(tagged) FROM tagged t  WHERE t.pin.id = :pinId GROUP BY t.tag.content ORDER BY count DESC")
    Page<Object[]> mCountByPinId(@Param("pinId") Long pinId, Pageable pageable);

    //핀에 해당하는 모든 태그 조회하기 - WebDataService에서 사용하는 용도
     List<Tagged> findAllByPinId(Long pinId);

     @Query("SELECT t FROM Tagged t WHERE t.pin.id = :pinId AND t.user.socialId = :socialId")
     List<Tagged> mFindAllByPinAndUser(@Param("pinId") Long pinId, @Param("socialId") Long socialId);
}