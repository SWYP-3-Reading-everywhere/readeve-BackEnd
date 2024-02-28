package com.book_everywhere.web;


import com.book_everywhere.service.BookService;
import com.book_everywhere.service.KakaoBookSearchService;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.book.BookDto;
import com.book_everywhere.web.dto.book.BookSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final KakaoBookSearchService kakaoBookSearchService;

    //책 검색 기능 - 사용 o/x?
    @GetMapping("/api/search/books")
    public CMRespDto<BookSearchResultDto> searchBooks(@RequestParam String title) {
        BookSearchResultDto result = kakaoBookSearchService.searchBook(title);
        return new CMRespDto<>(HttpStatus.OK, result, "책 검색 완료");
    }
    @PutMapping("/api/book/{id}")
    public CMRespDto<?> updateBook(@PathVariable Long id, BookDto bookDto) {
        bookService.책수정하기(id, bookDto);
        return new CMRespDto<>(HttpStatus.OK, null, "책 수정 완료");
    }

    @DeleteMapping("/api/book/{id}")
    public CMRespDto<?> deleteBook(@PathVariable Long id) {
        bookService.책삭제하기(id);
        return new CMRespDto<>(HttpStatus.OK, null, "책 삭제 완료");
    }

    @GetMapping("/api/book")
    public CMRespDto<?> findAllBook() {
        List<BookDto> result = bookService.모든책조회();
        return new CMRespDto<>(HttpStatus.OK, result, "모든 책 조회 완료");
    }
    @GetMapping("/api/book/{id}")
    public CMRespDto<?> findOneBook(@PathVariable Long id) {
        BookDto result = bookService.단일책조회(id);
        return new CMRespDto<>(HttpStatus.OK, result, "단일 책 조회 완료");
    }

    //유저 책 조회
    @GetMapping("/mypage/{socialId}")
    public CMRespDto<?> findOneBookWithUser(@RequestParam Long socialId) {
        List<BookDto> result = bookService.findAllBookOneUser(socialId);
        return new CMRespDto<>(HttpStatus.OK, result, "유저의 책 조회 완료");
    }
}
