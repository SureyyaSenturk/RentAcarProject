package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.config.MyUserDetails;
import com.bilgeadam.rentacar.entity.User;
import com.bilgeadam.rentacar.repository.UserRepository;
import java.util.Objects;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
  private final UserRepository userRepository;

  public CustomUserDetailService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = this.userRepository.findByEmail(email);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("There is no user with following email adress:" + email);
    }
    return new MyUserDetails(user);
  }
}
