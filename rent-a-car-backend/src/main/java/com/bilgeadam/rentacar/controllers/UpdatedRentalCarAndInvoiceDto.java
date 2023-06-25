package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.business.response.InvoiceResponse;
import com.bilgeadam.rentacar.business.response.RentalCarResponse;
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
