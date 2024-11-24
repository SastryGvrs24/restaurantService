package com.scalableservices.restaurantservice.controller;

import com.scalableservices.restaurantservice.domain.MenuItem;
import com.scalableservices.restaurantservice.dto.MenuItemDTO;
import com.scalableservices.restaurantservice.dto.MenuItemUpdateDto;
import com.scalableservices.restaurantservice.repository.MenuItemRepository;
import com.scalableservices.restaurantservice.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Consumer;


@RestController
@RequestMapping("/menuItem")
public class MenuItemController {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    MenuItemService menuItemService;

    @GetMapping("/{id}")
    ResponseEntity<MenuItemDTO> getMenuItem(@PathVariable long id) {

       Mono<String> test = WebClient.builder().baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/restaurant")
                        .queryParam("restaurantName", "My Restaurant")
                        .build())
               .retrieve()
               .bodyToMono(String.class);

        test.subscribe(x -> System.out.println(x), error -> System.out.println(error), () -> System.out.println("completed"));

        Optional<MenuItem> menuItem = menuItemRepository.findById(id);

        if(menuItem.isPresent()) {
            MenuItemDTO menuItemDTO = menuItemService.convertToDTO(menuItem.get());
            return ResponseEntity.ok(menuItemDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/updatemenuitem")
    @PreAuthorize("hasRole('restaurant')")
    ResponseEntity addMenuItems(@PathVariable long id, @RequestBody MenuItemUpdateDto item) {
        //authenticated user
        Optional<MenuItem> menuItem = menuItemRepository.findById(id);

        if(menuItem.isPresent()) {
            updateNonNullAttributes(item.getItemName(), menuItem.get()::setItemName);
            updateNonNullAttributes(item.getDescription(), menuItem.get()::setDescription);
            updateNonNullAttributes(item.getPrice(), menuItem.get()::setPrice);

            menuItemRepository.save(menuItem.get());

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public static <T> void updateNonNullAttributes(T value, Consumer<T> function) {
        if(value != null) {
            function.accept(value);
        }
    }
}
