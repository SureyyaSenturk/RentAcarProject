package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import com.bilgeadam.rentacar.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, String> {

  User findByEmail(String email);

  Optional<User> findUserByEmail(String email);
  boolean existsUserByEmail(String email);
}
