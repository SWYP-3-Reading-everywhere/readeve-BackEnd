package com.book_everywhere.domain.pin;

import com.book_everywhere.web.dto.pin.PinDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PinRepository extends JpaRepository<Pin, Long> {
    // 유저 아이디를 넣으면 어디에 방믄했는지 모두 가져옴 -> 나만의 지도 기능
    @Query("SELECT pin FROM Pin pin JOIN pin.visits visit WHERE visit.user.socialId = :socialId")
    List<Pin> mUserMap(@Param("socialId") Long socialId);


    // 그냥 핀 하나 찾아오는 쿼리
    @Query("SELECT pin FROM Pin pin WHERE pin.id = :pinId")
    Pin mFindByPinId(@Param("pinId") Long pinId);

    // 모든핀을 찾아옴
    @Query("SELECT pin FROM Pin pin")
    List<Pin> mFindAllPin();


    /**
     * 추가 도로명 주소를 통한 Pin 생성 설계
     */
    @Query("SELECT pin FROM Pin pin WHERE pin.address = :address")
    Pin  mFindPinByAddress(@Param("address") String address);
}
