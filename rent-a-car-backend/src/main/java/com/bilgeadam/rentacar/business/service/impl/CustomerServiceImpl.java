package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.Customer;
import com.bilgeadam.rentacar.config.mapper.CustomerMapper;
import com.bilgeadam.rentacar.repository.CustomerRepository;
import com.bilgeadam.rentacar.business.request.UpdateCustomerRequest;
import com.bilgeadam.rentacar.business.response.CustomerResponse;
import com.bilgeadam.rentacar.business.service.CustomerService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  public CustomerServiceImpl(
      final CustomerRepository customerRepository, final CustomerMapper customerMapper) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  @Override
  public DataResult<List<CustomerResponse>> getAll() {
    final List<Customer> customers = customerRepository.findAll();
    final List<CustomerResponse> customerResponses =
        customerMapper.entityListToResponseList(customers);
    return new SuccessDataResult<>(
        customerResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public DataResult<CustomerResponse> getById(String id)  {
    final Customer customer = getCustomerWithId(id);
    final CustomerResponse customerResponse = customerMapper.entityToResponse(customer);
    return new SuccessDataResult<>(
        customerResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateCustomerRequest updateCustomerRequest)
       {
    checkIfCustomerEmailAlreadyExists(updateCustomerRequest.getEmail());
    final Customer customer = getCustomerWithId(id);
    customerUpdateOperations(customer, updateCustomerRequest);
    final CustomerResponse customerResponse = customerMapper.entityToResponse(customer);
    return new SuccessDataResult(
        customerResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  private void customerUpdateOperations(
      Customer customer, UpdateCustomerRequest updateCustomerRequest) {
    customer.setEmail(updateCustomerRequest.getEmail());
    customer.setPassword(updateCustomerRequest.getPassword());
  }

  private void checkIfCustomerEmailAlreadyExists(String email)  {
    if (this.customerRepository.existsCustomerByEmail(email)) {
      throw new BusinessException(
          BusinessMessages.CustomerMessages.CUSTOMER_EMAIL_ALREADY_EXISTS + email);
    }
  }

  @Override
  public Result delete(String id)  {
    final Customer customer = getCustomerWithId(id);
    customer.setState((short) 0);
    this.customerRepository.save(customer);
    final CustomerResponse customerResponse = customerMapper.entityToResponse(customer);
    return new SuccessDataResult<>(
        customerResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public Customer getCustomerById(String id) {
    return getCustomerWithId(id);
  }

  private Customer getCustomerWithId(String id)  {
    return customerRepository
        .findById(id)
        .orElseThrow(
            () -> new BusinessException(BusinessMessages.CustomerMessages.CUSTOMER_NOT_FOUND + id));
  }
}
