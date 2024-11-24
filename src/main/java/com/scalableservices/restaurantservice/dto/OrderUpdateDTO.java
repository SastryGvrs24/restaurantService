package com.scalableservices.restaurantservice.dto;

public class OrderUpdateDTO {
    Long id;
    Long restaurantId;
    ORDERSTATUS orderstatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public ORDERSTATUS getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(ORDERSTATUS orderstatus) {
        this.orderstatus = orderstatus;
    }
}
