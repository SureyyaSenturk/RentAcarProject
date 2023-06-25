package com.bilgeadam.rentacar.config.mapper;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.business.service.CustomerService;
import com.bilgeadam.rentacar.business.request.CreateUserCardInformationRequest;

import java.util.List;

import com.bilgeadam.rentacar.entity.UserCardInformation;
import com.bilgeadam.rentacar.business.response.UserCardInformationResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UserCardInformationMapper
    extends BaseMapper<
        UserCardInformation, CreateUserCardInformationRequest, UserCardInformationResponse> {

  private final CustomerMapper customerMapper;
  private final CustomerService customerService;

  public UserCardInformationMapper(
      final CustomerMapper customerMapper, final CustomerService customerService) {
    this.customerMapper = customerMapper;
    this.customerService = customerService;
  }

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<UserCardInformation, UserCardInformationResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setCardNo(source.getCardNo());
            map().setCardHolder(source.getCardHolder());
            map().setCvv(source.getCvv());
            map().setExpirationMonth(source.getExpirationMonth());
            map().setExpirationYear(source.getExpirationYear());
            map().setCustomerResponse(customerMapper.entityToResponse(source.getCustomer()));
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateUserCardInformationRequest, UserCardInformation>() {
          protected void configure() {
            map().setCardNo(source.getCardNo());
            map().setCardHolder(source.getCardHolder());
            map().setCvv(source.getCvv());
            map().setExpirationMonth(source.getExpirationMonth());
            map().setExpirationYear(source.getExpirationYear());
            map().setCustomer(customerService.getCustomerById(source.getCustomerId()));
          }
        });
  }

  @Override
  public List<UserCardInformationResponse> entityListToResponseList(
      final List<UserCardInformation> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public UserCardInformation requestToEntity(final CreateUserCardInformationRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public UserCardInformationResponse entityToResponse(final UserCardInformation source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {

  }
}
