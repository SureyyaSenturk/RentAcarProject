package com.bilgeadam.rentacar.mapper;

import com.bilgeadam.rentacar.service.BrandService;
import com.bilgeadam.rentacar.response.CarResponse;
import com.bilgeadam.rentacar.request.CreateCarRequest;
import com.bilgeadam.rentacar.service.CarDamageService;
import com.bilgeadam.rentacar.service.ColorService;
import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.entity.Car;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class CarMapper extends BaseMapper<Car, CreateCarRequest, CarResponse> {
  private final CarDamageMapper carDamageMapper;
  private final CarDamageService carDamageService;
  private final BrandService brandService;
  private final ColorService colorService;

  public CarMapper(
      final CarDamageMapper carDamageMapper,
      final CarDamageService carDamageService,
      final BrandService brandService,
      final ColorService colorService) {
    this.carDamageMapper = carDamageMapper;
    this.carDamageService = carDamageService;
    this.brandService = brandService;
    this.colorService = colorService;
  }

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<Car, CarResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setDailyPrice(source.getDailyPrice());
            map().setModelYear(source.getModelYear());
            map().setDescription(source.getDescription());
            map().setKilometerInformation(source.getKilometerInformation());
            map().setBrandName(source.getBrand().getName());
            map().setColorName(source.getColor().getName());
            map().setImageUrl(source.getImageUrl());
            map()
                .setCarDamages(
                    source.getCarDamages().stream()
                        .map(carDamageMapper::entityToResponse)
                        .toList());
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateCarRequest, Car>() {
          protected void configure() {
            map().setDailyPrice(source.getDailyPrice());
            map().setModelYear(source.getModelYear());
            map().setDescription(source.getDescription());
            map().setKilometerInformation(source.getKilometerInformation());
            map().setBrand(brandService.getBrandWithId(source.getBrandId()));
            map().setColor(colorService.getColorWithId(source.getColorId()));
            map().setImageUrl(source.getImageUrl());
            map()
                .setCarDamages(
                    source.getCarDamageIds().stream()
                        .map(carDamageService::getCarDamageById)
                        .toList());
          }
        });
  }

  @Override
  public List<CarResponse> entityListToResponseList(final List<Car> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public Car requestToEntity(final CreateCarRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public CarResponse entityToResponse(final Car source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
