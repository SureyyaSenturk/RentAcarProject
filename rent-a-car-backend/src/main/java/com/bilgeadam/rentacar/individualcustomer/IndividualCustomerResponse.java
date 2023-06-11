package com.bilgeadam.rentacar.individualcustomer;

import com.bilgeadam.rentacar.common.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerResponse extends BaseResponse {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String nationalIdentity;
}

