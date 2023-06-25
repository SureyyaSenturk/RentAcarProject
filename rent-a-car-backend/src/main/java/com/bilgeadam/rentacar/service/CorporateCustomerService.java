package com.bilgeadam.rentacar.service;

import com.bilgeadam.rentacar.entity.CorporateCustomer;
import com.bilgeadam.rentacar.request.CreateCorporateCustomerRequest;
import com.bilgeadam.rentacar.request.UpdateCorporateCustomerRequest;
import com.bilgeadam.rentacar.response.CorporateCustomerResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface CorporateCustomerService {

    DataResult<List<CorporateCustomerResponse>> getAll();

    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) ;

    DataResult<CorporateCustomerResponse> getById(String id) ;

    Result delete(String id) ;

    Result update(String id, UpdateCorporateCustomerRequest updateCorporateCustomerRequest) ;

    CorporateCustomer getCorporateCustomerById(String id);
}
