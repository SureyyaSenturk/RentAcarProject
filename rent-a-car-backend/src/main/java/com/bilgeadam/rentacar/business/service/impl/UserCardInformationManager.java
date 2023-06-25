package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.UserCardInformation;
import com.bilgeadam.rentacar.business.request.CreateUserCardInformationRequest;
import com.bilgeadam.rentacar.business.request.UpdateUserCardInformationRequest;
import com.bilgeadam.rentacar.config.mapper.UserCardInformationMapper;
import com.bilgeadam.rentacar.repository.UserCardInformationRepository;
import com.bilgeadam.rentacar.business.response.UserCardInformationResponse;
import com.bilgeadam.rentacar.business.service.CustomerService;
import com.bilgeadam.rentacar.business.service.UserCardInformationService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserCardInformationManager implements UserCardInformationService {

  private final UserCardInformationRepository userCardInformationRepository;
  private final CustomerService customerService;
  private final UserCardInformationMapper userCardInformationMapper;

  public UserCardInformationManager(
      UserCardInformationRepository userCardInformationRepository,
      CustomerService customerService,
      final UserCardInformationMapper userCardInformationMapper) {
    this.userCardInformationRepository = userCardInformationRepository;
    this.customerService = customerService;
    this.userCardInformationMapper = userCardInformationMapper;
  }

  @Override
  public DataResult<List<UserCardInformationResponse>> getAll() {
    final List<UserCardInformation> userCardInformations = userCardInformationRepository.findAll();
    final List<UserCardInformationResponse> userCardInformationResponses =
        userCardInformationMapper.entityListToResponseList(userCardInformations);
    return new SuccessDataResult<>(
        userCardInformationResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateUserCardInformationRequest createUserCardInformationRequest) {
    checkIfCardNoAlreadyExists(createUserCardInformationRequest.getCardNo());
    checkIfCustomerIdExists(createUserCardInformationRequest.getCustomerId());
    final UserCardInformation userCardInformation =
        userCardInformationMapper.requestToEntity(createUserCardInformationRequest);
    this.userCardInformationRepository.save(userCardInformation);
    return new SuccessDataResult<>(
        createUserCardInformationRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<UserCardInformationResponse> getById(String id) {
    final UserCardInformation userCardInformation = getUserCardInformationIdAByUsingId(id);
    final UserCardInformationResponse userCardInformationResponse =
        userCardInformationMapper.entityToResponse(userCardInformation);
    return new SuccessDataResult<>(
        userCardInformationResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(
      String id, UpdateUserCardInformationRequest updateUserCardInformationRequest) {
    checkIfCardNoAlreadyExists(
        updateUserCardInformationRequest.getPaymentInformations().getCardNo());
    checkIfCustomerIdExists(updateUserCardInformationRequest.getCustomerId());
    final UserCardInformation userCardInformation = getUserCardInformationIdAByUsingId(id);
    updateUserCardInformationOperations(userCardInformation, updateUserCardInformationRequest);
    final UserCardInformationResponse userCardInformationResponse =
        userCardInformationMapper.entityToResponse(userCardInformation);
    return new SuccessDataResult<>(
        userCardInformationResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id) {
    final UserCardInformation userCardInformation = getUserCardInformationIdAByUsingId(id);
    final UserCardInformationResponse userCardInformationResponse =
        userCardInformationMapper.entityToResponse(userCardInformation);
    userCardInformation.setState((short) 0);
    this.userCardInformationRepository.save(userCardInformation);
    return new SuccessDataResult<>(
        userCardInformationResponse,
        BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  private void checkIfCardNoAlreadyExists(String cardNo) {
    if (this.userCardInformationRepository.existsUserCardInformationById(
        cardNo)) {
      throw new BusinessException(
          BusinessMessages.UserCardInformationMessages.CARD_NO_ALREADY_EXISTS);
    }
  }

  private void checkIfCustomerIdExists(String customerId) {
    if (this.customerService.getCustomerById(customerId) == null) {
      throw new BusinessException(BusinessMessages.CustomerMessages.CUSTOMER_NOT_FOUND);
    }
  }

  private UserCardInformation getUserCardInformationIdAByUsingId(String id) {
    return userCardInformationRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    BusinessMessages.UserCardInformationMessages
                        .USER_CARD_INFORMATION_ID_NOT_FOUND));
  }

  private void updateUserCardInformationOperations(
      UserCardInformation userCardInformation,
      UpdateUserCardInformationRequest updateUserCardInformationRequest) {
    userCardInformation.setCardNo(
        updateUserCardInformationRequest.getPaymentInformations().getCardNo());
    userCardInformation.setCardHolder(
        updateUserCardInformationRequest.getPaymentInformations().getCardHolder());
    userCardInformation.setExpirationYear(
        updateUserCardInformationRequest.getPaymentInformations().getExpirationYear());
    userCardInformation.setExpirationMonth(
        updateUserCardInformationRequest.getPaymentInformations().getExpirationMonth());
    userCardInformation.setCvv(updateUserCardInformationRequest.getPaymentInformations().getCvv());
    userCardInformation.setCustomer(customerService.getCustomerById(updateUserCardInformationRequest.getCustomerId()));
  }
}
