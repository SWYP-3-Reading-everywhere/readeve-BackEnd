package com.book_everywhere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookEverywhereApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookEverywhereApplication.class, args);
		System.out.println("연동 테스트입니다.");
	}

}
