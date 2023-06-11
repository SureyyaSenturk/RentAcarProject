package com.bilgeadam.rentacar.additionalservice;

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
