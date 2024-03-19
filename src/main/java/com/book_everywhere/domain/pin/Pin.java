package com.book_everywhere.domain.pin;

import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.visit.Visit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

    @Builder.Default
    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "pin")
    private List<Review> reviews = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    private List<Tagged> tags = new ArrayList<>();

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
}
