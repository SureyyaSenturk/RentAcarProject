package com.bilgeadam.rentacar.invoice;

import com.bilgeadam.rentacar.common.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest extends BaseRequest {
  private String rentalCarId;
}
