package com.book_everywhere.service;

import com.book_everywhere.domain.book.Book;
import com.book_everywhere.domain.book.BookRepository;
import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.review.Review;
import com.book_everywhere.domain.review.ReviewRepository;
import com.book_everywhere.domain.tag.Tag;
import com.book_everywhere.domain.tag.TagRepository;
import com.book_everywhere.domain.tagged.Tagged;
import com.book_everywhere.domain.tagged.TaggedRepository;
import com.book_everywhere.domain.visit.Visit;
import com.book_everywhere.domain.visit.VisitRepository;
import com.book_everywhere.web.dto.AllDataDto;
import com.book_everywhere.web.dto.book.BookRespDto;
import com.book_everywhere.web.dto.pin.PinRespDto;
import com.book_everywhere.web.dto.tag.TagRespDto;
import com.book_everywhere.web.exception.customs.CustomErrorCode;
import com.book_everywhere.web.exception.customs.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//프론트 단 요청에 의해 만들어진 서비스 입니다. 이후에 삭제될 예정입니다.
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataService {

    private final ReviewRepository reviewRepository;
    private final PinRepository pinRepository;
    private final BookRepository bookRepository;
    private final TaggedRepository taggedRepository;
    private final TagRepository tagRepository;
    private final VisitRepository visitRepository;



    public List<AllDataDto> 모든공유또는개인데이터조회(boolean isPrivate) {
        List<Review> reviews = reviewRepository.findByIsPrivateOrderByCreateAtDesc(isPrivate);

        return reviews.stream().map(review ->
        {
            Pin pin = pinRepository.mFindByPinId(review.getPin().getId());
            Visit visit = visitRepository.mFindByUserAndPin(review.getBook().getUser().getId(), review.getPin().getId());
            PinRespDto pinRespDto = new PinRespDto(
                    pin.getTitle(),
                    pin.getPlaceId(),
                    pin.getLatitude(),
                    pin.getLongitude(),
                    pin.getAddress(),
                    visit.isPinPrivate(),
                    pin.getUrl());
            Book book = bookRepository.findById(review.getBook().getId())
                    .orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND));
            BookRespDto bookRespDto = new BookRespDto(
                    book.getIsbn(),
                    book.getTitle(),
                    book.getCoverImageUrl(),
                    book.isComplete(),
                    book.getAuthor()
            );
            List<Tagged> taggedList = taggedRepository.findAllByPinId(pin.getId());
            List<Tag> tagObjects = tagRepository.findAll();
            List<TagRespDto> tagRespDtoList = tagObjects.stream().map(tag -> {
                String name = tag.getCategory().getName();
                for (Tagged tagged : taggedList) {
                    if (tag.getContent().equals(tagged.getTag().getContent())) {
                        return new TagRespDto(tag.getContent(), true, name);
                    }
                }
                return new TagRespDto(tag.getContent(), false, name);
            }).toList();

            return new AllDataDto(
                    review.getId(),
                    book.getUser().getSocialId(),
                    review.getWriter(),
                    review.getTitle(),
                    review.isPrivate(),
                    pinRespDto,
                    bookRespDto,
                    tagRespDtoList,
                    review.getContent(),
                    review.getCreateAt()
            );
        }).toList();
    }

    //태그 조회 테스트용
    public List<Tagged> findAllTaggedList(Long pinId) {
        return taggedRepository.findAllByPinId(pinId);
    }


    public List<AllDataDto> 유저독후감조회(Long socialId) {
        List<Review> reviews = reviewRepository.mFindReviewsByUser(socialId);

        return reviews.stream().map(review ->
        {
            Pin pin = pinRepository.mFindByPinId(review.getPin().getId());
            Visit visit = visitRepository.mFindByUserAndPin(review.getBook().getUser().getId(), review.getPin().getId());
            PinRespDto pinRespDto = new PinRespDto(
                    pin.getTitle(),
                    pin.getPlaceId(),
                    pin.getLatitude(),
                    pin.getLongitude(),
                    pin.getAddress(),
                    visit.isPinPrivate(),
                    pin.getUrl());
            Book book = bookRepository.findById(review.getBook().getId())
                    .orElseThrow(() -> new EntityNotFoundException(CustomErrorCode.BOOK_NOT_FOUND));
            BookRespDto bookRespDto = new BookRespDto(
                    book.getIsbn(),
                    book.getTitle(),
                    book.getCoverImageUrl(),
                    book.isComplete(),
                    book.getAuthor()
            );
            List<Tagged> taggedList = taggedRepository.mFindAllByPinAndUser(pin.getId(),socialId);
            List<Tag> tagObjects = tagRepository.findAll();
            List<TagRespDto> tagRespDtoList = tagObjects.stream().map(tag -> {
                String name = tag.getCategory().getName();
                for (Tagged tagged : taggedList) {
                    if (tag.getContent().equals(tagged.getTag().getContent())) {
                        return new TagRespDto(tag.getContent(), true, name);
                    }
                }
                return new TagRespDto(tag.getContent(), false, name);
            }).toList();


            return new AllDataDto(
                    review.getId(),
                    book.getUser().getSocialId(),
                    review.getWriter(),
                    review.getTitle(),
                    review.isPrivate(),
                    pinRespDto,
                    bookRespDto,
                    tagRespDtoList,
                    review.getContent(),
                    review.getCreateAt()
            );
        }).toList();
    }


}
