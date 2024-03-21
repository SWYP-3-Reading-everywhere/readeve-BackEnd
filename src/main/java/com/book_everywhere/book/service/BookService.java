package com.book_everywhere.book.service;

import com.book_everywhere.book.entity.Book;
import com.book_everywhere.book.repository.BookRepository;
import com.book_everywhere.book.dto.BookDto;
import com.book_everywhere.book.dto.BookRespDto;
import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.exception.customs.CustomErrorCode;
import com.book_everywhere.exception.customs.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    //등록 혹은 수정
    @Transactional
    public void 책생성(ReviewRespDto reviewRespDto) {
        BookRespDto bookRespDto = reviewRespDto.getBookRespDto();
        Book book = bookRepository.mFindBookIsbn(bookRespDto.getIsbn());
        if (book == null) {
            Book newBook = bookRespDto.toEntity();
            bookRepository.save(newBook);
        }
    }

    //조회
    //특정 유저의 모든 책 목록 조회
    @Transactional
    public List<BookDto> findAllBookOneUser(Long socialId) {
        List<Book> init = bookRepository.mFindBookByUserId(socialId);
        if(init.isEmpty()) {
            throw new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND);
        }
        return init.stream().map(book -> new BookDto(
                book.getTitle(),
                book.getCoverImageUrl(),
                book.getIsbn(),
                book.getCreateAt())).toList();
    }


    //책 한권 조회
    @Transactional
    public BookDto 단일책조회(Long id) {
        Book init = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND));
        return new BookDto(
                init.getTitle(),
                init.getCoverImageUrl(),
                init.getIsbn(),
                init.getCreateAt());
    }

    //등록된 모든 책 조회
    @Transactional
    public List<BookDto> 모든책조회() {
        List<Book> init = bookRepository.findAll();

        return init.stream().map(book -> new BookDto(
                book.getTitle(),
                book.getCoverImageUrl(),
                book.getIsbn(),
                book.getCreateAt())).toList();
    }

}
