package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.entity.CarDamage;
import com.bilgeadam.rentacar.business.request.CreateCarDamageRequest;
import com.bilgeadam.rentacar.business.request.UpdateCarDamageRequest;
import com.bilgeadam.rentacar.business.response.CarDamageResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface CarDamageService {
    DataResult<List<CarDamageResponse>> getAll();
    Result add(CreateCarDamageRequest createCarDamageRequest);
    DataResult<CarDamageResponse> getById(String id);
    Result update(String id, UpdateCarDamageRequest updateCarDamageRequest);
    Result delete(String id);
    CarDamage getCarDamageById(String id);
}
