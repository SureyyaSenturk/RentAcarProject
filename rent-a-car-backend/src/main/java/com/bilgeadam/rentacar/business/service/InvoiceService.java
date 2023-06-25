package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.business.request.CreateInvoiceRequest;
import com.bilgeadam.rentacar.business.request.CreateRentalCarRequest;
import com.bilgeadam.rentacar.entity.Invoice;
import com.bilgeadam.rentacar.entity.RentalCar;
import com.bilgeadam.rentacar.business.response.InvoiceResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;

public interface InvoiceService {

  DataResult<List<InvoiceResponse>> getAll();

  SuccessDataResult<Invoice> add(CreateInvoiceRequest createInvoiceRequest);

  DataResult<InvoiceResponse> getById(String id);

  Result delete(String id);

  DataResult<List<InvoiceResponse>> getAllInvoicesByRentalCarId(String id);

  double preInvoiceCalculator(CreateRentalCarRequest createRentalCarRequest);

  DataResult<Invoice> generateDelayedRentalCarInvoice(RentalCar rentalCar);

  DataResult<InvoiceResponse> reGenerateInvoiceForUpdatedRentalCar(RentalCar rentalCar);
}
