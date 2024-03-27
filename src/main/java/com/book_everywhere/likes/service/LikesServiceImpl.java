package com.book_everywhere.likes.service;

import com.book_everywhere.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;

    @Override
    public void 좋아요(Long socialId, Long review_id) {
        likesRepository.mLike(socialId, review_id);
    }

    @Override
    public void 좋아요취소(Long socialId, Long review_id) {
        likesRepository.mUnLike(socialId, review_id);
    }

}
