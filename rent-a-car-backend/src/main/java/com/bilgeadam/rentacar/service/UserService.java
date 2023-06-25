package com.bilgeadam.rentacar.service;

import com.bilgeadam.rentacar.request.UpdateUserRequest;
import com.bilgeadam.rentacar.response.UserResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface UserService {

  DataResult<List<UserResponse>> getAll();

  DataResult<UserResponse> getById(String id);

  Result update(String id, UpdateUserRequest updateUserRequest);

  Result delete(String id);

  boolean checkIfUserEmailAlreadyExists(String email);

    Result login(String username, String password);
}
