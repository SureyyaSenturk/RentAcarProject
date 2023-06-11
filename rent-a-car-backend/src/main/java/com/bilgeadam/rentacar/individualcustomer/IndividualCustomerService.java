package com.bilgeadam.rentacar.individualcustomer;

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
