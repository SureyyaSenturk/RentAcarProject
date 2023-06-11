package com.bilgeadam.rentacar.rentalcar;

import com.bilgeadam.rentacar.additionalservice.AdditionalProductResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.time.LocalDate;
import java.util.List;

public interface RentalCarService {

  DataResult<List<RentalCarResponse>> getAll() ;

  SuccessDataResult<RentalCar> rentForCorporateCustomers(
      CreateRentalCarRequest createRentalCarRequest) ;

  SuccessDataResult<RentalCar> rentForIndividualCustomers(
      CreateRentalCarRequest createRentalCarRequest) ;

  DataResult<RentalCarResponse> getById(String id) ;

  Result update(String id, UpdateRentalCarRequest updateRentalCarRequest) ;

  Result delete(String id) ;

  DataResult<List<RentalCarResponse>> getAllRentalCarsByCarId(String carId)
      ;

  List<RentalCar> getAllRentalCars();

  DataResult<List<AdditionalProductResponse>> getOrderedAdditionalServicesByRentalCarId(
      String rentalCarId);

  void setDelayedReturnDate(String rentalCarId, LocalDate delayedReturnDate);

  RentalCar getRentalCarById(String id);
}
