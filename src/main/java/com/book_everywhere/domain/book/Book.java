package com.book_everywhere.domain.book;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.web.dto.book.BookDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String title;
    private String coverImageUrl;
    private boolean isComplete;
    private String author;


    @CreationTimestamp
    private Timestamp createAt;

    @Builder
    public Book(Long id, User user, String isbn, String title, String coverImageUrl, boolean isComplete, String author, Timestamp createAt) {
        this.id = id;
        this.user = user;
        this.isbn = isbn;
        this.title = title;
        this.coverImageUrl = coverImageUrl;
        this.isComplete = isComplete;
        this.author = author;
        this.createAt = createAt;
        this.reviews = new ArrayList<>(); // reviews 필드를 직접 초기화
    }
}
