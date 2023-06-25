package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.entity.CarDamage;
import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDamageRepository extends BaseRepository<CarDamage, String> {

  boolean existsByDescription(String damageDescription);
}
