package com.bilgeadam.rentacar.rentalcar;

import com.bilgeadam.rentacar.additionalservice.AdditionalProductMapper;
import com.bilgeadam.rentacar.additionalservice.AdditionalProductService;
import com.bilgeadam.rentacar.car.CarMapper;
import com.bilgeadam.rentacar.car.CarService;
import com.bilgeadam.rentacar.city.CityMapper;
import com.bilgeadam.rentacar.city.CityService;
import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.customer.CustomerMapper;
import com.bilgeadam.rentacar.customer.CustomerService;
import com.bilgeadam.rentacar.util.Mapper;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class RentalCarMapper
    extends BaseMapper<RentalCar, CreateRentalCarRequest, RentalCarResponse> {
  private final ModelMapper modelMapper;

  private final CarService carService;
  private final CarMapper carMapper;
  private final CityService cityService;
  private final CityMapper cityMapper;
  private final CustomerService customerService;
  private final CustomerMapper customerMapper;
  private final AdditionalProductService additionalProductService;
  private final AdditionalProductMapper additionalProductMapper;

  public RentalCarMapper(
      final CarService carService,
      final CarMapper carMapper,
      final CityService cityService,
      final CityMapper cityMapper,
      final CustomerService customerService,
      final CustomerMapper customerMapper,
      @Lazy final AdditionalProductService additionalProductService,
      final AdditionalProductMapper additionalProductMapper) {
    this.carService = carService;
    this.carMapper = carMapper;
    this.cityService = cityService;
    this.cityMapper = cityMapper;
    this.customerService = customerService;
    this.customerMapper = customerMapper;
    this.additionalProductService = additionalProductService;
    this.additionalProductMapper = additionalProductMapper;

    this.modelMapper = new ModelMapper();
  }

  @Override
  protected void configureEntityToResponse() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<RentalCar, RentalCarResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setRentDate(source.getRentDate());
            map().setReturnDate(source.getReturnDate());
            map().setRentCity(cityMapper.entityToResponse(source.getRentCity()));
            map().setReturnCity(cityMapper.entityToResponse(source.getReturnCity()));
            map().setRentStartKilometer(source.getRentStartKilometer());
            map().setReturnKilometer(source.getReturnKilometer());
            map().setCarResponse(carMapper.entityToResponse(source.getCar()));
            map().setCustomerResponse(customerMapper.entityToResponse(source.getCustomer()));
            map()
                .setAdditionalServices(
                    additionalProductMapper.entityListToResponseList(
                        source.getAdditionalProducts()));
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateRentalCarRequest, RentalCar>() {
          protected void configure() {
            map().setRentDate(source.getRentDate());
            map().setReturnDate(source.getReturnDate());
            map().setReturnKilometer(source.getReturnKilometer());

          }
        });
  }

  @Override
  public List<RentalCarResponse> entityListToResponseList(final List<RentalCar> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public RentalCar requestToEntity(final CreateRentalCarRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public RentalCarResponse entityToResponse(final RentalCar source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
