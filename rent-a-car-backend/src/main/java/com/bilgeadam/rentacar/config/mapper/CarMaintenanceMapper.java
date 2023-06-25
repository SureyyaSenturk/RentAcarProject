package com.bilgeadam.rentacar.config.mapper;

import com.bilgeadam.rentacar.business.service.CarService;
import com.bilgeadam.rentacar.business.response.CarMaintenanceResponse;
import com.bilgeadam.rentacar.business.request.CreateCarMaintenanceRequest;
import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.entity.CarMaintenance;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CarMaintenanceMapper
    extends BaseMapper<CarMaintenance, CreateCarMaintenanceRequest, CarMaintenanceResponse> {
  private final CarMapper carMapper;
  private final CarService carService;

  public CarMaintenanceMapper(final CarMapper carMapper, final CarService carService) {
    this.carMapper = carMapper;
    this.carService = carService;
  }

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CarMaintenance, CarMaintenanceResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setDescription(source.getDescription());
            map().setReturnDate(source.getReturnDate());
            map().setCarResponse(carMapper.entityToResponse(source.getCar()));
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateCarMaintenanceRequest, CarMaintenance>() {
          protected void configure() {
            map().setDescription(source.getDescription());
            map().setReturnDate(source.getReturnDate());
            map().setCar(carService.getCarById(source.getCarId()));
          }
        });
  }

  @Override
  public List<CarMaintenanceResponse> entityListToResponseList(final List<CarMaintenance> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public CarMaintenance requestToEntity(final CreateCarMaintenanceRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public CarMaintenanceResponse entityToResponse(final CarMaintenance source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
