package com.book_everywhere.domain.image;

import com.book_everywhere.domain.pin.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
