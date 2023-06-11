package com.bilgeadam.rentacar.usercardinformation;

import com.bilgeadam.rentacar.common.repository.BaseRepository;

public interface UserCardInformationRepository extends BaseRepository<UserCardInformation, String> {

  boolean existsUserCardInformationById(String cardNo);
}
