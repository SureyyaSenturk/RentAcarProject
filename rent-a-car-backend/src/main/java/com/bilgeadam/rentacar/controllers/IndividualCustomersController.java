package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.business.request.CreateIndividualCustomerRequest;
import com.bilgeadam.rentacar.business.response.IndividualCustomerResponse;
import com.bilgeadam.rentacar.business.service.IndividualCustomerService;
import com.bilgeadam.rentacar.business.request.UpdateIndividualCustomerRequest;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/individualCustomersController")
@CrossOrigin
public class IndividualCustomersController {

  private final IndividualCustomerService individualCustomerService;

  public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
    this.individualCustomerService = individualCustomerService;
  }

  @GetMapping("/getAll")
  DataResult<List<IndividualCustomerResponse>> getAll() {
    return this.individualCustomerService.getAll();
  }

  @PostMapping("/register")
  Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) {
    return this.individualCustomerService.add(createIndividualCustomerRequest);
  }

  @GetMapping("/getById")
  DataResult<IndividualCustomerResponse> getById(@RequestParam String id) {
    return this.individualCustomerService.getById(id);
  }

  @PutMapping("/update")
  Result update(
      @RequestParam String id,
      @RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
    return this.individualCustomerService.update(id, updateIndividualCustomerRequest);
  }

  @DeleteMapping("/delete")
  Result delete(@RequestParam String id) {
    return this.individualCustomerService.delete(id);
  }
}
