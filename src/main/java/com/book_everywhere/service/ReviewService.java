package com.book_everywhere.service;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.web.dto.book.BookDto;
import com.book_everywhere.web.dto.review.ReviewRespDto;
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
    private final UserRepository userRepository;


    //사용자 검증에 메소드
    //등록
    @Transactional
    public Long 독후감생성하기(ReviewRespDto reviewRespDto) {
        User user = userRepository.findBySocialId(reviewRespDto.getSocialId()).orElseThrow();
        System.out.println("-------------------------------------------");
        System.out.println(user.getSocialId()+":"+reviewRespDto.getBookRespDto().getTitle());
        System.out.println("-------------------------------------------");
        Book book = bookRepository.mFindBookByUserIdAndTitle(user.getSocialId(), reviewRespDto.getBookRespDto().getTitle());
        Pin pin = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());
        System.out.println("-------------------------------------------");
        System.out.println(book+"살려줘 여기 사람살아요");
        System.out.println("-------------------------------------------");
        Review review = Review.builder()
                .book(book)
                .pin(pin)
                .title(reviewRespDto.getTitle())
                .content(reviewRespDto.getContent())
                .isPrivate(reviewRespDto.isPrivate())
                .build();
        reviewRepository.save(review);
        return review.getId();
    }


    //수정
    @Transactional
    public void 책업데이트(Long id, ReviewDto reviewDto) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review does not exist"));
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setIsPrivate(reviewDto.getIsPrivate());
    }

    //공유 목록에서의 독후감 조회
    @Transactional(readOnly = true)
    public List<ReviewDto> 공유독후감조회(boolean isPrivate, Pageable pageable) {
        List<Review> init = reviewRepository.findByIsPrivate(isPrivate, pageable);
        return init.stream().map(review -> new ReviewDto(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.isPrivate(),
                review.getCreateAt(),
                review.getUpdateAt())).toList();
    }

    //단일 핀을 눌렀을때 독후감이 조회됩니다.
    @Transactional(readOnly = true)
    public List<ReviewDto> 단일핀독후감조회(Long pinId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> init = reviewRepository.mFindReviewUserMap((Long) oAuth2User.getAttributes().get("id"), pinId);

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(), review.getTitle(), review.getContent(), review.isPrivate(), review.getUpdateAt(), review.getCreateAt()))
                .toList();

        return resultDto;
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> 유저모든독후감조회(@AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> init = reviewRepository.mFindReviewsByUser((Long) oAuth2User.getAttributes().get("id"));

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(), review.getTitle(), review.getContent(), review.isPrivate(), review.getUpdateAt(), review.getCreateAt()))
                .toList();

        return resultDto;
    }


    @Transactional(readOnly = true)
    //리뷰 하나만 조회
    public ReviewDto 단일독후감조회(Long id) {
        Review init = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Review does not exist"));
        return new ReviewDto(
                init.getId(),
                init.getTitle(),
                init.getContent(),
                init.isPrivate(),
                init.getCreateAt(),
                init.getUpdateAt());
    }

    @Transactional(readOnly = true)
    //등록된 모든 리뷰 조회
    public List<ReviewDto> 모든독후감조회() {
        List<Review> init = reviewRepository.findAll();
        return init.stream().map(review ->
                new ReviewDto(
                        review.getId(),
                        review.getTitle(),
                        review.getContent(),
                        review.isPrivate(),
                        review.getCreateAt(),
                        review.getUpdateAt())).toList();
    }

}