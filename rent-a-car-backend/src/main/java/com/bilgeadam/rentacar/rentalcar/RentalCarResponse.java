package com.bilgeadam.rentacar.rentalcar;

import com.bilgeadam.rentacar.additionalservice.AdditionalProductResponse;
import com.bilgeadam.rentacar.car.CarResponse;
import com.bilgeadam.rentacar.city.CityResponse;
import com.bilgeadam.rentacar.common.response.BaseResponse;
import com.bilgeadam.rentacar.customer.CustomerResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarResponse extends BaseResponse {

  private LocalDate rentDate;

  private LocalDate returnDate;

  private CityResponse rentCity;

  private CityResponse returnCity;

  private double rentStartKilometer;

  private double returnKilometer;

  private CarResponse carResponse;

  private CustomerResponse customerResponse;

  private List<AdditionalProductResponse> additionalServices;
}
