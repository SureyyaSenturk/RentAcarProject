package com.bilgeadam.rentacar.corporatecustomer;

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
