package com.book_everywhere.review.entity;

import com.book_everywhere.auth.entity.User;
import com.book_everywhere.book.entity.Book;
import com.book_everywhere.pin.entity.Pin;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pin_id")
    private Pin pin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 1500)
    private String content;
    @Column(nullable = false)
    private boolean isPrivate;
    @Column(nullable = false)
    private String writer;
    @Column(nullable = false)
    private boolean isBookComplete;

    @CreationTimestamp
    private Timestamp createAt;

    @UpdateTimestamp
    private Timestamp updateAt;

  //==연관 관계 편의 메서드==//
    public void setBook(Book book) {
        if(this.book != null) {
            this.book.getReviews().remove(this);
        }
        this.book = book;
        book.getReviews().add(this);
    }

    public void setUser(User user) {
        if(this.user != null) {
            this.user.getReviews().remove(this);
        }
        this.user = user;
        user.getReviews().add(this);
    }

    public void setPin(Pin pin) {
        if(this.pin != null) {
            this.pin.getReviews().remove(this);
        }
        this.pin = pin;
        pin.getReviews().add(this);
    }

    //==수정 메서드==//
    public void changeReview(String title, String content, boolean isPrivate, boolean isBookComplete, String writer) {
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
        this.isBookComplete = isBookComplete;
        this.writer = writer;
    }
}
