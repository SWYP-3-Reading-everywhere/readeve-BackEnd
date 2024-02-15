package com.book_everywhere.domain.pin;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.review.Review;
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

import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "pin")
    private List<Book> books;
    //경도 x
    @Column(nullable = false)
    private double latitude;
    //위도 y
    @Column(nullable = false)
    private double longitude;

    private String title;
    @Column(length = 100)
    private String memo;
    private String tag;

    //최초 방문자의 생성
    @CreationTimestamp
    private Timestamp createAt;

}
