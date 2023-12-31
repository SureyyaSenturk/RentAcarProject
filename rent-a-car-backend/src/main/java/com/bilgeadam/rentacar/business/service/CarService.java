package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.entity.Car;
import com.bilgeadam.rentacar.business.request.CreateCarRequest;
import com.bilgeadam.rentacar.business.request.UpdateCarRequest;
import com.bilgeadam.rentacar.business.response.CarResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface CarService {
  DataResult<List<CarResponse>> getAll();
  Result add(CreateCarRequest createCarRequest);
  Result update(String id, UpdateCarRequest updateCarRequest);
  Result delete(String id);
  DataResult<CarResponse> getById(String id);
  DataResult<List<CarResponse>> findByDailyPriceLessThanEqual(double dailyPrice);
  DataResult<List<CarResponse>> getAllPaged(int pageNo, int pageSize);
  DataResult<List<CarResponse>> getAllSortedByDailyPrice(Sort.Direction sortDirection);
  void carKilometerSetOperation(String carId, double kilometer);

  Car getCarById(String id);
}
