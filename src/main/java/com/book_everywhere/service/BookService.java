package com.book_everywhere.service;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.web.dto.book.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    //등록
    @Transactional
    public Long createBook(Long userId, BookDto bookDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User does not exist"));
        Book book = Book.builder()
                .user(user)
                .title(bookDto.getTitle())
                .coverImageUrl(bookDto.getCoverImageUrl())
                .author(bookDto.getAuthor())
                .isComplete(bookDto.getIsComplete())
                .build();
        bookRepository.save(book);
        return book.getId();
    }

    //수정
    @Transactional
    public void updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
        book.setTitle(bookDto.getTitle());
        book.setCoverImageUrl(bookDto.getCoverImageUrl());
        book.setAuthor(bookDto.getAuthor());
        book.setComplete(bookDto.getIsComplete());
    }

    //삭제
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
        bookRepository.delete(book);
    }

    //조회
    //특정 유저의 모든 독후감 목록 조회
    public List<Review> findAllReviewsOneUser(Long userSocialId) {
        User user = userRepository.findBySocialId(userSocialId);
        return bookRepository.findAllReviewsByUser(user);
    }

    //특정 유저의 모든 책 목록 조회
    public List<Book> findAllBookOneUser(Long userSocialId) {
        User user = userRepository.findBySocialId(userSocialId);
        return bookRepository.findAllByUser(user);
    }


    //책 한권 조회
    public Book findOneBook(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
    }

    //등록된 모든 책 조회
    public List<Book> findAllBook() {
        return bookRepository.findAll();
    }

}
