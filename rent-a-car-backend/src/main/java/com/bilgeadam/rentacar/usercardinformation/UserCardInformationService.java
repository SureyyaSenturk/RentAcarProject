package com.bilgeadam.rentacar.usercardinformation;

import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;

public interface UserCardInformationService {

    DataResult<List<UserCardInformationResponse>> getAll() ;

    Result add(CreateUserCardInformationRequest createUserCardInformationRequest) ;

    DataResult<UserCardInformationResponse> getById(String id) ;

    Result update(String id, UpdateUserCardInformationRequest updateUserCardInformationRequest) ;

    Result delete(String id) ;
}
