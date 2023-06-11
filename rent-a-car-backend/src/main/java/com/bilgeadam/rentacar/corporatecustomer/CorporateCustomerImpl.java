package com.bilgeadam.rentacar.corporatecustomer;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.config.Authority;
import com.bilgeadam.rentacar.customer.CustomerService;
import com.bilgeadam.rentacar.user.UserService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CorporateCustomerImpl implements CorporateCustomerService {

  private final CorporateCustomerRepository corporateCustomerRepository;
  private final CustomerService customerService;
  private final UserService userService;
  private final CorporateCustomerMapper corporateCustomerMapper;

  public CorporateCustomerImpl(
      CorporateCustomerRepository corporateCustomerRepository,
      @Lazy CustomerService customerService,
      @Lazy UserService userService,
      final CorporateCustomerMapper corporateCustomerMapper) {
    this.corporateCustomerRepository = corporateCustomerRepository;
    this.customerService = customerService;
    this.userService = userService;
    this.corporateCustomerMapper = corporateCustomerMapper;
  }

  @Override
  public DataResult<List<CorporateCustomerResponse>> getAll() {
    final List<CorporateCustomer> all = corporateCustomerRepository.findAll();
    final List<CorporateCustomerResponse> corporateCustomerResponses =
        corporateCustomerMapper.entityListToResponseList(all);
    return new SuccessDataResult<>(
        corporateCustomerResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest)
       {
    checkIfCorporateCustomerEmailAlreadyExists(createCorporateCustomerRequest.getEmail());
    checkIfCorporateCustomerTaxNumberAlreadyExists(createCorporateCustomerRequest.getTaxNumber());
    final CorporateCustomer corporateCustomer =
        corporateCustomerMapper.requestToEntity(createCorporateCustomerRequest);
    corporateCustomer.setAuthority(Authority.CUSTOMER.getAuthority());
    this.corporateCustomerRepository.save(corporateCustomer);
    return new SuccessDataResult<>(
        createCorporateCustomerRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<CorporateCustomerResponse> getById(String id)  {
    final CorporateCustomer corporateCustomer = getCorporateCustomerByUsingId(id);
    final CorporateCustomerResponse corporateCustomerResponse =
        corporateCustomerMapper.entityToResponse(corporateCustomer);
    return new SuccessDataResult<>(
        corporateCustomerResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result delete(String id)  {
    final CorporateCustomer corporateCustomer = getCorporateCustomerByUsingId(id);
    final CorporateCustomerResponse corporateCustomerResponse =
        corporateCustomerMapper.entityToResponse(corporateCustomer);
    corporateCustomer.setState((short) 0);
    this.corporateCustomerRepository.save(corporateCustomer);
    return new SuccessDataResult<>(
        corporateCustomerResponse,
        BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public Result update(String id, UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
       {
    final CorporateCustomer corporateCustomer = getCorporateCustomerByUsingId(id);
    checkIfCorporateCustomerEmailAlreadyExists(updateCorporateCustomerRequest.getEmail());
    checkIfCorporateCustomerTaxNumberAlreadyExists(updateCorporateCustomerRequest.getTaxNumber());
    corporateCustomerUpdateOperations(corporateCustomer, updateCorporateCustomerRequest);
    final CorporateCustomerResponse corporateCustomerResponse =
        corporateCustomerMapper.entityToResponse(corporateCustomer);
    this.corporateCustomerRepository.save(corporateCustomer);
    return new SuccessDataResult<>(
        corporateCustomerResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public CorporateCustomer getCorporateCustomerById(final String id) {
    return getCorporateCustomerByUsingId(id);
  }

  private void corporateCustomerUpdateOperations(
      CorporateCustomer corporateCustomer,
      UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

    corporateCustomer.setEmail(updateCorporateCustomerRequest.getEmail());
    corporateCustomer.setPassword(updateCorporateCustomerRequest.getPassword());
    corporateCustomer.setCompanyName(updateCorporateCustomerRequest.getCompanyName());
    corporateCustomer.setTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
  }

  private CorporateCustomer getCorporateCustomerByUsingId(String id)  {
    return corporateCustomerRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_NOT_FOUND + id));
  }

  private void checkIfCorporateCustomerEmailAlreadyExists(String email)  {
    if (this.userService.checkIfUserEmailAlreadyExists(email)) {
      throw new BusinessException(
          BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_EMAIL_ALREADY_EXISTS
              + email);
    }
  }

  private void checkIfCorporateCustomerTaxNumberAlreadyExists(String taxNumber)
       {
    if (this.corporateCustomerRepository.existsCorporateCustomerByTaxNumber(taxNumber)) {
      throw new BusinessException(
          BusinessMessages.CorporateCustomerMessages.CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS
              + taxNumber);
    }
  }
}
