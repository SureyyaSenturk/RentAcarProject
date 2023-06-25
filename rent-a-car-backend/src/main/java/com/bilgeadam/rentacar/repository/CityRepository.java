package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.entity.City;
import com.bilgeadam.rentacar.common.repository.BaseRepository;

public interface CityRepository extends BaseRepository<City, String> {
  boolean existsCityByCode(Integer code);

  boolean existsByName(String cityName);
}
