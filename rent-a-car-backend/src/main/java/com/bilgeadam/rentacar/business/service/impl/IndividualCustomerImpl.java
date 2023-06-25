package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.IndividualCustomer;
import com.bilgeadam.rentacar.business.request.CreateIndividualCustomerRequest;
import com.bilgeadam.rentacar.business.request.UpdateIndividualCustomerRequest;
import com.bilgeadam.rentacar.config.mapper.IndividualCustomerMapper;
import com.bilgeadam.rentacar.repository.IndividualCustomerRepository;
import com.bilgeadam.rentacar.business.response.IndividualCustomerResponse;
import com.bilgeadam.rentacar.business.service.IndividualCustomerService;
import com.bilgeadam.rentacar.business.service.UserService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class IndividualCustomerImpl implements IndividualCustomerService {

  private final IndividualCustomerRepository individualCustomerRepository;
  private final UserService userService;

  private final IndividualCustomerMapper individualCustomerMapper;

  public IndividualCustomerImpl(
      IndividualCustomerRepository individualCustomerRepository,
      UserService userService,
      final IndividualCustomerMapper individualCustomerMapper) {
    this.individualCustomerRepository = individualCustomerRepository;
    this.individualCustomerMapper = individualCustomerMapper;
    this.userService = userService;
  }

  @Override
  public DataResult<List<IndividualCustomerResponse>> getAll() {
    final List<IndividualCustomer> individualCustomers = individualCustomerRepository.findAll();
    final List<IndividualCustomerResponse> individualCustomerResponses =
        individualCustomerMapper.entityListToResponseList(individualCustomers);
    return new SuccessDataResult<>(
        individualCustomerResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest)
       {
    checkIfIndividualCustomerEmailAlreadyExists(createIndividualCustomerRequest.getEmail());
    checkIfIndividualCustomerNationalIdentityAlreadyExists(
        createIndividualCustomerRequest.getNationalIdentity());
    final IndividualCustomer individualCustomer =
        individualCustomerMapper.requestToEntity(createIndividualCustomerRequest);
    this.individualCustomerRepository.save(individualCustomer);
    return new SuccessDataResult<>(
        createIndividualCustomerRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<IndividualCustomerResponse> getById(String id)  {
    final IndividualCustomer individualCustomer = getIndividualCustomerWithId(id);
    final IndividualCustomerResponse individualCustomerResponse = individualCustomerMapper.entityToResponse(individualCustomer);
    return new SuccessDataResult<>(
        individualCustomerResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result delete(String id)  {
    final IndividualCustomer individualCustomer = getIndividualCustomerWithId(id);
    individualCustomer.setState((short) 0);
    this.individualCustomerRepository.deleteById(id);
    final IndividualCustomerResponse individualCustomerResponse = individualCustomerMapper.entityToResponse(individualCustomer);
    return new SuccessDataResult<>(
        individualCustomerResponse,
        BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public Result update(String id, UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
       {
    checkIfIndividualCustomerEmailAlreadyExists(updateIndividualCustomerRequest.getEmail());
    checkIfIndividualCustomerNationalIdentityAlreadyExists(
        updateIndividualCustomerRequest.getNationalIdentity());
    final IndividualCustomer individualCustomer = getIndividualCustomerWithId(id);
    individualCustomerUpdateOperations(individualCustomer, updateIndividualCustomerRequest);
    final IndividualCustomerResponse individualCustomerResponse = individualCustomerMapper.entityToResponse(individualCustomer);
    return new SuccessDataResult<>(
        individualCustomerResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public IndividualCustomer getIndividualCustomerById(final String id) {
    return getIndividualCustomerWithId(id);
  }

  private void individualCustomerUpdateOperations(
      IndividualCustomer individualCustomer,
      UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
    individualCustomer.setEmail(updateIndividualCustomerRequest.getEmail());
    individualCustomer.setFirstName(updateIndividualCustomerRequest.getFirstName());
    individualCustomer.setLastName(updateIndividualCustomerRequest.getLastName());
    individualCustomer.setNationalIdentity(updateIndividualCustomerRequest.getNationalIdentity());
  }

  private void checkIfIndividualCustomerEmailAlreadyExists(String email)  {
    if (this.userService.checkIfUserEmailAlreadyExists(email)) {
      throw new BusinessException(
          BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_EMAIL_ALREADY_EXISTS
              + email);
    }
  }

  private void checkIfIndividualCustomerNationalIdentityAlreadyExists(String nationalIdentity)
       {
    if (this.individualCustomerRepository.existsIndividualCustomerByNationalIdentity(
        nationalIdentity)) {
      throw new BusinessException(
          BusinessMessages.IndividualCustomerMessages
                  .INDIVIDUAL_CUSTOMER_NATIONAL_IDENTITY_ALREADY_EXISTS
              + nationalIdentity);
    }
  }

  private IndividualCustomer getIndividualCustomerWithId(String id)  {
    return individualCustomerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    BusinessMessages.IndividualCustomerMessages.INDIVIDUAL_CUSTOMER_NOT_FOUND
                        + id));
  }
}
