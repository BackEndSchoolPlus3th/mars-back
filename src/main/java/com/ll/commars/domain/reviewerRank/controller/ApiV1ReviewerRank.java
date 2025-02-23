package com.ll.commars.domain.reviewerRank.controller;

import com.ll.commars.domain.reviewerRank.dto.ReviewerRank;
import com.ll.commars.domain.reviewerRank.dto.ReviewerRankResponse;
import com.ll.commars.domain.reviewerRank.service.ReviewerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reviewer-rank")
@RequiredArgsConstructor
@Tag(name = "ApiV1ReviewerRank", description = "리뷰어 랭킹 조회 API")
public class ApiV1ReviewerRank {
    private final ReviewerService reviewerService;

    @GetMapping("/top10")
    @Operation(summary = "리뷰를 가장 많이 작성한 상위 10명의 유저 조회")
    public ResponseEntity<ReviewerRankResponse> getTopReviewers() {
        ReviewerRankResponse reviewerRanks = reviewerService.getTopReviewers();
        return ResponseEntity.ok(reviewerRanks);
    }
}
