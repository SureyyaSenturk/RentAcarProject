package com.bilgeadam.rentacar.api.controllers;

import com.bilgeadam.rentacar.usercardinformation.CreateUserCardInformationRequest;
import com.bilgeadam.rentacar.usercardinformation.UpdateUserCardInformationRequest;
import com.bilgeadam.rentacar.usercardinformation.UserCardInformationResponse;
import com.bilgeadam.rentacar.usercardinformation.UserCardInformationService;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userCardInformationsController")
@CrossOrigin
public class UserCardInformationsController {

  private final UserCardInformationService userCardInformationService;

  public UserCardInformationsController(UserCardInformationService userCardInformationService) {
    this.userCardInformationService = userCardInformationService;
  }

  @GetMapping("/getAll")
  DataResult<List<UserCardInformationResponse>> getAll() {
    return this.userCardInformationService.getAll();
  }

  @PostMapping("/add")
  Result add(CreateUserCardInformationRequest createUserCardInformationRequest) {
    return this.userCardInformationService.add(createUserCardInformationRequest);
  }

  @GetMapping("/getById")
  DataResult<UserCardInformationResponse> getById(String id) {
    return this.userCardInformationService.getById(id);
  }

  @PutMapping("/update")
  Result update(String id, UpdateUserCardInformationRequest updateUserCardInformationRequest) {
    return this.userCardInformationService.update(id, updateUserCardInformationRequest);
  }

  @DeleteMapping("/delete")
  Result delete(String id) {
    return this.userCardInformationService.delete(id);
  }
}
