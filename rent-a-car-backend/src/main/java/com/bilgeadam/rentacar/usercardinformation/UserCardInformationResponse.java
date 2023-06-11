package com.bilgeadam.rentacar.usercardinformation;

import com.bilgeadam.rentacar.common.response.BaseResponse;
import com.bilgeadam.rentacar.customer.CustomerResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCardInformationResponse extends BaseResponse {
    private String cardNo;
    private String cardHolder;
    private Integer expirationMonth;
    private Integer expirationYear;
    private Integer cvv;
    private CustomerResponse customerResponse;


}
