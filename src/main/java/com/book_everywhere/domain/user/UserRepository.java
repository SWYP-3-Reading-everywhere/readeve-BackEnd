package com.book_everywhere.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //소셜아이디를 통해 식별함
    Optional<User> findBySocialId(Long socialId);
}
