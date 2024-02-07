package com.book_everywhere.domain.pin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Integer> {
}
