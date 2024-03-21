package com.book_everywhere.data.service;

import com.book_everywhere.book.entity.Book;
import com.book_everywhere.book.repository.BookRepository;
import com.book_everywhere.data.dto.AllDataDto;
import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.review.entity.Review;
import com.book_everywhere.review.repository.ReviewRepository;
import com.book_everywhere.tag.entity.Tag;
import com.book_everywhere.tag.repository.TagRepository;
import com.book_everywhere.tag.entity.Tagged;
import com.book_everywhere.tag.repository.TaggedRepository;
import com.book_everywhere.pin.entity.Visit;
import com.book_everywhere.pin.repository.VisitRepository;
import com.book_everywhere.book.dto.BookRespDto;
import com.book_everywhere.pin.dto.PinRespDto;
import com.book_everywhere.tag.dto.TagRespDto;
import com.book_everywhere.exception.customs.CustomErrorCode;
import com.book_everywhere.exception.customs.EntityNotFoundException;
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
