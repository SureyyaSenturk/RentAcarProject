package com.bilgeadam.rentacar.config.mapper;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.business.response.CorporateCustomerResponse;
import com.bilgeadam.rentacar.business.request.CreateCorporateCustomerRequest;
import com.bilgeadam.rentacar.entity.CorporateCustomer;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CorporateCustomerMapper
    extends BaseMapper<
        CorporateCustomer, CreateCorporateCustomerRequest, CorporateCustomerResponse> {

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CorporateCustomer, CorporateCustomerResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setEmail(source.getEmail());
            map().setPassword(source.getPassword());
            map().setCompanyName(source.getCompanyName());
            map().setTaxNumber(source.getTaxNumber());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateCorporateCustomerRequest, CorporateCustomer>() {
          protected void configure() {
            map().setEmail(source.getEmail());
            map().setTaxNumber(source.getTaxNumber());
            map().setPassword(source.getPassword());
            map().setCompanyName(source.getCompanyName());
          }
        });
  }

  @Override
  public List<CorporateCustomerResponse> entityListToResponseList(
      final List<CorporateCustomer> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public CorporateCustomer requestToEntity(final CreateCorporateCustomerRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public CorporateCustomerResponse entityToResponse(final CorporateCustomer source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
