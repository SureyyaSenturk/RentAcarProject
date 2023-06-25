package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.response.CityResponse;
import com.bilgeadam.rentacar.service.CityService;
import com.bilgeadam.rentacar.request.CreateCityRequest;
import com.bilgeadam.rentacar.request.UpdateCityRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin
public class CitiesController {

  private final CityService cityService;

  public CitiesController(CityService cityService) {
    this.cityService = cityService;
  }

  @GetMapping("/getAll")
  public DataResult<List<CityResponse>> getAll() {
    return this.cityService.getAll();
  }

  @PostMapping("/add")
  public Result add(@RequestBody @Valid CreateCityRequest createCityRequest) {
    return this.cityService.add(createCityRequest);
  }

  @GetMapping("/getById")
  public DataResult<CityResponse> getById(@RequestParam String id) {
    return this.cityService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestParam String id, @RequestBody @Valid UpdateCityRequest updateCityRequest) {
    return this.cityService.update(id, updateCityRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestParam String id) {
    return this.cityService.delete(id);
  }
}
