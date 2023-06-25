package com.bilgeadam.rentacar.repository;

import com.bilgeadam.rentacar.common.repository.BaseRepository;
import com.bilgeadam.rentacar.entity.UserCardInformation;

public interface UserCardInformationRepository extends BaseRepository<UserCardInformation, String> {

  boolean existsUserCardInformationById(String cardNo);
}
