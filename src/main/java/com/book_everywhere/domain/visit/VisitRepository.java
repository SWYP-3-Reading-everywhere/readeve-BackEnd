package com.book_everywhere.domain.visit;

import com.book_everywhere.domain.pin.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

    //전체핀에서 몇명이 방문했는지 알려주는 쿼리
    @Query(value ="SELECT visit.pin.id, COUNT(visit) FROM Visit visit GROUP BY visit.pin.id", nativeQuery = true)
    List<Object[]> mCountVisitPin();
}
