package com.scalableservices.restaurantservice;

import com.scalableservices.restaurantservice.domain.MenuItem;
import com.scalableservices.restaurantservice.domain.Restaurant;
import com.scalableservices.restaurantservice.repository.MenuItemRepository;
import com.scalableservices.restaurantservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Arrays;

@SpringBootApplication
//@EnableDiscoveryClient
public class RestaurantserviceApplication implements CommandLineRunner {

	@Autowired
	RestaurantRepository restaurantRepository;


	public static void main(String[] args) {
		SpringApplication.run(RestaurantserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Restaurant restaurant1 = new Restaurant();
		restaurant1.setAddress("123 Main St");
		restaurant1.setRestaurantName("The Food Place");
		restaurant1.setContactNumber(9876543210L);
		restaurant1.setOperationHours("8AM - 10PM");

		MenuItem item1 = new MenuItem();
		item1.setItemName("Pasta");
		item1.setPrice(12);
		item1.setAvailable(true);
		item1.setRestaurant(restaurant1);

		MenuItem item2 = new MenuItem();
		item2.setItemName("Burger");
		item2.setPrice(8);
		item2.setAvailable(true);
		item2.setRestaurant(restaurant1);

		restaurant1.setMenuItems(Arrays.asList(item1, item2));

		// Restaurant 2
		Restaurant restaurant2 = new Restaurant();
		restaurant2.setAddress("456 Elm St");
		restaurant2.setRestaurantName("Gourmet Bites");
		restaurant2.setContactNumber(1122334455L);
		restaurant2.setOperationHours("7AM - 11PM");

		MenuItem item3 = new MenuItem();
		item3.setItemName("Salad");
		item3.setPrice(7);
		item3.setAvailable(true);
		item3.setRestaurant(restaurant2);

		MenuItem item4 = new MenuItem();
		item4.setItemName("Soup");
		item4.setPrice(6);
		item4.setAvailable(true);
		item4.setRestaurant(restaurant2);

		restaurant2.setMenuItems(Arrays.asList(item3, item4));

		// Restaurant 3
		Restaurant restaurant3 = new Restaurant();
		restaurant3.setAddress("789 Pine St");
		restaurant3.setRestaurantName("Tasty Treats");
		restaurant3.setContactNumber(9988776655L);
		restaurant3.setOperationHours("9AM - 9PM");

		MenuItem item5 = new MenuItem();
		item5.setItemName("Pizza");
		item5.setPrice(15);
		item5.setAvailable(true);
		item5.setRestaurant(restaurant3);

		MenuItem item6 = new MenuItem();
		item6.setItemName("Ice Cream");
		item6.setPrice(5);
		item6.setAvailable(true);
		item6.setRestaurant(restaurant3);

		MenuItem item7 = new MenuItem();
		item7.setItemName("Sandwich");
		item7.setPrice(10);
		item7.setAvailable(true);
		item7.setRestaurant(restaurant3);

		restaurant3.setMenuItems(Arrays.asList(item5, item6, item7));
		restaurantRepository.saveAll(Arrays.asList(restaurant1, restaurant2, restaurant3));
	}
}
