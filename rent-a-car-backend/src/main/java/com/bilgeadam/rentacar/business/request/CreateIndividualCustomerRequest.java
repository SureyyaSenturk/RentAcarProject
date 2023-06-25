package com.bilgeadam.rentacar.business.request;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.common.request.BaseRequest;
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
public class CreateIndividualCustomerRequest extends BaseRequest {
  @Email private String email;
  @NotNull private String password;

  @NotNull
  @Pattern(
      regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{2,50}",
      message = BusinessMessages.IndividualCustomerRequestsMessages.FIRST_NAME_REGEX_MESSAGE)
  private String firstName;

  @NotNull
  @Pattern(
      regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{2,50}",
      message = BusinessMessages.IndividualCustomerRequestsMessages.LAST_NAME_REGEX_MESSAGE)
  private String lastName;

  @NotNull
  @Pattern(
      regexp = "^[0-9]{11}",
      message = BusinessMessages.IndividualCustomerRequestsMessages.NATIONAL_IDENTITY_REGEX_MESSAGE)
  private String nationalIdentity;
}
