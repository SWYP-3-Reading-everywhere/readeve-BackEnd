package com.book_everywhere.domain.pin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PinRepository extends JpaRepository<Pin, Integer> {
    // 유저 아이디를 넣으면 어디에 방믄했는지 모두 가져옴 -> 나만의 지도 기능
    @Query(value = "SELECT Pin.* FROM Pin JOIN Visit ON Pin.id = Visit.pinId WHERE Visit.userId = :socialId", nativeQuery = true)
    List<Pin> mUserMap(@Param("socialId") Long socialId);

    Optional<Pin> findByPinId(Long pinId);
}
