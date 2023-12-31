package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.entity.Brand;
import com.bilgeadam.rentacar.business.request.CreateBrandRequest;
import com.bilgeadam.rentacar.business.request.UpdateBrandRequest;
import com.bilgeadam.rentacar.business.response.BrandResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface BrandService {

  DataResult<List<BrandResponse>> getAll();

  Result add(CreateBrandRequest createBrandRequest);

  DataResult<BrandResponse> getById(String id);

  Result update(String id, UpdateBrandRequest updateBrandRequest);

  Result delete(String id);

  Brand getBrandWithId(String id);
}
