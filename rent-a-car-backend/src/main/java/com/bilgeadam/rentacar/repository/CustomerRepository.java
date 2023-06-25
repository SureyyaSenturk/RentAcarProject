package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import com.bilgeadam.rentacar.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, String> {

    boolean existsCustomerByEmail(String email);
}
