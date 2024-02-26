package com.book_everywhere;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.tag.Tag;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.user.Role;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.visit.Visit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 테스트용 더미데이터 입니다.
 */
@Component
public class DummyDataGenerator implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        generateDummyData();
    }

    public void generateDummyData() {
        // 5명의 유저, 각 유저당 1개의 책, 리뷰, 핀, 태그 생성 및 연결
        for (int i = 0; i <= 5; i++) {
            User user = User.builder()
                    .socialId(3345007590L+ i)
                    .nickname("User" + i)
                    .image("http://image.url/user" + i)
                    .build();
            entityManager.persist(user);
            Tag tag = Tag.builder()
                    .content("tag" + i)
                    .build();
            entityManager.persist(tag);
        }
    }
}
