package com.book_everywhere.tag.entity;

import com.book_everywhere.tag.entity.Category;
import com.book_everywhere.tag.entity.Tagged;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL)
    private List<Tagged> tags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false, unique = true)
    private String content;
}
