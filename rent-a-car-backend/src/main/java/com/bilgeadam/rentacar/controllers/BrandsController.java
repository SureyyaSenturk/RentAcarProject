package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.response.BrandResponse;
import com.bilgeadam.rentacar.service.BrandService;
import com.bilgeadam.rentacar.request.CreateBrandRequest;
import com.bilgeadam.rentacar.request.UpdateBrandRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin
public class BrandsController {

  private final BrandService brandService;

  public BrandsController(BrandService brandService) {
    this.brandService = brandService;
  }

  @GetMapping("/getAll")
  public DataResult<List<BrandResponse>> getAll() {
    return this.brandService.getAll();
  }

  @PostMapping("/add")
  public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest) {
    return this.brandService.add(createBrandRequest);
  }

  @GetMapping("/getById")
  public DataResult<BrandResponse> getById(@RequestParam String id) {

    return this.brandService.getById(id);
  }

  @PutMapping("/update")
  public Result update(
      @RequestParam String id, @RequestBody @Valid UpdateBrandRequest updateBrandRequest) {
    return this.brandService.update(id, updateBrandRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestParam String id) {
    return this.brandService.delete(id);
  }
}
