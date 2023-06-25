package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import com.bilgeadam.rentacar.entity.IndividualCustomer;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualCustomerRepository extends BaseRepository<IndividualCustomer, String> {
  boolean existsIndividualCustomerByNationalIdentity(String nationalIdentity);
}
