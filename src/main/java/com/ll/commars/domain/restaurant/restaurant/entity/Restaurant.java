package com.ll.commars.domain.restaurant.restaurant.entity;

import com.ll.commars.domain.restaurant.businessHour.entity.RestaurantBusinessHour;
import com.ll.commars.domain.restaurant.category.entity.RestaurantCategory;
import com.ll.commars.domain.restaurant.menu.entity.RestaurantMenu;
import com.ll.commars.domain.review.review.entity.Review;
import com.ll.commars.domain.user.favoriteRestaurant.entity.FavoriteRestaurant;
import com.ll.commars.global.baseEntity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@Builder
@ToString(exclude = {"restaurantMenus", "restaurantCategories", "restaurantBusinessHours", "reviews", "favoriteRestaurants"})
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "details", nullable = false)
    private String details;

    @Column(name = "average_rate")
    private Double averageRate;

    @Column(name = "image_url")
    private String imageUrl;

    // 식당 전화 번호
    @Column(name = "contact")
    private String contact;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    // 위도
    @NotNull
    @Column(name = "lat", nullable = false)
    private Double lat;

    // 경도
    @NotNull
    @Column(name = "lng", nullable = false)
    private Double lng;

    // 식당 영업 여부
    @Column(name = "running_state")
    private Boolean runningState;

    // 요약 리뷰
    @Column(name = "summarized_review")
    private String summarizedReview;

    // Restaurant와 RestaurantMenu: 일대다
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RestaurantMenu> restaurantMenus;

    // Restaurant와 RestaurantCategory: 일대다
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantCategory> restaurantCategories;

    // Restaurant와 RestaurantBusinessHours: 일대다
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantBusinessHour> restaurantBusinessHours;

    // Restaurant와 Review: 일대다
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    // Restaurant와 FavoriteRestaurant: 일대다
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FavoriteRestaurant> favoriteRestaurants;
}
