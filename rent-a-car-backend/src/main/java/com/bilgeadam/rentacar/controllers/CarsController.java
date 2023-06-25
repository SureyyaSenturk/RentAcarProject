package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.response.CarResponse;
import com.bilgeadam.rentacar.service.CarService;
import com.bilgeadam.rentacar.request.CreateCarRequest;
import com.bilgeadam.rentacar.request.UpdateCarRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cars")
@CrossOrigin
public class CarsController {

  private final CarService carService;

  public CarsController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/getAll")
  public DataResult<List<CarResponse>> getAll() {
    return this.carService.getAll();
  }

  @PostMapping("/add")
  public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) {
    return this.carService.add(createCarRequest);
  }

  @GetMapping("/getById")
  public DataResult<CarResponse> getById(@RequestParam String id) {
    return this.carService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestParam String id, @RequestBody @Valid UpdateCarRequest updateCarRequest) {
    return this.carService.update(id, updateCarRequest);
  }

  @GetMapping("/findByDailyPriceLessThanEqual")
  DataResult<List<CarResponse>> findByDailyPriceLessThanEqual(@RequestParam double dailyPrice) {
    return this.carService.findByDailyPriceLessThanEqual(dailyPrice);
  }

  @GetMapping("/getAllPaged")
  DataResult<List<CarResponse>> getAllPaged(@RequestParam int pageNo, @RequestParam int pageSize) {
    return this.carService.getAllPaged(pageNo, pageSize);
  }

  @GetMapping("/getAllSortedByDailyPrice")
  DataResult<List<CarResponse>> getAllSortedByDailyPrice(Sort.Direction sortDirection) {
    return this.carService.getAllSortedByDailyPrice(sortDirection);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestParam String id) {
    return this.carService.delete(id);
  }
}
