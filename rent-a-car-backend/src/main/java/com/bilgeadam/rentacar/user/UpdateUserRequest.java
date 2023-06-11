package com.bilgeadam.rentacar.user;

import com.bilgeadam.rentacar.common.request.BaseRequest;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest extends BaseRequest {

  @Email private String email;

  private String password;
}
