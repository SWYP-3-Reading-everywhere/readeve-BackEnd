package com.book_everywhere.web;

import com.book_everywhere.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AuthController {

    /**
     * 소셜로그인 테스트를위한 controller
     */

    @GetMapping("/")
    public String test(@AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("------------------------------------------");
        log.info(String.valueOf(oAuth2User));
        log.info("------------------------------------------");
        return "homePage";
    }
    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }
}
