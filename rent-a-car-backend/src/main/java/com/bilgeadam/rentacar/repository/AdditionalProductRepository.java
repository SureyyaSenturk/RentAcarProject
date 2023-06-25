package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import com.bilgeadam.rentacar.entity.AdditionalProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalProductRepository extends BaseRepository<AdditionalProduct, String> {
  boolean existsByName(String additionalServiceName);
}
