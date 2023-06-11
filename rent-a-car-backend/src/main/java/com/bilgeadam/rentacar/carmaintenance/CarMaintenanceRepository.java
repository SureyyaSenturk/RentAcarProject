package com.bilgeadam.rentacar.carmaintenance;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMaintenanceRepository extends BaseRepository<CarMaintenance, String> {

  List<CarMaintenance> getByCar_Id(String carId);

  List<CarMaintenance> findAllByCar_Id(String carId);
}
