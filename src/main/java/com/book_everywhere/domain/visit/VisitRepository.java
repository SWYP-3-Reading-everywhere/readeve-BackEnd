package com.book_everywhere.domain.visit;

import com.book_everywhere.domain.pin.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
    // 유저 아이디를 넣으면 어디에 방믄했는지 모두 가져옴 -> 나만의 지도 기능
    @Query(value = "SELECT Pin.* FROM Pin JOIN Visit ON Pin.id = Visit.pinId WHERE Visit.userId = :socialId", nativeQuery = true)
    List<Pin> mUserMap(@Param("socialId") int socialId);

    //전체핀에서 몇명이 방문했는지 알려주는 쿼리
    @Query(value ="SELECT visit.pin.id, COUNT(visit) FROM Visit visit GROUP BY visit.pin.id", nativeQuery = true)
    List<Object[]> mCountVisitPin();
}
