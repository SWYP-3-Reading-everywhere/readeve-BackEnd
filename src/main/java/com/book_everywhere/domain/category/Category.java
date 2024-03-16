package com.book_everywhere.domain.category;

import com.book_everywhere.domain.tag.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<Tag> tag;

    private String name;

    @Builder
    public Category(Long id, String name) {
        this.id = id;
        this.tag = new ArrayList<>();
        this.name = name;
    }
}
