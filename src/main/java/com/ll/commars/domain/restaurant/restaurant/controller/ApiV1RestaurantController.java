package com.ll.commars.domain.restaurant.restaurant.controller;

import com.ll.commars.domain.restaurant.businessHour.dto.BusinessHourDto;
import com.ll.commars.domain.restaurant.category.dto.RestaurantCategoryDto;
import com.ll.commars.domain.restaurant.menu.dto.RestaurantMenuDto;
import com.ll.commars.domain.restaurant.menu.service.RestaurantMenuService;
import com.ll.commars.domain.restaurant.restaurant.dto.RestaurantDto;
import com.ll.commars.domain.restaurant.restaurant.service.RestaurantService;
import com.ll.commars.domain.review.review.dto.ReviewDto;
import com.ll.commars.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/restaurant", produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1RestaurantController", description = "식당 CRUD API")
public class ApiV1RestaurantController {
    private final RestaurantService restaurantService;
    private final RestaurantMenuService restaurantMenuService;

    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "식당 정보 등록")
    public RsData<RestaurantDto.RestaurantWriteResponse> write(
            @RequestBody @Valid RestaurantDto.RestaurantWriteRequest request
    ){
        RestaurantDto.RestaurantWriteResponse response = restaurantService.write(request);
        return new RsData<>("201", "식당 등록 성공", response);
    }

    @GetMapping("/")
    @Operation(summary = "모든 식당 조회")
    public RsData<RestaurantDto.RestaurantShowAllResponse> getRestaurants() {
        RestaurantDto.RestaurantShowAllResponse response = restaurantService.getRestaurants();
        return new RsData<>("200", "모든 식당 조회 성공", response);
    }

    @GetMapping("/{restaurant_id}")
    @Operation(summary = "특정 식당 조회")
    public RsData<RestaurantDto.RestaurantInfo> getRestaurant(
            @PathVariable("restaurant_id") @NotNull Long restaurantId
    ){
        RestaurantDto.RestaurantInfo response = restaurantService.getRestaurant(restaurantId);
        return new RsData<>("200", "특정 식당 조회 성공", response);
    }

    @PatchMapping(value = "/{restaurant_id}", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "특정 식당 수정")
    public RsData<RestaurantDto.RestaurantWriteResponse> modifyRestaurant(
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid RestaurantDto.RestaurantWriteRequest request
    ){
        RestaurantDto.RestaurantWriteResponse response = restaurantService.modifyRestaurant(restaurantId, request);
        return new RsData<>("200", "식당 수정 성공", response);
    }

    @DeleteMapping("/{restaurant_id}")
    @Operation(summary = "특정 식당 삭제")
    public RsData<String> deleteRestaurant(
            @PathVariable("restaurant_id") @NotNull Long restaurantId
    ){
        restaurantService.deleteRestaurant(restaurantId);
        return new RsData<>("204", "식당 삭제 성공", "식당 삭제 성공");
    }

    @PostMapping(value = "{restaurant_id}/menu", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "식당 메뉴 등록")
    public RsData<RestaurantMenuDto.MenuWriteResponse> writeMenu(
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid RestaurantMenuDto.MenuInfo request
    ){
        RestaurantMenuDto.MenuWriteResponse response = restaurantMenuService.write(restaurantId, request);

        return new RsData<>("201", "메뉴 등록 성공", response);
    }

    @GetMapping("/{restaurant_id}/menu")
    @Operation(summary = "식당 메뉴 조회")
    public RsData<RestaurantMenuDto.ShowAllMenusResponse> getMenus(
            @PathVariable("restaurant_id") @NotNull Long restaurantId
    ){
        RestaurantMenuDto.ShowAllMenusResponse response = restaurantService.getMenus(restaurantId);
        return new RsData<>("200", "메뉴 조회 성공", response);
    }

    @PostMapping(value = "/{restaurant_id}/review", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "식당 리뷰 등록")
    public RsData<ReviewDto.ReviewWriteResponse> writeReview(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid ReviewDto.ReviewWriteRequest request
            ){
        Long userId = Long.valueOf(userDetails.getUsername());
        ReviewDto.ReviewWriteResponse response = restaurantService.writeReview(restaurantId, request, userId);
        return new RsData<>("201", "리뷰 등록 성공", response);
    }

    @GetMapping("/{restaurant_id}/review")
    @Operation(summary = "식당 리뷰 조회")
    public RsData<ReviewDto.ShowAllReviewsResponse> getReviews(
            @PathVariable("restaurant_id") @NotNull Long restaurantId
    ){
        ReviewDto.ShowAllReviewsResponse response = restaurantService.getReviews(restaurantId);
        return new RsData<>("200", "리뷰 조회 성공", response);
    }

    @PostMapping("/{restaurant_id}/category")
    @Operation(summary = "식당 카테고리 등록")
    public RsData<RestaurantDto.RestaurantCategoryWriteResponse> writeCategory(
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid RestaurantCategoryDto.RestaurantCategoryWriteRequest request
    ){
        RestaurantDto.RestaurantCategoryWriteResponse response = restaurantService.writeCategory(restaurantId, request);
        return new RsData<>("201", "카테고리 등록 성공", response);
    }

    @GetMapping("/{restaurant_id}/category")
    @Operation(summary = "식당 카테고리 조회")
    public RsData<RestaurantCategoryDto.ShowCategoryNameResponse> getCategories(
            @PathVariable("restaurant_id") @NotNull Long restaurantId
    ){
        RestaurantCategoryDto.ShowCategoryNameResponse response = restaurantService.getCategories(restaurantId);
        return new RsData<>("200", "카테고리 조회 성공", response);
    }

    @PatchMapping("/{restaurant_id}/category")
    @Operation(summary = "식당 카테고리 수정")
    public RsData<RestaurantDto.RestaurantCategoryWriteResponse> modifyCategory(
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid RestaurantCategoryDto.RestaurantCategoryWriteRequest request
    ){
        RestaurantDto.RestaurantCategoryWriteResponse response = restaurantService.modifyCategory(restaurantId, request);
        return new RsData<>("200", "카테고리 수정 성공", response);
    }

    @PostMapping("/{restaurant_id}/business-hour")
    @Operation(summary = "식당 영업시간 등록")
    public RsData<BusinessHourDto.BusinessHourWriteResponse> writeBusinessHours(
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid BusinessHourDto.BusinessHourWriteRequest request
    ){
        BusinessHourDto.BusinessHourWriteResponse response = restaurantService.writeBusinessHours(restaurantId, request);
        return new RsData<>("201", "영업시간 등록 성공", response);
    }

    @PatchMapping("/{restaurant_id}/business-hour")
    @Operation(summary = "식당 영업시간 수정")
    public RsData<BusinessHourDto.BusinessHourWriteResponse> modifyBusinessHours(
            @PathVariable("restaurant_id") @NotNull Long restaurantId,
            @RequestBody @Valid BusinessHourDto.BusinessHourWriteRequest request
    ){
        BusinessHourDto.BusinessHourWriteResponse response = restaurantService.modifyBusinessHours(restaurantId, request);
        return new RsData<>("200", "영업시간 수정 성공", response);
    }
}
