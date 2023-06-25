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
public class UserCardInformationResponse extends BaseResponse {
    private String cardNo;
    private String cardHolder;
    private Integer expirationMonth;
    private Integer expirationYear;
    private Integer cvv;
    private CustomerResponse customerResponse;


}
