package com.bilgeadam.rentacar.config;

import java.util.Locale;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

  public static final String[] PUBLIC_ENDPOINTS = {
    "/api/corporateCustomersController/register",
    "/api/individualCustomersController/register",
    "/api/cities/getAll",
    "/api/users/login",
    "/api/paymentsController/paymentForIndividualCustomer",
    "/api/paymentsController/paymentForCorporateCustomer"
  };

  public static final String[] CUSTOMER_AUTHORITY = {
    "/api/paymentsController/additionalPaymentForDelaying",
    "/api/rentalCars/getOrderedAdditionalServicesByRentalCarId"
  };

  public static final String[] ADD_UPDATE_DELETE_OPERATIONS = {
    "/api/**/add",
    "/api/**/update",
    "/api/**/delete",
    "/api/corporateCustomersController/getAll",
    "/api/corporateCustomersController/getById",
    "/api/corporateCustomersController/update",
    "/api/corporateCustomersController/delete",
    "/api/individualCustomersController/getAll",
    "/api/individualCustomersController/getById",
    "/api/individualCustomersController/update",
    "/api/individualCustomersController/delete",
    "/api/customers/getAll",
    "/api/customers/getById",
    "/api/customers/update",
    "/api/customers/delete",
    "/api/invoicesController/getAll",
    "/api/invoicesController/getAllInvoicesByRentalCarId",
    "/api/invoicesController/getById",
    "/api/invoicesController/delete",
    "/api/paymentsController/getAll",
    "/api/paymentsController/delete",
    "/api/paymentsController/getById",
    "/api/rentalCars/getAll",
    "/api/rentalCars/getById",
    "/api/rentalCars/getById",
    "/api/rentalCars/update",
    "/api/rentalCars/update",
    "/api/userCardInformationsController/getAll",
    "/api/userCardInformationsController/getById",
    "/api/userCardInformationsController/update",
    "/api/userCardInformationsController/delete",
    "//api/users/getAll",
    "//api/users/getById",
    "//api/users/update",
    "//api/users/delete",
  };

  public static final String[] GETALL_AND_GETBYID_ENDPOINTS = {
    "/api/additionalProductsController/getAll",
    "/api/additionalProductsController/getById",
    "/api/brands/getAll",
    "/api/brands/getById",
    "/api/carDamagesController/getAll",
    "/api/carDamagesController/getById",
    "/api/carMaintenances/getAll",
    "/api/carMaintenances//getById",
    "/api/cars/getAll",
    "/api/cars/getById",
    "/api/cities/getById",
    "/api/colors/getAll",
    "/api/colors/getById",
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http.authorizeHttpRequests(
            authorize ->
                authorize
                    .antMatchers(PUBLIC_ENDPOINTS)
                    .permitAll()
                    .antMatchers(ADD_UPDATE_DELETE_OPERATIONS)
                    .hasAnyAuthority(
                        Authority.ADMIN.getAuthority().toUpperCase(Locale.ENGLISH),
                        Authority.PRODUCT_OWNER.getAuthority().toUpperCase(Locale.ENGLISH))
                    .antMatchers(GETALL_AND_GETBYID_ENDPOINTS)
                    .hasAnyAuthority(AuthorityUtil.getAllAuthorities())
                    .antMatchers(CUSTOMER_AUTHORITY)
                    .hasAnyAuthority(AuthorityUtil.getAllAuthorities()))
        .csrf(AbstractHttpConfigurer::disable)
        .cors(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())
        .headers()
        .frameOptions()
        .disable()
        .and()
        .build();
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }
}
