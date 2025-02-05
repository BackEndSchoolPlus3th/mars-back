package com.ll.commars.domain.review.review.controller;

import com.ll.commars.domain.review.review.dto.ReviewDto;
import com.ll.commars.domain.review.review.entity.Review;
import com.ll.commars.domain.review.review.service.ReviewService;
import com.ll.commars.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
public class ApiV1ReviewController {
    private final ReviewService reviewService;

    // 모든 리뷰 조회
    @GetMapping("/")
    public RsData<ReviewDto.ReviewShowAllResponse> getReviews() {
        ReviewDto.ReviewShowAllResponse response = reviewService.getReviews();
        return new RsData<>("200", "모든 리뷰 조회 성공", response);
    }
}
