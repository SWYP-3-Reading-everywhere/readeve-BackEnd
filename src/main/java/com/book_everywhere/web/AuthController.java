package com.book_everywhere.web;

import com.book_everywhere.web.dto.CMRespDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AuthController {

    @GetMapping("/")
    public CMRespDto<?> index(HttpSession session) {

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        Authentication authentication2 = null;

        if (session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY)
                instanceof SecurityContext securityContext) {   // java 17 문법입니다!
            authentication2 = securityContext.getAuthentication();
        }

        System.out.println("authentication1 = " + authentication1);
        System.out.println("authentication2 = " + authentication2);

        System.out.println("authentication1 hashCode = " + authentication1.hashCode());
        System.out.println("authentication2 hashCode = " + authentication2.hashCode());
        return new CMRespDto<>(HttpStatus.OK, authentication1,"태그 조회 성공!");
    }
}
