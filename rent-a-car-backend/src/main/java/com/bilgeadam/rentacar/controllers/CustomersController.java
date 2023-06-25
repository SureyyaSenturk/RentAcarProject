package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.response.CustomerResponse;
import com.bilgeadam.rentacar.service.CustomerService;
import com.bilgeadam.rentacar.request.UpdateCustomerRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin
public class CustomersController {

  private final CustomerService customerService;

  public CustomersController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/getAll")
  DataResult<List<CustomerResponse>> getAll() {
    return this.customerService.getAll();
  }

  @GetMapping("/getById")
  DataResult<CustomerResponse> getById(@RequestParam String id) {
    return this.customerService.getById(id);
  }

  @PutMapping("/update")
  Result update(
      @RequestParam String id, @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
    return this.customerService.update(id, updateCustomerRequest);
  }

  @DeleteMapping("/delete")
  Result delete(@RequestParam String id) {
    return this.customerService.delete(id);
  }
}
