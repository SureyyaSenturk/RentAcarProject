package com.bilgeadam.rentacar.mapper;

import com.bilgeadam.rentacar.response.BrandResponse;
import com.bilgeadam.rentacar.request.CreateBrandRequest;
import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.entity.Brand;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper extends BaseMapper<Brand, CreateBrandRequest, BrandResponse> {

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<Brand, BrandResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setName(source.getName());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateBrandRequest, Brand>() {
          protected void configure() {
            map().setName(source.getName());
          }
        });
  }

  @Override
  public List<BrandResponse> entityListToResponseList(final List<Brand> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public Brand requestToEntity(final CreateBrandRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public BrandResponse entityToResponse(final Brand source) {
    return super.entityToResponse(source);
  }


  @Override
  protected void configure() {

  }
}
