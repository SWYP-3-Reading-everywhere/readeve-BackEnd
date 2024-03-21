package com.book_everywhere.tag.repository;

import com.book_everywhere.tag.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
