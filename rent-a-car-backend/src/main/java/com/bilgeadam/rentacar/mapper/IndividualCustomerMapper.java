package com.bilgeadam.rentacar.mapper;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.request.CreateIndividualCustomerRequest;

import java.util.List;

import com.bilgeadam.rentacar.entity.IndividualCustomer;
import com.bilgeadam.rentacar.response.IndividualCustomerResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class IndividualCustomerMapper
    extends BaseMapper<
        IndividualCustomer, CreateIndividualCustomerRequest, IndividualCustomerResponse> {

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<IndividualCustomer, IndividualCustomerResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setEmail(source.getEmail());
            map().setFirstName(source.getFirstName());
            map().setLastName(source.getLastName());
            map().setPassword(source.getPassword());
            map().setNationalIdentity(source.getNationalIdentity());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateIndividualCustomerRequest, IndividualCustomer>() {
          protected void configure() {
            map().setEmail(source.getEmail());
            map().setFirstName(source.getFirstName());
            map().setLastName(source.getLastName());
            map().setPassword(source.getPassword());
            map().setNationalIdentity(source.getNationalIdentity());
          }
        });
  }

  @Override
  public List<IndividualCustomerResponse> entityListToResponseList(
      final List<IndividualCustomer> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public IndividualCustomer requestToEntity(final CreateIndividualCustomerRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public IndividualCustomerResponse entityToResponse(final IndividualCustomer source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
