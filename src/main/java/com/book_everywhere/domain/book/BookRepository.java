package com.book_everywhere.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {


    //유저의 모든 책 조회
    List<Book> findAllByUserId(Long userId);

    /**
     * 추가 user.id 와 book.title을 통해 일치하는 북 가져오기
     */
    @Query("SELECT book FROM Book book WHERE book.user.socialId = :socialId AND book.title = :title")
    Book mFindBookByUserIdAndTitle(@Param("socialId") Long socialId , @Param("title") String title);

}
