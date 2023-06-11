package com.bilgeadam.rentacar.additionalservice;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalProductRepository extends BaseRepository<AdditionalProduct, String> {
  boolean existsByName(String additionalServiceName);
}
