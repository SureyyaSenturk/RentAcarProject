package com.bilgeadam.rentacar.customer;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.util.Mapper;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper extends BaseMapper<Customer, UpdateCustomerRequest, CustomerResponse> {


  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<Customer, CustomerResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setEmail(source.getEmail());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<UpdateCustomerRequest, Customer>() {
          protected void configure() {

          }
        });
  }

  @Override
  public List<CustomerResponse> entityListToResponseList(final List<Customer> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public Customer requestToEntity(final UpdateCustomerRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public CustomerResponse entityToResponse(final Customer source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
