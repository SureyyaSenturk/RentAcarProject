package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.entity.AdditionalProduct;
import com.bilgeadam.rentacar.business.request.CreateAdditionalProductRequest;
import com.bilgeadam.rentacar.business.request.UpdateAdditionalProductRequest;
import com.bilgeadam.rentacar.business.response.AdditionalProductResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface AdditionalProductService {
    DataResult<List<AdditionalProductResponse>> getAll() ;
    Result add(CreateAdditionalProductRequest createAdditionalProductRequest) ;
    DataResult<AdditionalProductResponse> getById(String id) ;
    Result update(String id, UpdateAdditionalProductRequest updateAdditionalProductRequest) ;
    Result delete(String id) ;
    AdditionalProduct getAdditionalServiceById(String id);
}
