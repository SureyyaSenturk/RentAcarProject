package com.bilgeadam.rentacar.api.controllers;

import com.bilgeadam.rentacar.corporatecustomer.CorporateCustomerResponse;
import com.bilgeadam.rentacar.corporatecustomer.CorporateCustomerService;
import com.bilgeadam.rentacar.corporatecustomer.CreateCorporateCustomerRequest;
import com.bilgeadam.rentacar.corporatecustomer.UpdateCorporateCustomerRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/corporateCustomersController")
@CrossOrigin
public class CorporateCustomersController {

  private final CorporateCustomerService corporateCustomerService;

  public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
    this.corporateCustomerService = corporateCustomerService;
  }

  @GetMapping("/getAll")
  DataResult<List<CorporateCustomerResponse>> getAll() {
    return this.corporateCustomerService.getAll();
  }

  @PostMapping("/register")
  Result add(@RequestBody @Valid CreateCorporateCustomerRequest createIndividualCustomerRequest) {
    return this.corporateCustomerService.add(createIndividualCustomerRequest);
  }

  @GetMapping("/getById")
  DataResult<CorporateCustomerResponse> getById(@RequestParam String id) {
    return this.corporateCustomerService.getById(id);
  }

  @PutMapping("/update")
  Result update(
      @RequestParam String id,
      @RequestBody @Valid UpdateCorporateCustomerRequest updateIndividualCustomerRequest) {
    return this.corporateCustomerService.update(id, updateIndividualCustomerRequest);
  }

  @DeleteMapping("/delete")
  Result delete(@RequestParam String id) {
    return this.corporateCustomerService.delete(id);
  }
}
