package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import com.bilgeadam.rentacar.entity.CorporateCustomer;
import org.springframework.stereotype.Repository;

@Repository
public interface CorporateCustomerRepository extends BaseRepository<CorporateCustomer, String> {

  boolean existsCorporateCustomerByTaxNumber(String taxNumber);
}
