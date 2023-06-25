package com.bilgeadam.rentacar.api.models;

import com.bilgeadam.rentacar.response.InvoiceResponse;
import com.bilgeadam.rentacar.response.RentalCarResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedRentalCarAndInvoiceDto {

  private RentalCarResponse updatedRentalCar;

  private InvoiceResponse updatedInvoice;
}
