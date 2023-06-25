package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.business.request.CreatePaymentRequest;
import com.bilgeadam.rentacar.business.request.CreateRentalCarRequest;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentModel {

  @Valid private CreateRentalCarRequest createRentalCarRequest;

  @Valid private CreatePaymentRequest createPaymentRequest;
}
