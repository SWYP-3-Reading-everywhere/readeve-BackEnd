package com.book_everywhere.review.service;

import com.book_everywhere.book.entity.Book;
import com.book_everywhere.book.repository.BookRepository;
import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.review.entity.Review;
import com.book_everywhere.review.repository.ReviewRepository;
import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import com.book_everywhere.tag.service.TaggedService;
import com.book_everywhere.exception.customs.CustomErrorCode;
import com.book_everywhere.exception.customs.EntityNotFoundException;
import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.exception.customs.PropertyBadRequestException;
import com.book_everywhere.review.dto.ReviewDto;
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
    private final TaggedService taggedService;


    //사용자 검증에 메소드
    //등록
    @Transactional
    public Long 독후감생성하기(ReviewRespDto reviewRespDto) {
        User user = userRepository.findBySocialId(reviewRespDto.getSocialId()).orElseThrow(
                () -> new EntityNotFoundException(CustomErrorCode.USER_NOT_FOUND));
        Book book = bookRepository.mFindBookByUserIdAndTitle(user.getSocialId(), reviewRespDto.getBookRespDto().getTitle());
        if (book == null) {
            throw new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND);
        }

        Pin pin = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());
        if (pin == null) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }
        Review review = Review.builder()
                .book(book)
                .pin(pin)
                .user(user)
                .title(reviewRespDto.getTitle())
                .writer(reviewRespDto.getWriter())
                .content(reviewRespDto.getContent())
                .isPrivate(reviewRespDto.isPrivate())
                .isBookComplete(reviewRespDto.getBookRespDto().isComplete())
                .build();
        reviewRepository.save(review);
        return review.getId();
    }

    //책에따른 모든 리뷰기능이 추가되었습니다.
    @Transactional
    public List<ReviewDto> 책에따른모든리뷰(Long bookId) {
        List<Review> init = reviewRepository.mFindReviewsByBook(bookId);
        if (init.isEmpty()) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(), review.getWriter(), review.getTitle(), review.getContent(), review.isPrivate(), review.getUpdateAt(), review.getCreateAt()))
                .toList();

        return resultDto;
    }


    //단일 핀을 눌렀을때 독후감이 조회됩니다.
    @Transactional(readOnly = true)
    public List<ReviewDto> 단일핀독후감조회(Long pinId, @AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> init = reviewRepository.mFindReviewUserMap((Long) oAuth2User.getAttributes().get("id"), pinId);
        if (init.isEmpty()) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(), review.getWriter(), review.getTitle(), review.getContent(), review.isPrivate(), review.getUpdateAt(), review.getCreateAt()))
                .toList();

        return resultDto;
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> 유저모든독후감조회(@AuthenticationPrincipal OAuth2User oAuth2User) {
        List<Review> init = reviewRepository.mFindReviewsByUser((Long) oAuth2User.getAttributes().get("id"));

        List<ReviewDto> resultDto = init.stream()
                .map(review -> new ReviewDto(review.getId(), review.getWriter(), review.getTitle(), review.getContent(), review.isPrivate(), review.getUpdateAt(), review.getCreateAt()))
                .toList();

        return resultDto;
    }


    @Transactional(readOnly = true)
    //리뷰 하나만 조회
    public ReviewDto 단일독후감조회(Long id) {
        Review init = reviewRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(CustomErrorCode.REVIEW_NOT_FOUND));
        return new ReviewDto(
                init.getId(),
                init.getWriter(),
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
                        review.getWriter(),
                        review.getTitle(),
                        review.getContent(),
                        review.isPrivate(),
                        review.getCreateAt(),
                        review.getUpdateAt())).toList();
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> 모든공유독후감조회() {
        List<Review> init = reviewRepository.findByIsPrivateOrderByCreateAtDesc(false);
        return init.stream().map(review ->
                new ReviewDto(
                        review.getId(),
                        review.getWriter(),
                        review.getTitle(),
                        review.getContent(),
                        review.isPrivate(),
                        review.getCreateAt(),
                        review.getUpdateAt())).toList();
    }

    @Transactional
    public void 독후감수정(Long reviewId, ReviewRespDto reviewRespDto) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.REVIEW_NOT_FOUND));
        Book newBook = bookRepository.mFindBookByUserIdAndTitle(reviewRespDto.getSocialId(), reviewRespDto.getBookRespDto().getTitle());
        Pin newPin = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());
        User user = userRepository.findBySocialId(reviewRespDto.getSocialId()).orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.USER_NOT_FOUND));
        if(newBook == null) {
            throw new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND);
        }
        if(newPin == null) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }
        review.changeReview(reviewRespDto.getTitle(),reviewRespDto.getContent(),reviewRespDto.isPrivate(),reviewRespDto.getBookRespDto().isComplete(),reviewRespDto.getWriter());
        review.setUser(user);
        review.setBook(newBook);
        review.setPin(newPin);
        reviewRepository.save(review);
    }
    @Transactional
    public void 유저독후감개수검증후책삭제(Long socialId, String title) {
        Book book = bookRepository.mFindBookByUserIdAndTitle(socialId, title);
        if(book == null) {
            throw new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND);
        }
        List<Review> reviews = reviewRepository.mFindReviewsByBook(book.getId());
        if(reviews.isEmpty()) {
            bookRepository.delete(book);
        }
    }

    @Transactional
    public void 독후감개수검증후핀삭제(String address, Long socialId) {
        Pin pin = pinRepository.mFindPinByAddress(address);
        if(pin == null) {
            throw new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND);
        }
        List<Review> reviews = reviewRepository.mFindReviewsByPin(pin.getId());
        if(reviews.isEmpty()) {
            if(!pin.getTags().isEmpty()) {
                taggedService.태그삭제(address, socialId);
            }
            pinRepository.delete(pin);
        }
    }

    @Transactional
    public void 독후감삭제(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.REVIEW_NOT_FOUND));
        reviewRepository.delete(review);
    }

    public void 등록또는수정전예외처리(ReviewRespDto reviewRespDto) {
        if (reviewRespDto.getTitle() == null || reviewRespDto.getTitle().isEmpty()) {
            throw new PropertyBadRequestException(CustomErrorCode.TITLE_IS_NOT_BLANK);
        }
        if (reviewRespDto.getTitle().length() > 20) {
            throw new PropertyBadRequestException(CustomErrorCode.TITLE_IS_NOT_OVER_20);
        }
        if (reviewRespDto.getContent() == null || reviewRespDto.getContent().isEmpty()) {
            throw new PropertyBadRequestException(CustomErrorCode.CONTENT_IS_NOT_BLANK);
        }
        if (reviewRespDto.getContent().length() > 1500) {
            throw new PropertyBadRequestException(CustomErrorCode.CONTENT_IS_NOT_OVER_1500);
        }
        if (reviewRespDto.getBookRespDto() == null) {
            throw new PropertyBadRequestException(CustomErrorCode.BOOK_IS_NOT_NULL);
        }
        if (reviewRespDto.getPinRespDto().getAddress() == null || reviewRespDto.getPinRespDto().getAddress().isEmpty()) {
            throw new PropertyBadRequestException(CustomErrorCode.ADDRESS_IS_NOT_NULL);
        }
    }
}