package com.bilgeadam.rentacar.api.models;

import com.bilgeadam.rentacar.request.CreatePaymentRequest;
import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDelayedPaymentModel {

  @Min(1)
  private String rentalCarId;

  private LocalDate delayedReturnDate;

  @Min(0)
  private double carDelayedKilometerInformation;

  @Valid private CreatePaymentRequest createPaymentRequest;
}
