package com.book_everywhere.common.controller;

import com.book_everywhere.common.dto.CMRespDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

@Tag(name = "Health Checking 컨트롤러", description = "AWS 및 배포 통신 API입니다.")
@RestController
public class UniversalController {

    @Value("${server.env}")
    private String env;
    @Value("${server.port}")
    private String serverPort;
    @Value("${server.serverAddress}")
    private String serverAddress;
    @Value("${serverName}")
    private String serverName;

    @GetMapping("/health")
    public CMRespDto<?> checkRequest() {
        Map<String, String> responseData = new TreeMap<>();
        responseData.put("serverName", serverName);
        responseData.put("serverAddress", serverAddress);
        responseData.put("serverPort", serverPort);
        responseData.put("env", env);
        return new CMRespDto<>(HttpStatus.OK, responseData, "health checking complete");
    }

    @GetMapping("/env")
    public CMRespDto<?> envRequest() {
        return new CMRespDto<>(HttpStatus.OK, env, ".env checking complete");
    }
    @GetMapping("/")
    public CMRespDto<?> request() {
        return new CMRespDto<>(HttpStatus.OK, env, ".env checking complete");
    }

    @GetMapping("/test/error")
    public String throwError() {
        throw new RuntimeException("테스트 에러 발생!");
    }

}
