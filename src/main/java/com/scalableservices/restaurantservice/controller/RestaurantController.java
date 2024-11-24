package com.scalableservices.restaurantservice.controller;


import com.scalableservices.restaurantservice.domain.MenuItem;
import com.scalableservices.restaurantservice.domain.Restaurant;
import com.scalableservices.restaurantservice.dto.OrderUpdateDTO;
import com.scalableservices.restaurantservice.dto.RestaurantUpdateDTO;
import com.scalableservices.restaurantservice.repository.RestaurantRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static com.scalableservices.restaurantservice.controller.MenuItemController.updateNonNullAttributes;

@RestController
public class RestaurantController {

    @Value("${app-orderservice-url}")
    private String orderServiceUrl;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/restaurant")
    ResponseEntity<Restaurant> getRestaurants(@RequestParam String restaurantName) {
        Restaurant restaurant = restaurantRepository.findByRestaurantName(restaurantName);
        return ResponseEntity.ok(restaurant);
    }

    @GetMapping("/restaurant/{id}")
    ResponseEntity<Restaurant> getRestaurantByID(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if(restaurant.isPresent()) {
            return ResponseEntity.ok(restaurant.get());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/restaurant/{id}/menuitems")
    ResponseEntity<List<MenuItem>> getRestaurantMenuItemsByID(@PathVariable Long id) {
       Optional<Restaurant> restaurant = restaurantRepository.findById(id);

       if(restaurant.isPresent()) {
           return ResponseEntity.ok(restaurant.get().getMenuItems());
       }

       return ResponseEntity.notFound().build();
    }

    @PostMapping("/restaurant/{id}/updateRestaurant")
    @PreAuthorize("hasRole('restaurant')")
    ResponseEntity<String> updateRestaurant(@PathVariable Long id, @RequestBody RestaurantUpdateDTO restaurantUpdateDTO) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if(restaurant.isPresent()) {
            updateNonNullAttributes(restaurantUpdateDTO.getRestaurantName(), restaurant.get()::setRestaurantName);
            updateNonNullAttributes(restaurantUpdateDTO.getAddress(), restaurant.get()::setAddress);
            updateNonNullAttributes(restaurantUpdateDTO.getContactNumber(), restaurant.get()::setContactNumber);
            updateNonNullAttributes(restaurantUpdateDTO.getOperationHours(), restaurant.get()::setOperationHours);

            restaurantRepository.save(restaurant.get());
            return ResponseEntity.ok("{msg : \"Restaurant details updated successfully\"}");
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/restaurant/{id}/updateRestaurantOrder")
    @PreAuthorize("hasRole('restaurant')")
    public ResponseEntity<String> updateRestaurantOrder(
            @PathVariable Long id,
            @RequestBody OrderUpdateDTO orderUpdateDTO,
            HttpServletRequest request) {

        // Extract JWT token from cookies
        String jwtToken = extractJwtFromCookies(request);
        if (jwtToken == null) {
            return ResponseEntity.status(401).body("{msg : \"Unauthorized: JWT token not found\"}");
        }

        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        if (restaurant.isPresent()) {
            // Set headers with JWT token
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cookie", "token=" + jwtToken);

            // Create HTTP entity with headers and body
            HttpEntity<OrderUpdateDTO> requestEntity = new HttpEntity<>(orderUpdateDTO, headers);

            // Send POST request to the external service
            ResponseEntity<String> response = restTemplate.exchange(
                    orderServiceUrl + "/orders/updateOrderRestaurant",
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok("{response : \"Restaurant details updated successfully\"}");
            } else {
                return ResponseEntity.status(response.getStatusCode()).body("{response : \"Failed to update restaurant details\"}");
            }
        }

        return ResponseEntity.notFound().build();
    }

    private String extractJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) {
            return null;
        }
        for (Cookie cookie : request.getCookies()) {
            if ("token".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
