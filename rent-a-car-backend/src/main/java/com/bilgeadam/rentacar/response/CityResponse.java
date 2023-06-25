package com.bilgeadam.rentacar.response;

import com.bilgeadam.rentacar.common.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse extends BaseResponse {

  private Integer code;
  private String name;
}
