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

    @Builder.Default
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Column(nullable = false)
    private String isbn;
    @Column(nullable = false)
    private String title;
    private String coverImageUrl;
    private boolean isComplete;
    private String author;


    @CreationTimestamp
    private Timestamp createAt;


}
