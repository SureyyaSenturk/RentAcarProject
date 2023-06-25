package com.bilgeadam.rentacar.business.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {

    private String email;
    private String firstName;
    private String lastName;
    private String nationalIdentity;
}
