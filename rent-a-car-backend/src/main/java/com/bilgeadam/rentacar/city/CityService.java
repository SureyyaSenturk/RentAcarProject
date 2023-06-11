package com.bilgeadam.rentacar.city;


import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface CityService {

    DataResult<List<CityResponse>> getAll();

    Result add(CreateCityRequest createCityRequest);

    DataResult<CityResponse> getById(String id);

    Result update(String id, UpdateCityRequest updateCityRequest);

    Result delete(String id);

    boolean cityExistsById(String id);

    City getCityById(String id);
}
