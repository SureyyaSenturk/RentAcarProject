package com.bilgeadam.rentacar.response;

import com.bilgeadam.rentacar.response.CarDamageResponse;
import com.bilgeadam.rentacar.common.response.BaseResponse;
import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse extends BaseResponse {

  private double dailyPrice;
  private int modelYear;
  private String description;
  private double kilometerInformation;
  private String brandName;
  private String colorName;
  private String imageUrl;
  private List<CarDamageResponse> carDamages;
}
