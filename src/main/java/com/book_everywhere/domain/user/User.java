package com.book_everywhere.domain.user;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.visit.Visit;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Visit> visits;

    @Column(nullable = false, unique = true)
    private Long socialId;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String image;
//    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    private Role role;

    @CreationTimestamp
    private Timestamp createAt;

    public User update(String nickname,String image,Role role) {
        this.nickname = nickname;
        this.image = image;
        this.role = role;
        return this;
    }
}
