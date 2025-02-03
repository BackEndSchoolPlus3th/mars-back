package com.ll.commars.domain.restaurants.restaurants.service;

import com.ll.commars.domain.restaurants.restaurants.entity.Restaurants;
import com.ll.commars.domain.restaurants.restaurants.repository.RestaurantsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantsService {
    private final RestaurantsRepository restaurantsRepository;

    public Restaurants write(String name, String details, Double averageRate) {
        Restaurants restaurants = Restaurants.builder()
                .name(name)
                .details(details)
                .averageRate(averageRate)
                .build();

        return restaurantsRepository.save(restaurants);
    }

    public void truncate() {
        restaurantsRepository.deleteAll();
    }

    public String getRestaurantDetail(String name) {
        Restaurants restaurants = restaurantsRepository.findByName(name);
        return restaurants.getDetails();
    }
}
