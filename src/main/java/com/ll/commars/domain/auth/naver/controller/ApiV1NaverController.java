package com.ll.commars.domain.auth.naver.controller;


import com.ll.commars.domain.auth.auth.service.AuthService;
import com.ll.commars.domain.auth.naver.service.NaverService;
import com.ll.commars.domain.user.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/auth", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "ApiV1NaverController", description = "네이버 로그인 API")
public class ApiV1NaverController {
    private final String state = UUID.randomUUID().toString();

    private final NaverService naverservice;
    private final AuthService authService;


    @PostMapping(value = "/login/naver", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "네이버 로그인")
    public ResponseEntity<?> loginForNaver(@RequestBody Map<String, String> body) {

        System.out.println("body = " + body);
        String naverAccessToken = naverservice.getAccessToken(body.get("code"), body.get("state"));
        System.out.println("accessToken = " + naverAccessToken);

        if (naverAccessToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("토큰 발급 실패");
        }

        Map<String, Object> userProfile = naverservice.getUserProfile(naverAccessToken);

        System.out.println("naver userProfile = " + userProfile);

        User naverUser = naverservice.loginForNaver(userProfile);

       return authService.login(naverUser);
    }


    @GetMapping("/login/naver/state")
    @Operation(summary = "네이버 로그인 상태")
    public ResponseEntity<?> naverState() {
        System.out.println("get state = " + state);
        return ResponseEntity.ok(state);
    }

    @GetMapping("/logout/naver")
    @Operation(summary = "네이버 로그아웃")
    public void logout(HttpServletResponse response) {
        String accessToken = response.getHeader(HttpHeaders.AUTHORIZATION);

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", "")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        try {
            response.sendRedirect("/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
