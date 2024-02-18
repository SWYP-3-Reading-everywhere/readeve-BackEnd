package com.book_everywhere.service;

import com.book_everywhere.domain.review.Review;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;
    @Test
    void 모든_독후감_찾기() {
    }

    @Test
    void 독후감_하나_찾기() {
    }
}