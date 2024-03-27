package com.book_everywhere.likes.service;

import com.book_everywhere.auth.entity.User;
import com.book_everywhere.auth.repository.UserRepository;
import com.book_everywhere.likes.entity.Likes;
import com.book_everywhere.likes.repository.LikesRepository;
import com.book_everywhere.review.entity.Review;
import com.book_everywhere.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class LikesServiceImpl implements LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;

    @Override
    public void 좋아요(Long socialId, Long review_id) {
        User user = userRepository.findBySocialId(socialId).orElseThrow();
        likesRepository.mLike(user.getId(),review_id);
    }

    @Override
    public void 좋아요취소(Long socialId, Long review_id) {
        User user = userRepository.findBySocialId(socialId).orElseThrow();
        likesRepository.mUnLike(user.getId(), review_id);
    }

}
