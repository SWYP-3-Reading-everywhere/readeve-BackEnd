package com.book_everywhere.domain.visit;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    //전체핀에서 몇명이 방문했는지 알려주는 쿼리
    @Query("SELECT v.pin.id, COUNT(v) FROM Visit v GROUP BY v.pin.id")
    List<Object[]> mCountVisitPin();


    //이전에 방문했는지 체크하는 코드
    @Query("SELECT v FROM Visit v WHERE v.user.id = :userId AND v.pin.id = :pinId")
    Visit mFindByUserAndPin(@Param("userId") Long userId, @Param("pinId") Long pinId);

}
