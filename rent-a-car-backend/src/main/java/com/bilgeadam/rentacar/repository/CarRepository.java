package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.entity.Car;
import com.bilgeadam.rentacar.common.repository.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends BaseRepository<Car, String> {

  List<Car> findByDailyPriceLessThanEqual(double dailyPrice);
}
