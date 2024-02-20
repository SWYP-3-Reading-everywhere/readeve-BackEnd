package com.book_everywhere.service;

import com.book_everywhere.domain.pin.Pin;
import com.book_everywhere.domain.pin.PinRepository;
import com.book_everywhere.domain.user.User;
import com.book_everywhere.domain.user.UserRepository;
import com.book_everywhere.domain.visit.Visit;
import com.book_everywhere.domain.visit.VisitRepository;
import com.book_everywhere.web.dto.visit.VisitRespDto;
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
    public void 독후감쓰기전방문등록(VisitRespDto visitRespDto) {
        //review가 올라가기전 visit에 등록되어있는지 확인후 없다면 visit등록
        User user = userRepository.findBySocialId(visitRespDto.getSocialId()).orElseThrow();
        Pin pin = pinRepository.mFindByPinId(visitRespDto.getPinId());

        Visit visited = visitRepository.mFindByUserAndPin(user.getId(), pin.getId());

        if (visited != null) {
            Visit visit = Visit.builder()
                    .user(user)
                    .pin(pin)
                    .isPrivate(visitRespDto.isPrivate())
                    .build();

            visitRepository.save(visit);
        }
    }

}
