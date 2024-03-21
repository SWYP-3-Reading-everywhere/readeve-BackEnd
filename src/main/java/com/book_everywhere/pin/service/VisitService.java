package com.book_everywhere.pin.service;

import com.book_everywhere.pin.entity.Pin;
import com.book_everywhere.pin.repository.PinRepository;
import com.book_everywhere.pin.repository.VisitRepository;
import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import com.book_everywhere.pin.entity.Visit;
import com.book_everywhere.review.dto.ReviewRespDto;
import com.book_everywhere.exception.customs.CustomErrorCode;
import com.book_everywhere.exception.customs.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VisitService {
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final VisitRepository visitRepository;

    @Transactional
    public void 독후감쓰기전방문등록또는수정(ReviewRespDto reviewRespDto) {
        //review가 올라가기전 visit에 등록되어있는지 확인후 없다면 visit등록
        User user = userRepository.findBySocialId(reviewRespDto.getSocialId()).orElseThrow(
                () -> new EntityNotFoundException(CustomErrorCode.PIN_NOT_FOUND)
        );
        Pin pin = pinRepository.mFindPinByAddress(reviewRespDto.getPinRespDto().getAddress());

        Visit visited = visitRepository.mFindByUserAndPin(user.getId(), pin.getId());

        if (visited == null) {
            Visit visit = Visit.builder()
                    .user(user)
                    .pin(pin)
                    .isPinPrivate(reviewRespDto.getPinRespDto().isPrivate())
                    .build();

            visitRepository.save(visit);
        } else {
            visited.changeVisit(user, pin, reviewRespDto.getPinRespDto().isPrivate());
        }
    }
}
