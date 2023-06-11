package com.bilgeadam.rentacar.brand;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends BaseRepository<Brand, String> {

  boolean existsByName(String name);
}
