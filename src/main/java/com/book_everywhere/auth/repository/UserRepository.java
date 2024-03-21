package com.book_everywhere.auth.repository;

import com.book_everywhere.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //소셜아이디를 통해 식별함
    Optional<User> findBySocialId(Long socialId);
}
