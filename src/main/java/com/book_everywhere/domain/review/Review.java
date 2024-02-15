package com.book_everywhere.domain.review;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.image.Image;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    private String title;
    private String content;
    @Column(nullable = false)
    private String isPrivate;
    @OneToMany
    @JoinColumn(name = "image_id")
    private List<Image> images;

    @CreationTimestamp // INSERT 시 자동으로 값을 채워줌
    @Builder.Default
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    @UpdateTimestamp // UPDATE 시 자동으로 값을 채워줌
    private LocalDateTime updatedAt = LocalDateTime.now();

}
