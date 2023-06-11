package com.bilgeadam.rentacar.corporatecustomer;

import com.bilgeadam.rentacar.common.BusinessMessages;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {

  @NotNull @Email private String email;

  @NotNull private String password;

  @NotNull
  @Pattern(
      regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,50}",
      message = BusinessMessages.CorporateCustomerRequestsMessages.COMPANY_NAME_REGEX_MESSAGE)
  private String companyName;

  @NotNull
  @Pattern(
      regexp = "^[0-9]{10}",
      message = BusinessMessages.CorporateCustomerRequestsMessages.TAX_NUMBER_REGEX_MESSAGE)
  private String taxNumber;
}
