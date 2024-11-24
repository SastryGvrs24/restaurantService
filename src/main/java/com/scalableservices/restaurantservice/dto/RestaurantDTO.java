package com.scalableservices.restaurantservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantDTO {

    private Long id;
    private String restaurantName;
    private String address;
    private Long contactNumber;
    private String operationHours;
    private List<MenuItemDTO> menuItems; // List of MenuItemDTO to avoid circular references

    // Constructor
    public RestaurantDTO(Long id, String restaurantName, String address, Long contactNumber, String operationHours, List<MenuItemDTO> menuItems) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.operationHours = operationHours;
        this.menuItems = menuItems;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getOperationHours() {
        return operationHours;
    }

    public void setOperationHours(String operationHours) {
        this.operationHours = operationHours;
    }

    public List<MenuItemDTO> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDTO> menuItems) {
        this.menuItems = menuItems;
    }
}
