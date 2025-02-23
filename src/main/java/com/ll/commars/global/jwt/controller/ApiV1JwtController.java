package com.ll.commars.global.jwt.controller;


import com.ll.commars.domain.user.user.entity.User;
import com.ll.commars.domain.user.user.service.UserService;
import com.ll.commars.global.jwt.component.JwtProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping(value = "/api/jwt", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "ApiV1JwtController", description = "JWT 관련 API")
public class ApiV1JwtController {
    private final JwtProvider jwtProvider;
    private final UserService userService;

    @GetMapping("/refresh")
    @Operation(summary = "Refresh Token을 이용한 Access Token 재발급")
    public ResponseEntity<?> refresh(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {

        String requestToken = extractRefreshTokenFromCookies(request);

        if (requestToken == null) {
            return ResponseEntity.badRequest().body("Refresh Token이 존재하지 않습니다.");
        }

        Optional<User> user = userService.findById(Long.parseLong(userDetails.getUsername()));

        if (user.isEmpty()) {
            return ResponseEntity.badRequest().body("사용자 정보가 존재하지 않습니다.");
        }

        String refreshToken = jwtProvider.generateRefreshToken(user.get());

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(jwtProvider.REFRESH_TOKEN_VALIDITY)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(Map.of("server", "refresh ok"));
    }


    private String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
