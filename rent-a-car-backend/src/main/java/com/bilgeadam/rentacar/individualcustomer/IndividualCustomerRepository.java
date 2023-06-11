package com.bilgeadam.rentacar.individualcustomer;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualCustomerRepository extends BaseRepository<IndividualCustomer, String> {
  boolean existsIndividualCustomerByNationalIdentity(String nationalIdentity);
}
