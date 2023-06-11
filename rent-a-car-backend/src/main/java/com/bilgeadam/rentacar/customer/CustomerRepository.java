package com.bilgeadam.rentacar.customer;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends BaseRepository<Customer, String> {

    boolean existsCustomerByEmail(String email);
}
