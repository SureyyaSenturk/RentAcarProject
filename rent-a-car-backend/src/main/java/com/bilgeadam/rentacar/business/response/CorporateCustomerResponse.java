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
public class CorporateCustomerResponse extends BaseResponse {

    private String email;

    private String password;

    private String companyName;

    private String taxNumber;
}
