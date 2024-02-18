package com.book_everywhere.domain.book;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.web.dto.book.BookDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
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

    private String title;
    private String coverImageUrl;
    private String author;
    private boolean isComplete;

    @CreationTimestamp
    private Timestamp createAt;

    //==생성 메서드==//
    public Book createBook(User user, BookDto bookDto) {
        Book book = Book.builder()
                .title(bookDto.getTitle())
                .coverImageUrl(bookDto.getCoverImageUrl())
                .author(bookDto.getAuthor())
                .isComplete(bookDto.getIsComplete())
                .build();
        book.setUser(user);
        return book;

    }

    //update setter
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    private void setUser(User user) {
        this.user = user;
    }
}
