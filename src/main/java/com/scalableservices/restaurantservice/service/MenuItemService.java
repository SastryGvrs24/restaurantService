package com.scalableservices.restaurantservice.service;

import com.scalableservices.restaurantservice.domain.MenuItem;
import com.scalableservices.restaurantservice.dto.MenuItemDTO;
import com.scalableservices.restaurantservice.dto.RestaurantDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuItemService {

    // Convert a MenuItem entity to a MenuItemDTO
    public MenuItemDTO convertToDTO(MenuItem menuItem) {
        if (menuItem == null) {
            return null;
        }

        // Convert the Restaurant entity to RestaurantDTO
        RestaurantDTO restaurantDTO = menuItem.getRestaurant() != null
                ? new RestaurantDTO(
                menuItem.getRestaurant().getId(),
                menuItem.getRestaurant().getRestaurantName(),
                menuItem.getRestaurant().getAddress(),
                menuItem.getRestaurant().getContactNumber(),
                menuItem.getRestaurant().getOperationHours(),
                null  // We do not need to include the menu items here to avoid recursion
        )
                : null;

        // Return the MenuItemDTO
        return new MenuItemDTO(
                menuItem.getId(),
                menuItem.getItemName(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                restaurantDTO
        );
    }

    // Convert a list of MenuItem entities to a list of MenuItemDTOs
    public List<MenuItemDTO> convertToDTOList(List<MenuItem> menuItems) {
        if (menuItems == null) {
            return null;
        }

        return menuItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
