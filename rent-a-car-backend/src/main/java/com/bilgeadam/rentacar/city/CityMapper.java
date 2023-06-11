package com.bilgeadam.rentacar.city;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.util.Mapper;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CityMapper extends BaseMapper<City, CreateCityRequest, CityResponse> {

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<City, CityResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setCode(source.getCode());
            map().setName(source.getName());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateCityRequest, City>() {
          protected void configure() {
            map().setCode(source.getCode());
            map().setName(source.getName());
          }
        });
  }

  @Override
  public List<CityResponse> entityListToResponseList(final List<City> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public City requestToEntity(final CreateCityRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public CityResponse entityToResponse(final City source) {
    return super.entityToResponse(source);
  }


  @Override
  protected void configure() {

  }
}
