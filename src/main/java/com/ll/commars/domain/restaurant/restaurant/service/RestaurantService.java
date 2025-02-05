package com.ll.commars.domain.restaurant.restaurant.service;

import com.ll.commars.domain.restaurant.menu.dto.RestaurantMenuDto;
import com.ll.commars.domain.restaurant.restaurant.dto.RestaurantDto;
import com.ll.commars.domain.restaurant.restaurant.entity.Restaurant;
import com.ll.commars.domain.restaurant.restaurant.repository.RestaurantRepository;
import com.ll.commars.domain.review.review.dto.ReviewDto;
import com.ll.commars.domain.review.review.entity.Review;
import com.ll.commars.domain.review.review.repository.ReviewRepository;
import com.ll.commars.domain.user.user.entity.User;
import com.ll.commars.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // 식당 정보 등록
    @Transactional
    public RestaurantDto.RestaurantWriteResponse write(
            RestaurantDto.RestaurantWriteRequest request
    ) {
        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .details(request.getDetails())
                .averageRate(request.getAverageRate())
                .imageUrl(request.getImageUrl())
                .contact(request.getContact())
                .address(request.getAddress())
                .lat(request.getLat())
                .lng(request.getLng())
                .runningState(request.getRunningState())
                .summarizedReview(request.getSummarizedReview())
                .build();

        restaurantRepository.save(restaurant);

        return RestaurantDto.RestaurantWriteResponse.builder()
                .name(request.getName())
                .build();
    }

    // 모든 식당 삭제
    @Transactional
    public void truncate() {
        restaurantRepository.deleteAll();
    }

    // 모든 식당 조회
    @Transactional(readOnly = true)
    public RestaurantDto.RestaurantShowAllResponse getRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAllWithMenus();

        List<RestaurantDto.RestaurantInfo> restaurantInfos = restaurants.stream()
                .map(restaurant -> {
                    List<RestaurantMenuDto.MenuInfo> menuInfos = restaurant.getRestaurantMenus().stream()
                            .map(menu -> RestaurantMenuDto.MenuInfo.builder()
                                    .name(menu.getName())
                                    .price(menu.getPrice())
                                    .imageUrl(menu.getImageUrl())
                                    .build())
                            .collect(Collectors.toList());

                    return RestaurantDto.RestaurantInfo.builder()
                            .id(restaurant.getId())
                            .name(restaurant.getName())
                            .details(restaurant.getDetails())
                            .averageRate(restaurant.getAverageRate())
                            .imageUrl(restaurant.getImageUrl())
                            .contact(restaurant.getContact())
                            .address(restaurant.getAddress())
                            .lat(restaurant.getLat())
                            .lng(restaurant.getLng())
                            .runningState(restaurant.getRunningState())
                            .summarizedReview(restaurant.getSummarizedReview())
                            .restaurantMenus(menuInfos)
                            .build();
                })
                .collect(Collectors.toList());

        return RestaurantDto.RestaurantShowAllResponse.builder()
                .restaurants(restaurantInfos)
                .build();
    }

    // 식당 리뷰 작성
    public ReviewDto.ReviewWriteResponse writeReview(Long restaurantId, ReviewDto.ReviewWriteRequest request) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        User writer = userRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        reviewRepository.save(Review.builder()
                .restaurant(restaurant)
                .user(writer)
                .body(request.getBody())
                .name(request.getReviewName())
                .rate(request.getRate())
                .build());

        return ReviewDto.ReviewWriteResponse.builder()
                .restaurantName(restaurant.getName())
                .build();
    }

    public RestaurantDto.RestaurantShowAllReviewsResponse getReviews(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        List<Review> reviews = reviewRepository.findByRestaurant(restaurant);

        List<ReviewDto.ReviewInfo> reviewInfos = reviews.stream()
                .map(review -> ReviewDto.ReviewInfo.builder()
                        .userEmail(review.getUser().getEmail())
                        .restaurantName(review.getRestaurant().getName())
                        .reviewName(review.getName())
                        .body(review.getBody())
                        .rate(review.getRate())
                        .build())
                .collect(Collectors.toList());

        return RestaurantDto.RestaurantShowAllReviewsResponse.builder()
                .reviews(reviewInfos)
                .build();
    }

    @Transactional
    public RestaurantDto.RestaurantShowAllMenusResponse getMenus(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        List<RestaurantMenuDto.MenuInfo> menuInfos = restaurant.getRestaurantMenus().stream()
                .map(menu -> RestaurantMenuDto.MenuInfo.builder()
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imageUrl(menu.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return RestaurantDto.RestaurantShowAllMenusResponse.builder()
                .menus(menuInfos)
                .build();
    }

    @Transactional(readOnly = true)
    public RestaurantDto.RestaurantInfo getRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        List<RestaurantMenuDto.MenuInfo> menuInfos = restaurant.getRestaurantMenus().stream()
                .map(menu -> RestaurantMenuDto.MenuInfo.builder()
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .imageUrl(menu.getImageUrl())
                        .build())
                .collect(Collectors.toList());

        return RestaurantDto.RestaurantInfo.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .details(restaurant.getDetails())
                .averageRate(restaurant.getAverageRate())
                .imageUrl(restaurant.getImageUrl())
                .contact(restaurant.getContact())
                .address(restaurant.getAddress())
                .lat(restaurant.getLat())
                .lng(restaurant.getLng())
                .runningState(restaurant.getRunningState())
                .summarizedReview(restaurant.getSummarizedReview())
                .restaurantMenus(menuInfos)
                .build();
    }

    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));

        restaurantRepository.deleteById(restaurantId);
    }
}
