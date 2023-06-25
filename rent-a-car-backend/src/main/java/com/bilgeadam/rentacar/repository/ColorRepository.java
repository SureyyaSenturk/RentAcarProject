package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.entity.Color;
import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends BaseRepository<Color, String> {
  boolean existsByName(String name);
}
