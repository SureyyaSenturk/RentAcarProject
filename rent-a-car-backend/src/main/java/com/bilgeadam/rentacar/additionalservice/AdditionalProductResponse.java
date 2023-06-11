package com.bilgeadam.rentacar.additionalservice;

import com.bilgeadam.rentacar.common.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalProductResponse extends BaseResponse {

    private String name;

    private double dailyPrice;
}
