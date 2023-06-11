package com.bilgeadam.rentacar.carmaintenance;

import com.bilgeadam.rentacar.car.CarResponse;
import com.bilgeadam.rentacar.common.response.BaseResponse;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceResponse extends BaseResponse {

  private String description;
  private LocalDate returnDate;
  private CarResponse carResponse;
}
