package com.book_everywhere.tag.entity;

import com.book_everywhere.tag.entity.Tag;
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

    @Builder.Default
    @OneToMany(mappedBy = "category")
    private List<Tag> tag = new ArrayList<>();

    private String name;


}
