package com.bilgeadam.rentacar.api.controllers;

import com.bilgeadam.rentacar.additionalservice.AdditionalProductResponse;
import com.bilgeadam.rentacar.rentalcar.RentalCarResponse;
import com.bilgeadam.rentacar.rentalcar.RentalCarService;
import com.bilgeadam.rentacar.rentalcar.UpdateRentalCarRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rentalCars")
@CrossOrigin
public class RentalCarsController {

  private final RentalCarService rentalCarService;

  public RentalCarsController(RentalCarService rentalCarService) {
    this.rentalCarService = rentalCarService;
  }

  @GetMapping("/getAll")
  public DataResult<List<RentalCarResponse>> getAll() {
    return this.rentalCarService.getAll();
  }

  @GetMapping("/getById")
  public DataResult<RentalCarResponse> getById(@RequestParam String id) {
    return this.rentalCarService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestParam String id, @RequestBody @Valid UpdateRentalCarRequest updateRentalCarRequest) {
    return this.rentalCarService.update(id, updateRentalCarRequest);
  }

  @DeleteMapping("/deletedelete")
  public Result delete(@RequestParam String id) {
    return this.rentalCarService.delete(id);
  }

  @GetMapping("/getOrderedAdditionalServicesByRentalCarId")
  public DataResult<List<AdditionalProductResponse>> getOrderedAdditionalServicesByRentalCarId(
      String rentalCarId) {
    return this.rentalCarService.getOrderedAdditionalServicesByRentalCarId(rentalCarId);
  }
}
