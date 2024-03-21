package com.book_everywhere.domain.user;

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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user")
    private List<Tagged> taggeds = new ArrayList<>();

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
