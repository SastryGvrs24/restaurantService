package com.scalableservices.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuItemDTO {

    private Long id;
    private String itemName;
    private int price;
    private String description;
    private RestaurantDTO restaurant; // Updated to include RestaurantDTO

    // Constructor
    public MenuItemDTO(Long id, String itemName, int price, String description, RestaurantDTO restaurant) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.description = description;
        this.restaurant = restaurant;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }
}
