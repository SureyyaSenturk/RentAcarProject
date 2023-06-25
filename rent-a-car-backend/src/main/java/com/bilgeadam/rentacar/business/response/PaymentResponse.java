package com.bilgeadam.rentacar.business.response;

import com.bilgeadam.rentacar.common.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse extends BaseResponse {

    private String cardNo;

    private double paymentAmount;

    private RentalCarResponse rentalCarResponse;
}
