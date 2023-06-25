package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.entity.Customer;
import com.bilgeadam.rentacar.business.request.UpdateCustomerRequest;
import com.bilgeadam.rentacar.business.response.CustomerResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface CustomerService {
  DataResult<List<CustomerResponse>> getAll();

  DataResult<CustomerResponse> getById(String id) ;

  Result update(String id, UpdateCustomerRequest updateCustomerRequest) ;

  Result delete(String id) ;

  Customer getCustomerById(String id);
}
