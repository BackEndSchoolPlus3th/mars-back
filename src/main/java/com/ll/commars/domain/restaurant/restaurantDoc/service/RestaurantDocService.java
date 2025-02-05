package com.ll.commars.domain.restaurant.restaurantDoc.service;

import com.ll.commars.domain.restaurant.restaurantDoc.document.RestaurantDoc;
import com.ll.commars.domain.restaurant.restaurantDoc.repository.RestaurantDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantDocService {
    private final RestaurantDocRepository restaurantDocRepository;

    public RestaurantDoc write(String name, String details, Double averageRate) {
        RestaurantDoc restaurantDoc = RestaurantDoc.builder()
                .name(name)
                .details(details)
                .averageRate(averageRate)
                .build();

        return restaurantDocRepository.save(restaurantDoc);
    }

    public void truncate() {
        restaurantDocRepository.deleteAll();
    }

    public List<RestaurantDoc> searchByKeyword(String keyword) {
        return restaurantDocRepository.searchByKeyword(keyword);
    }

    public List<RestaurantDoc> showSortByRate() {
        return restaurantDocRepository.findAllByOrderByAverageRateDesc();
    }
}
