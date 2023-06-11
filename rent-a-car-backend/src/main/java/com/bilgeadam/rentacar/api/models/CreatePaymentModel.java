package com.bilgeadam.rentacar.api.models;

import com.bilgeadam.rentacar.payment.CreatePaymentRequest;
import com.bilgeadam.rentacar.rentalcar.CreateRentalCarRequest;
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
