package com.book_everywhere.web;


import com.book_everywhere.service.BookService;
import com.book_everywhere.service.KakaoBookSearchService;
import com.book_everywhere.web.dto.CMRespDto;
import com.book_everywhere.web.dto.book.BookDto;
import com.book_everywhere.web.dto.book.BookSearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    //삭제 논의
//    @PostMapping("/api/book")
//    public CMRespDto<BookDto> addBook(@RequestParam Long socialId, BookDto bookDto) {
//        bookService.책생성하기(socialId, bookDto);
//        return new CMRespDto<>(HttpStatus.OK, null, "책 등록 완료");
//    }

    @PutMapping("/api/book/{id}")
    public CMRespDto<BookDto> updateBook(@PathVariable Long id, BookDto bookDto) {
        bookService.책수정하기(id, bookDto);
        return new CMRespDto<>(HttpStatus.OK, null, "책 수정 완료");
    }

    @DeleteMapping("/api/book/{id}")
    public CMRespDto<BookDto> deleteBook(@PathVariable Long id) {
        bookService.책삭제하기(id);
        return new CMRespDto<>(HttpStatus.OK, null, "책 삭제 완료");
    }
}
