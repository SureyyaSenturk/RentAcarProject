package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.response.CarDamageResponse;
import com.bilgeadam.rentacar.service.CarDamageService;
import com.bilgeadam.rentacar.request.CreateCarDamageRequest;
import com.bilgeadam.rentacar.request.UpdateCarDamageRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carDamagesController")
@CrossOrigin
public class CarDamagesController {

  private final CarDamageService carDamageService;

  public CarDamagesController(CarDamageService carDamageService) {
    this.carDamageService = carDamageService;
  }

  @GetMapping("/getAll")
  public DataResult<List<CarDamageResponse>> getAll() {
    return this.carDamageService.getAll();
  }

  @PostMapping("/add")
  public Result add(@RequestBody @Valid CreateCarDamageRequest createCarDamageRequest) {
    return this.carDamageService.add(createCarDamageRequest);
  }

  @GetMapping("/getById")
  public DataResult<CarDamageResponse> getById(@RequestParam String id) {

    return this.carDamageService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestParam String id, @RequestBody @Valid UpdateCarDamageRequest updateCarDamageRequest) {
    return this.carDamageService.update(id, updateCarDamageRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestParam String id) {
    return this.carDamageService.delete(id);
  }
}
