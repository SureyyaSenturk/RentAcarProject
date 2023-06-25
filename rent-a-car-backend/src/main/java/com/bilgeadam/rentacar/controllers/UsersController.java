package com.bilgeadam.rentacar.controllers;

import com.bilgeadam.rentacar.request.UpdateUserRequest;
import com.bilgeadam.rentacar.response.UserResponse;
import com.bilgeadam.rentacar.service.UserService;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UsersController {

  private final UserService userService;

  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/getAll")
  public DataResult<List<UserResponse>> getAll() {
    return this.userService.getAll();
  }

  @GetMapping("/getById")
  public DataResult<UserResponse> getById(@RequestParam String id) {
    return this.userService.getById(id);
  }

  @PutMapping("/update")
  public Result update(@RequestParam String id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
    return this.userService.update(id, updateUserRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestParam String id) {
    return this.userService.delete(id);
  }

  @PostMapping("/login")
  public Result login(@RequestParam String username,@RequestParam String password) {
    return this.userService.login(username,password);
  }
}
