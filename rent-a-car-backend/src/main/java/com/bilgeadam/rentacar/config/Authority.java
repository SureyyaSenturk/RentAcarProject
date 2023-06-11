package com.bilgeadam.rentacar.config;

import java.util.Arrays;
import java.util.Locale;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Authority implements GrantedAuthority {
  ADMIN,
  PRODUCT_OWNER,
  USER,
  CUSTOMER;

  @Override
  public String getAuthority() {
    return name();
  }
}

class AuthorityUtil {
  public static String[] getAllAuthorities() {
    return Arrays.stream(Authority.values())
        .map(authority -> authority.getAuthority().toUpperCase(Locale.ENGLISH))
        .toArray(String[]::new);
  }
}
