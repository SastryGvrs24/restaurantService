package com.scalableservices.restaurantservice.repository;


import com.scalableservices.restaurantservice.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long id);
}
