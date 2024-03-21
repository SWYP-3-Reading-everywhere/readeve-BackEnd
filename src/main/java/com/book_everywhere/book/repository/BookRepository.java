package com.book_everywhere.book.repository;

import com.book_everywhere.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT book FROM Book book JOIN book.reviews r WHERE r.user.id = :userId")
    List<Book> mFindBookByUserId(@Param("userId") Long userId);

    /**
     * 추가 user.id 와 book.title을 통해 일치하는 북 가져오기
     */
    @Query("SELECT book FROM Book book JOIN book.reviews r WHERE r.user.socialId = :socialId AND book.title = :title")
    Book mFindBookByUserIdAndTitle(@Param("socialId") Long socialId , @Param("title") String title);

}
