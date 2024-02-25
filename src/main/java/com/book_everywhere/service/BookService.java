package com.book_everywhere.service;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.web.dto.book.BookDto;
import com.book_everywhere.web.dto.book.BookRespDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    //등록
    @Transactional
    public void 책생성하기(ReviewRespDto reviewRespDto) {
        User user = userRepository.findBySocialId(reviewRespDto.getSocialId()).orElseThrow();
        BookRespDto bookRespDto = reviewRespDto.getBookRespDto();
        Book userBook = bookRepository.mFindBookByUserIdAndTitle(user.getSocialId(), bookRespDto.getTitle());
        if (userBook == null) {
            Book book = Book.builder()
                    .user(user)
                    .isbn(bookRespDto.getIsbn())
                    .coverImageUrl(bookRespDto.getThumbnail())
                    .title(bookRespDto.getTitle())
                    .isComplete(bookRespDto.isComplete())
                    .build();

            bookRepository.save(book);
        }
    }

    //수정
    @Transactional
    public void 책수정하기(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
        book.setTitle(bookDto.getTitle());
        book.setCoverImageUrl(bookDto.getCoverImageUrl());
        book.setComplete(bookDto.getIsComplete());
    }

    //삭제
    @Transactional
    public void 책삭제하기(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
        bookRepository.delete(book);
    }

    //조회
    //특정 유저의 모든 책 목록 조회
    @Transactional
    public List<BookDto> findAllBookOneUser(Long userSocialId) {
        User user = userRepository.findBySocialId(userSocialId).orElseThrow();
        List<Book> init = bookRepository.findAllByUser(user);
        return init.stream().map(book -> new BookDto(
                book.getUser().getId(),
                book.getTitle(),
                book.getCoverImageUrl(),
                book.getIsbn(),
                book.isComplete(),
                book.getCreateAt())).toList();
    }


    //책 한권 조회
    @Transactional
    public BookDto 단일책조회(Long id) {
        Book init = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
        return new BookDto(
                init.getUser().getId(),
                init.getTitle(),
                init.getCoverImageUrl(),
                init.getIsbn(),
                init.isComplete(),
                init.getCreateAt());
    }

    //등록된 모든 책 조회
    @Transactional
    public List<BookDto> 모든책조회() {
        List<Book> init = bookRepository.findAll();
        return init.stream().map(book -> new BookDto(
                book.getUser().getId(),
                book.getTitle(),
                book.getCoverImageUrl(),
                book.getIsbn(),
                book.isComplete(),
                book.getCreateAt())).toList();
    }

}
