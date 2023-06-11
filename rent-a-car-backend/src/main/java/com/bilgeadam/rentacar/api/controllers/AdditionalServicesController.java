package com.bilgeadam.rentacar.api.controllers;

import com.bilgeadam.rentacar.additionalservice.AdditionalProductResponse;
import com.bilgeadam.rentacar.additionalservice.AdditionalProductService;
import com.bilgeadam.rentacar.additionalservice.CreateAdditionalProductRequest;
import com.bilgeadam.rentacar.additionalservice.UpdateAdditionalProductRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/additionalProductsController")
@CrossOrigin
public class AdditionalServicesController {
  private final AdditionalProductService additionalProductService;

  public AdditionalServicesController(AdditionalProductService additionalProductService) {
    this.additionalProductService = additionalProductService;
  }

  @GetMapping("/getAll")
  DataResult<List<AdditionalProductResponse>> getAll() {
    return this.additionalProductService.getAll();
  }

  @PostMapping("/add")
  Result add(@RequestBody @Valid CreateAdditionalProductRequest createAdditionalProductRequest) {
    return this.additionalProductService.add(createAdditionalProductRequest);
  }

  @GetMapping("/getById")
  DataResult<AdditionalProductResponse> getById(@RequestParam String id) {
    return this.additionalProductService.getById(id);
  }

  @PutMapping("/update")
  Result update(
      @RequestParam String id,
      @RequestBody @Valid UpdateAdditionalProductRequest updateAdditionalProductRequest) {
    return this.additionalProductService.update(id, updateAdditionalProductRequest);
  }

  @DeleteMapping("/delete")
  Result delete(@RequestParam String id) {
    return this.additionalProductService.delete(id);
  }
}
