package com.bilgeadam.rentacar.api.controllers;

import com.bilgeadam.rentacar.carmaintenance.CarMaintenanceResponse;
import com.bilgeadam.rentacar.carmaintenance.CarMaintenanceService;
import com.bilgeadam.rentacar.carmaintenance.CreateCarMaintenanceRequest;
import com.bilgeadam.rentacar.carmaintenance.UpdateCarMaintenanceRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carMaintenances")
@CrossOrigin
public class CarMaintenancesController {

  private final CarMaintenanceService carMaintenanceService;

  public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
    this.carMaintenanceService = carMaintenanceService;
  }

  @GetMapping("/getAll")
  public DataResult<List<CarMaintenanceResponse>> getAll() {
    return this.carMaintenanceService.getAll();
  }

  @PostMapping("/add")
  public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) {
    return this.carMaintenanceService.add(createCarMaintenanceRequest);
  }

  @GetMapping("/getById")
  public DataResult<CarMaintenanceResponse> getById(@RequestParam String id) {
    return this.carMaintenanceService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestParam String id,
      @RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
    return this.carMaintenanceService.update(id, updateCarMaintenanceRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestParam String id) {
    return this.carMaintenanceService.delete(id);
  }
}
