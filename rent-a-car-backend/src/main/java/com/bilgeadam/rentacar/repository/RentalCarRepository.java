package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import java.util.List;

import com.bilgeadam.rentacar.entity.RentalCar;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalCarRepository extends BaseRepository<RentalCar, String> {

  List<RentalCar> getByCar_Id(String carId);

  List<RentalCar> findAllByCar_Id(String carId);
}
