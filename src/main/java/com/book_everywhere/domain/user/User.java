package com.book_everywhere.domain.user;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.visit.Visit;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "user")
    private List<Book> books;

    @OneToMany(mappedBy = "user")
    private List<Visit> visits;

    private String nickname;
    private String image;
    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 저장
    private Role role;

    @CreationTimestamp
    private Timestamp createAt;

    public User update(String nickname,String image) {
        this.nickname = nickname;
        this.image = image;
        return this;
    }
}
