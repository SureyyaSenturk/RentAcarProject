package com.bilgeadam.rentacar.mapper;

import com.bilgeadam.rentacar.response.AdditionalProductResponse;
import com.bilgeadam.rentacar.request.CreateAdditionalProductRequest;
import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.entity.AdditionalProduct;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class AdditionalProductMapper
    extends BaseMapper<
        AdditionalProduct, CreateAdditionalProductRequest, AdditionalProductResponse> {



  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<AdditionalProduct, AdditionalProductResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setName(source.getName());
            map().setDailyPrice(source.getDailyPrice());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateAdditionalProductRequest, AdditionalProduct>() {
          protected void configure() {
            map().setName(source.getName());
            map().setDailyPrice(source.getDailyPrice());
          }
        });
  }

  @Override
  public List<AdditionalProductResponse> entityListToResponseList(
      final List<AdditionalProduct> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public AdditionalProduct requestToEntity(final CreateAdditionalProductRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public AdditionalProductResponse entityToResponse(final AdditionalProduct source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
