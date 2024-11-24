package com.scalableservices.restaurantservice.service;

import com.scalableservices.restaurantservice.domain.Restaurant;
import com.scalableservices.restaurantservice.dto.MenuItemDTO;
import com.scalableservices.restaurantservice.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    public RestaurantDTO convertToDTO(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }

        // Convert the list of MenuItems to MenuItemDTOs
        List<MenuItemDTO> menuItemDTOs = restaurant.getMenuItems().stream()
                .map(menuItem -> new MenuItemDTO(
                        menuItem.getId(),
                        menuItem.getItemName(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        new RestaurantDTO(restaurant.getId(), restaurant.getRestaurantName(), restaurant.getAddress(), restaurant.getContactNumber(), restaurant.getOperationHours(), null) // Avoid recursive call here
                ))
                .collect(Collectors.toList());

        // Return the RestaurantDTO
        return new RestaurantDTO(
                restaurant.getId(),
                restaurant.getRestaurantName(),
                restaurant.getAddress(),
                restaurant.getContactNumber(),
                restaurant.getOperationHours(),
                menuItemDTOs
        );
    }
}
