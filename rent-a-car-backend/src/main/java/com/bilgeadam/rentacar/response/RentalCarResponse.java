package com.bilgeadam.rentacar.response;

import com.bilgeadam.rentacar.common.response.BaseResponse;

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
