package com.book_everywhere.domain.tagged;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaggedRepository extends JpaRepository<Tagged,Integer> {
}
