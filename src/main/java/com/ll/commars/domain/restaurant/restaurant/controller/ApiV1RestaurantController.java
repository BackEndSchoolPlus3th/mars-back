package com.ll.commars.domain.restaurant.restaurant.controller;

import com.ll.commars.domain.restaurant.menu.dto.RestaurantMenuDto;
import com.ll.commars.domain.restaurant.menu.service.RestaurantMenuService;
import com.ll.commars.domain.restaurant.restaurant.dto.RestaurantDto;
import com.ll.commars.domain.restaurant.restaurant.service.RestaurantService;
import com.ll.commars.domain.review.review.dto.ReviewDto;
import com.ll.commars.domain.review.review.entity.Review;
import com.ll.commars.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurant")
public class ApiV1RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMenuService restaurantMenuService;

    // 식당 정보 등록(완료)
    @PostMapping("/")
    public RsData<RestaurantDto.RestaurantWriteResponse> write(
            @RequestBody @Valid RestaurantDto.RestaurantWriteRequest request
    ){
        RestaurantDto.RestaurantWriteResponse response = restaurantService.write(request);
        return new RsData<>("201", "식당 등록 성공", response);
    }

    // 모든 식당 조회(완료)
    @GetMapping("/")
    public RsData<RestaurantDto.RestaurantShowAllResponse> getRestaurants() {
        RestaurantDto.RestaurantShowAllResponse response = restaurantService.getRestaurants();
        return new RsData<>("200", "모든 식당 조회 성공", response);
    }

    // 식당 메뉴 등록
    @PostMapping("{restaurant_id}/menu")
    public RsData<RestaurantMenuDto.RestaurantMenuWriteResponse> writeMenu(
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid RestaurantMenuDto.MenuInfo request
    ){
        RestaurantMenuDto.RestaurantMenuWriteResponse response = restaurantMenuService.write(restaurantId, request);

        return new RsData<>("201", "메뉴 등록 성공", response);
    }

    // 식당 메뉴 조회

    // 식당 메뉴 수정

    // 식당 리뷰 등록
    @PostMapping("/{restaurant_id}/review")
    public RsData<ReviewDto.ReviewWriteResponse> writeReview(
            @PathVariable("restaurant_id") @NotBlank Long restaurantId,
            @RequestBody @Valid ReviewDto.ReviewWriteRequest request
    ){
        ReviewDto.ReviewWriteResponse response = restaurantService.writeReview(restaurantId, request);
        return new RsData<>("201", "리뷰 등록 성공", response);
    }

    // 식당 리뷰 조회
    @GetMapping("/{restaurant_id}/review")
    public RsData<RestaurantDto.RestaurantShowAllReviewsResponse> getReviews(
            @PathVariable("restaurant_id") @NotNull Long restaurantId
    ){
        RestaurantDto.RestaurantShowAllReviewsResponse response = restaurantService.getReviews(restaurantId);
        return new RsData<>("200", "리뷰 조회 성공", response);
    }

    // 식당 리뷰 삭제

    // 식당 리뷰 수정
}
