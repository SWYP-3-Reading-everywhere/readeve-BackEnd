package com.book_everywhere.likes.service;

public interface LikesService {


    void 좋아요(Long socialId,Long review_id);

    void 좋아요취소(Long socialId,Long review_id);


}
