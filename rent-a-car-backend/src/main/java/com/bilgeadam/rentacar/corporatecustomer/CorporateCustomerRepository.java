package com.bilgeadam.rentacar.corporatecustomer;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateCustomerRepository extends BaseRepository<CorporateCustomer, String> {

  boolean existsCorporateCustomerByTaxNumber(String taxNumber);
}
