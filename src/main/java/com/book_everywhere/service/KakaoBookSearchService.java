package com.book_everywhere.service;

import com.book_everywhere.web.dto.book.BookSearchResultDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KakaoBookSearchService {
    private static final String API_URL = "https://dapi.kakao.com/v3/search/book?target=title&query=";

    @Value("${kakao.api.key}")
    private String apiKey;

    public BookSearchResultDto searchBook(String query) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<BookSearchResultDto> response = restTemplate.exchange(API_URL + query, HttpMethod.GET, entity, BookSearchResultDto.class);

        return response.getBody();
    }
}