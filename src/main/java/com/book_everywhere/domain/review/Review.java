package com.book_everywhere.domain.review;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.web.dto.review.ReviewDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "pinId")
    private Pin pin;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private boolean isPrivate;
    private String writer;

    @CreationTimestamp
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;

  //==연관 관계 편의 메서드==//
    private void setBook(Book book) {
        this.book = book;
        book.getReviews().add(this);
    }

    private void setPin(Pin pin) {
        this.pin = pin;
        pin.getReviews().add(this);
    }

    //==수정 메서드==//
    public void changeReview(ReviewRespDto reviewRespDto) {
        this.title = reviewRespDto.getTitle();
        this.content = reviewRespDto.getContent();
        this.isPrivate = reviewRespDto.isPrivate();
        this.writer = reviewRespDto.getWriter();
        setBook(reviewRespDto.getBookRespDto().toEntity());
        setPin(reviewRespDto.getPinRespDto().toEntity());
    }
}
