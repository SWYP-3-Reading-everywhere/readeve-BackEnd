package com.book_everywhere.domain.tag;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.category.Category;
import com.book_everywhere.domain.tagged.Tagged;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "tag",cascade = CascadeType.PERSIST)
    private List<Tagged> tags = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @Column(nullable = false, unique = true)
    private String content;
}
