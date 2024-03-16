package com.book_everywhere.domain.pin;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.visit.Visit;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Pin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "pin")
    private List<Visit> visits;

    @OneToMany(mappedBy = "pin")
    private List<Review> reviews;

    @OneToMany(mappedBy = "pin")
    private List<Tagged> tags;

    @Column(nullable = false)
    private double placeId;

    //경도 x
    @Column(nullable = false)
    private double latitude;
    //위도 y
    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String url;
    @Column(nullable = false,unique = true)
    private String address;

    //최초 방문자의 생성
    @CreationTimestamp
    private Timestamp createAt;

    @Builder
    public Pin(Long id, double placeId, double latitude, double longitude, String title, String url, String address, Timestamp createAt) {
        this.id = id;
        this.visits = new ArrayList<>();
        this.reviews = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.placeId = placeId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.url = url;
        this.address = address;
        this.createAt = createAt;
    }
}
