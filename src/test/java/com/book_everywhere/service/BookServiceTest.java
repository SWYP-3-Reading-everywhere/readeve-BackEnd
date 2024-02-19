package com.book_everywhere.service;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.domain.user.Role;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.web.dto.book.BookDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.grammars.hql.HqlParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserRepository userRepository;
    @PersistenceContext
    EntityManager em;

    @Test
    void 책_등록() {
        //given
        User user = new User(1L,null,123L,"닉네임","imageUrl", Role.ROLE_MEMBER, Timestamp.valueOf("2023-11-11 11:30:13"));
        userRepository.save(user);

        BookDto bookDto = new BookDto();
        bookDto.setTitle("책 이름");
        bookDto.setAuthor("저자");
        bookDto.setCoverImageUrl("http://example.com");
        bookDto.setComplete(false);
        Long bookId = bookService.createBook(user.getId(), bookDto);

        //when
        Book findBook = bookService.findOneBook(bookId);

        //then
        assertThat(findBook.getTitle()).isEqualTo(bookDto.getTitle());
        assertThat(findBook.getAuthor()).isEqualTo(bookDto.getAuthor());
        assertThat(findBook.getCoverImageUrl()).isEqualTo(bookDto.getCoverImageUrl());
        //assertThat(findBook.getUser()).isEqualTo(user); //객체의 주소값이 다르다 왜그런지 이유를 모르겠음
        //유저에 저장되어있는 값들은 모두 같다.
    }

    @Test
    void 책_수정() {
        //given
        User user = new User(1L,null,123L,"닉네임","imageUrl", Role.ROLE_MEMBER, Timestamp.valueOf("2023-11-11 11:30:13"));
        userRepository.save(user);

        BookDto bookDto = new BookDto();
        bookDto.setTitle("책 이름");
        bookDto.setAuthor("저자");
        bookDto.setCoverImageUrl("http://example.com");
        bookDto.setComplete(false);
        Long bookId = bookService.createBook(user.getId(), bookDto);

        Book findBook = bookService.findOneBook(bookId);
        BookDto updateBookDto = new BookDto();
        updateBookDto.setTitle("새로운 책이름");
        updateBookDto.setAuthor("새로운 저자");
        updateBookDto.setCoverImageUrl("http://example.com");
        updateBookDto.setComplete(false);

        //when
        bookService.updateBook(findBook.getId(), updateBookDto);
        em.flush();
        em.close();

        //then
        //책 이름이 "새로운 책이름"으로 변경되어야 한다.
        assertThat(findBook.getTitle()).isEqualTo("새로운 책이름");
        //책 저자가 "새로운 저자"로 변경되어야 한다.
        assertThat(findBook.getAuthor()).isEqualTo("새로운 저자");
        //책 coverImageUrl은 변경되면 안된다.
        assertThat(findBook.getCoverImageUrl()).isEqualTo("http://example.com");
    }

    @Test
    void 책_삭제() {
        //given
        User user = new User(1L,null,123L,"닉네임","imageUrl", Role.ROLE_MEMBER, Timestamp.valueOf("2023-11-11 11:30:13"));
        userRepository.save(user);

        BookDto bookDto = new BookDto();
        bookDto.setTitle("책 이름");
        bookDto.setAuthor("저자");
        bookDto.setCoverImageUrl("http://example.com");
        bookDto.setComplete(false);
        Long bookId = bookService.createBook(user.getId(), bookDto);

        //when
        bookService.deleteBook(bookId);

        //then
        assertThrows(IllegalArgumentException.class, () -> bookService.findOneBook(bookId));

    }

    @Test
    @Transactional(readOnly = true)
    void 특정_유저_모든_책_조회() {
        //given
        User user = new User(1L,null,123L,"닉네임","imageUrl", Role.ROLE_MEMBER, Timestamp.valueOf("2023-11-11 11:30:13"));
        userRepository.save(user);

        BookDto bookDto = new BookDto();
        bookDto.setTitle("책 이름");
        bookDto.setAuthor("저자");
        bookDto.setCoverImageUrl("http://example.com");
        bookDto.setComplete(false);
        bookService.createBook(user.getId(), bookDto);

        BookDto bookDto2 = new BookDto();
        bookDto2.setTitle("책 이름");
        bookDto2.setAuthor("저자");
        bookDto2.setCoverImageUrl("http://example.com");
        bookDto2.setComplete(false);
        bookService.createBook(user.getId(), bookDto2);

        BookDto bookDto3 = new BookDto();
        bookDto3.setTitle("책 이름");
        bookDto3.setAuthor("저자");
        bookDto3.setCoverImageUrl("http://example.com");
        bookDto3.setComplete(false);
        bookService.createBook(user.getId(), bookDto3);

        //when
        List<Book> allBookOneUser = bookService.findAllBookOneUser(user.getSocialId());


        //then
        assertThat(allBookOneUser.size()).isEqualTo(3);


    }
}