package com.scalableservices.restaurantservice.dto;

public enum ORDERSTATUS {
    CREATED("created"),
    ACCEPTED_BY_RESTAURANT("accepted by restaurant"),
    CANCELLED("cancelled"),
    COMPLETED("completed");

    private final String orderStatus;

    ORDERSTATUS(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
