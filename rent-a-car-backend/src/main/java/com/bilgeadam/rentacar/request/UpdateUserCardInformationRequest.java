package com.bilgeadam.rentacar.request;

import com.bilgeadam.rentacar.common.request.BaseRequest;
import com.bilgeadam.rentacar.request.CreatePaymentRequest;
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
public class UpdateUserCardInformationRequest extends BaseRequest {

  @Valid private CreatePaymentRequest paymentInformations;

  @Min(1)
  private String customerId;
}