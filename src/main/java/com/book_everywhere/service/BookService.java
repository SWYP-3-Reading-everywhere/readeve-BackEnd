package com.book_everywhere.service;

import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
}
