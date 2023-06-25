package com.bilgeadam.rentacar.config.mapper;

import com.bilgeadam.rentacar.business.response.CarDamageResponse;
import com.bilgeadam.rentacar.business.request.CreateCarDamageRequest;
import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.entity.CarDamage;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CarDamageMapper
    extends BaseMapper<CarDamage, CreateCarDamageRequest, CarDamageResponse> {

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CarDamage, CarDamageResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setDescription(source.getDescription());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateCarDamageRequest, CarDamage>() {
          protected void configure() {
            map().setDescription(source.getDescription());
          }
        });
  }

  @Override
  public List<CarDamageResponse> entityListToResponseList(final List<CarDamage> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public CarDamage requestToEntity(final CreateCarDamageRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public CarDamageResponse entityToResponse(final CarDamage source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
