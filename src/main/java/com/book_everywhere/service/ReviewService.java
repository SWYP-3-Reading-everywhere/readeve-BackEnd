package com.book_everywhere.service;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.web.dto.book.BookDto;
import org.springframework.data.domain.Pageable;
import com.book_everywhere.web.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final PinRepository pinRepository;


    //사용자 검증에 메소드
    //등록
    @Transactional
    public Long createReview(Long bookId, Long pinId, ReviewDto reviewDto) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                ()-> new IllegalArgumentException("Book does not exist"));
        Pin pin = pinRepository.findById(pinId).orElseThrow(
                () -> new IllegalArgumentException("Pin does not exist"));

        Review review = new Review().createFromDto(book, pin, reviewDto);
        reviewRepository.save(review);
        return review.getId();
    }


    //수정
    @Transactional
    public void updateReview(Long id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review does not exist"));
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setIsPrivate(reviewDto.getIsPrivate());
    }

    //조회
    //특정 유저의 특정 책에 등록된 독후감 조회 기능
//     public List<ReviewDto> findReviewsByUserAndBook(Long bookId) {
//         Book book = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("Book does not exist"));
//         List<Review> init = reviewRepository.findReviewsByUserAndBook(book.getUser().getId(), book.getId());
//         return init.stream().map(review ->
//                 new ReviewDto(review.getTitle(),
//                 review.getContent(),
//                 review.getIsPrivate(),
//                 review.getCreatedAt(),
//                 review.getUpdatedAt())).toList();
//     }

    //공유 목록에서의 독후감 조회
    public List<ReviewDto> findPublicReviews(boolean isPrivate, Pageable pageable) {
        List<Review> init = reviewRepository.findByIsPrivate(isPrivate, pageable);
        return init.stream().map(review -> new ReviewDto(review.getTitle(),
                review.getContent(),
                review.getIsPrivate(),
                review.getCreatedAt(),
                review.getUpdatedAt())).toList();
    //단일 핀을 눌렀을때 독후감이 조회됩니다.
    @Transactional(readOnly = true)
    public List<ReviewDto> 단일핀독후감조회(Long pinId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> init = reviewRepository.mFindReviewUserMap((Long)oAuth2User.getAttributes().get("id"),pinId);

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(),review.getTitle(),review.getContent(),review.isPrivate(),review.getUpdateAt(),review.getCreateAt()))
                .toList();

        return resultDto;
    }
    @Transactional(readOnly = true)
    public List<ReviewDto> 유저모든독후감조회(@AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> init = reviewRepository.mFindReviewsByUser((Long)oAuth2User.getAttributes().get("id"));

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(),review.getTitle(),review.getContent(),review.isPrivate(),review.getUpdateAt(),review.getCreateAt()))
                .toList();



    //리뷰 하나만 조회
    public ReviewDto findOneReview(Long id) {
        Review init = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review does not exist"));
        return new ReviewDto(init.getTitle(),
                init.getContent(),
                init.getIsPrivate(),
                init.getCreatedAt(),
                init.getUpdatedAt());
    }

    //등록된 모든 리뷰 조회
    public List<ReviewDto> findReviews() {
        List<Review> init = reviewRepository.findAll();
        return init.stream().map(review ->
                new ReviewDto(
                        review.getTitle(),
                        review.getContent(),
                        review.getIsPrivate(),
                        review.getCreatedAt(),
                        review.getUpdatedAt())).toList();
    }

}
