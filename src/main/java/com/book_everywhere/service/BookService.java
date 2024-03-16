package com.book_everywhere.service;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.web.dto.book.BookDto;
import com.book_everywhere.web.dto.book.BookRespDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import com.book_everywhere.web.exception.customs.CustomErrorCode;
import com.book_everywhere.web.exception.customs.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    //등록 혹은 수정
    @Transactional
    public void 책생성또는수정(ReviewRespDto reviewRespDto) {
        User user = userRepository.findBySocialId(reviewRespDto.getSocialId()).orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.USER_NOT_FOUND));
        BookRespDto bookRespDto = reviewRespDto.getBookRespDto();
        Book userBook = bookRepository.mFindBookByUserIdAndTitle(user.getSocialId(), bookRespDto.getTitle());
        if (userBook == null) {
            Book book = bookRespDto.toEntity(user);
            bookRepository.save(book);
        }
    }

    //조회
    //특정 유저의 모든 책 목록 조회
    @Transactional
    public List<BookDto> findAllBookOneUser(Long userSocialId) {
        User user = userRepository.findBySocialId(userSocialId).orElseThrow(
                () -> new EntityNotFoundException(CustomErrorCode.USER_NOT_FOUND));
        List<Book> init = bookRepository.findAllByUserId(user.getId());
        if(init.isEmpty()) {
            throw new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND);
        }
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
        Book init = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND));
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
