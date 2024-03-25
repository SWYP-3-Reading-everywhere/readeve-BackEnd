package com.book_everywhere.domain.book.repository;

import com.book_everywhere.book.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;
    
    @Test
    void mFindBookByUserId() {
    }

    @Test
    void mFindBookIsbn() {
    }

    @Test
    void mFindBookTitle() {
    }
}