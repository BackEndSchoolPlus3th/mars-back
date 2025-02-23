package com.ll.commars.domain.review.review.controller;

import com.ll.commars.domain.review.review.dto.ReviewDto;
import com.ll.commars.domain.review.review.entity.Review;
import com.ll.commars.domain.review.review.service.ReviewService;
import com.ll.commars.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/review", produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1ReviewController", description = "리뷰 CRUD API")
public class ApiV1ReviewController {
    private final ReviewService reviewService;

    @DeleteMapping("/{review_id}")
    @Operation(summary = "리뷰 삭제")
    public RsData<String> deleteReview(
            @PathVariable("review_id") @NotNull Long reviewId
    ){
        reviewService.deleteReview(reviewId);
        return new RsData<>("200", "리뷰 삭제 성공", "리뷰 삭제 성공!");
    }

    @PatchMapping(value = "/{review_id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "리뷰 수정")
    public RsData<ReviewDto.ReviewWriteResponse> modifyReview(
            @PathVariable("review_id") @NotNull Long reviewId,
            @RequestBody @Valid ReviewDto.ReviewWriteRequest request
    ){
        ReviewDto.ReviewWriteResponse response = reviewService.modifyReview(reviewId, request);
        return new RsData<>("200", "리뷰 수정 성공", response);
    }

    @GetMapping("/showAllReviews")
    @Operation(summary = "모든 리뷰 조회")
    public RsData<ReviewDto.ShowAllReviewsResponse> showAllReviews( @RequestParam("restaurant_id") @NotNull Long restaurantId){
        ReviewDto.ShowAllReviewsResponse response = reviewService.showAllReviews(restaurantId);
        return new RsData<>("200", "모든 리뷰 조회 성공", response);
    }

    @PostMapping("/writeReview")
    @Operation(summary = "리뷰 작성")
    public ResponseEntity<?> writeReview(
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        System.out.println("body = " + body);
        Review review = reviewService.wirteReview(body.get("restaurant_id").toString(), userDetails.getUsername(), body.get("name").toString(), body.get("body").toString(), Integer.parseInt(body.get("rate").toString()));
        System.out.println("review = " + review);
        return ResponseEntity.ok()
                .body("리뷰 작성 성공");
    }
}
