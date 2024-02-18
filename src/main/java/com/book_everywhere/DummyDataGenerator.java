package com.book_everywhere;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.tag.Tag;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.visit.Visit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        for (int i = 1; i <= 5; i++) {
            User user = User.builder()
                    .socialId(1000L + i)
                    .nickname("User" + i)
                    .image("http://image.url/user" + i)
                    .build();
            entityManager.persist(user);

            Book book = Book.builder()
                    .user(user)
                    .title("Book Title " + i)
                    .coverImageUrl("http://image.url/bookcover" + i)
                    .author("Author" + i)
                    .isComplete(i % 2 == 0)
                    .build();
            entityManager.persist(book);

            Pin pin = Pin.builder()
                    .latitude(37.5665 + i)
                    .longitude(126.9780 + i)
                    .title("Pin Title " + i)
                    .address("Seoul " + i)
                    .build();
            entityManager.persist(pin);

            Tag tag = Tag.builder()
                    .content("Tag Content " + i)
                    .build();
            entityManager.persist(tag);

            Tagged tagged = Tagged.builder()
                    .pin(pin)
                    .tag(tag)
                    .build();
            entityManager.persist(tagged);

            Review review = Review.builder()
                    .book(book)
                    .pin(pin)
                    .title("Review Title " + i)
                    .content("Review Content " + i)
                    .isPrivate(false)
                    .build();
            entityManager.persist(review);

            Visit visit = Visit.builder()
                    .user(user)
                    .pin(pin)
                    .isPrivate(false)
                    .build();
            entityManager.persist(visit);
        }
    }
}
