package com.bilgeadam.rentacar.service;

import com.bilgeadam.rentacar.entity.IndividualCustomer;
import com.bilgeadam.rentacar.request.CreateIndividualCustomerRequest;
import com.bilgeadam.rentacar.request.UpdateIndividualCustomerRequest;
import com.bilgeadam.rentacar.response.IndividualCustomerResponse;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface IndividualCustomerService {

    DataResult<List<IndividualCustomerResponse>> getAll();

    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) ;

    DataResult<IndividualCustomerResponse> getById(String id) ;

    Result delete(String id) ;

    Result update(String id, UpdateIndividualCustomerRequest updateIndividualCustomerRequest) ;

    IndividualCustomer getIndividualCustomerById(String id);
}
