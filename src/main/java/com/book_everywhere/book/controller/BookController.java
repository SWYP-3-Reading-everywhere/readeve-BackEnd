package com.book_everywhere.book.controller;


import com.book_everywhere.book.service.BookService;
import com.book_everywhere.util.dto.CMRespDto;
import com.book_everywhere.book.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

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
    @GetMapping("/api/mypage/{socialId}")
    public CMRespDto<?> findOneBookWithUser(@PathVariable Long socialId) {
        List<BookDto> result = bookService.findAllBookOneUser(socialId);
        return new CMRespDto<>(HttpStatus.OK, result, "유저의 책 조회 완료");
    }
}
