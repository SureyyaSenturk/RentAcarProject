package com.bilgeadam.rentacar.color;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends BaseRepository<Color, String> {
  boolean existsByName(String name);
}
