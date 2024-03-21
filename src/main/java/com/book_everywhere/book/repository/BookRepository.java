package com.book_everywhere.book.repository;

import com.book_everywhere.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT book FROM Book book JOIN book.reviews r WHERE r.user.socialId = :socialId")
    List<Book> mFindBookByUserId(@Param("socialId") Long socialId);

    /**
     * book.title을 통해 일치하는 북 가져오기
     */
    @Query("SELECT book FROM Book book WHERE book.isbn = :isbn")
    Book mFindBookIsbn(@Param("isbn") String isbn);

    @Query("SELECT b FROM Book b WHERE b.title = :title")
    Book mFindBookTitle(@Param("title") String title);

}
