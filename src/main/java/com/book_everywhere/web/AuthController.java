package com.book_everywhere.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class AuthController {

    @GetMapping("/")
    public String test() {
        return "homePage";
    }
    @GetMapping("/login")
    public String login() {
        return "loginPage";
    }
}
