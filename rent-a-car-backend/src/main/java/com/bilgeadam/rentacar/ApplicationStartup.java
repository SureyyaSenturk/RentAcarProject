package com.bilgeadam.rentacar;

import com.bilgeadam.rentacar.brand.Brand;
import com.bilgeadam.rentacar.car.Car;
import com.bilgeadam.rentacar.city.City;
import com.bilgeadam.rentacar.city.CityRepository;
import com.bilgeadam.rentacar.color.Color;
import com.bilgeadam.rentacar.user.User;
import com.bilgeadam.rentacar.user.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {
  private final UserRepository userRepository;
  private final CityRepository cityRepository;

  public ApplicationStartup(UserRepository userRepository, final CityRepository cityRepository) {
    this.userRepository = userRepository;
    this.cityRepository = cityRepository;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    final User admin = new User();
    admin.setEmail("test");
    admin.setPassword("test");
    admin.setAuthority("ADMIN");

    final User productOwner = new User();
    productOwner.setEmail("test2");
    productOwner.setPassword("test2");
    productOwner.setAuthority("PRODUCT_OWNER");

    final User user = new User();
    user.setEmail("test3");
    user.setPassword("test3");
    user.setAuthority("USER");

    final User customer = new User();
    customer.setEmail("test4");
    customer.setPassword("test4");
    customer.setAuthority("CUSTOMER");

    final List<User> userList = new ArrayList<>();
    userList.add(admin);
    userList.add(productOwner);
    userList.add(user);
    userList.add(customer);

    final City city = new City();
    city.setCode(2);
    city.setName("Adıyaman");

    final City city2 = new City();
    city2.setCode(34);
    city2.setName("Istanbul");

    final City city3 = new City();
    city3.setCode(1);
    city3.setName("Adana");

    final List<City> cityList = new ArrayList<>();
    cityList.add(city);
    cityList.add(city2);
    cityList.add(city3);
    this.cityRepository.saveAll(cityList);
    this.userRepository.saveAll(userList);
  }
}
