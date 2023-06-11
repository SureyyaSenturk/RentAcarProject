package com.bilgeadam.rentacar.carmaintenance;

import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface CarMaintenanceService {

  DataResult<List<CarMaintenanceResponse>> getAll();

  Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);

  DataResult<CarMaintenanceResponse> getById(String id);

  Result update(String id, UpdateCarMaintenanceRequest updateCarMaintenanceRequest);

  Result delete(String id);

  List<CarMaintenanceResponse> getAllCarMaintenancesByCarId(String carId);
}
