package com.bilgeadam.rentacar.mapper;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.entity.Invoice;
import com.bilgeadam.rentacar.request.CreateInvoiceRequest;
import com.bilgeadam.rentacar.response.InvoiceResponse;
import com.bilgeadam.rentacar.service.RentalCarService;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper extends BaseMapper<Invoice, CreateInvoiceRequest, InvoiceResponse> {
  private final RentalCarService rentalCarService;
  private final RentalCarMapper rentalCarMapper;

  public InvoiceMapper(
      final RentalCarService rentalCarService, final RentalCarMapper rentalCarMapper) {
    this.rentalCarService = rentalCarService;
    this.rentalCarMapper = rentalCarMapper;
  }

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<Invoice, InvoiceResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setNumber(source.getNumber());
            map().setDate(source.getDate());
            map().setAdditionalProductTotalPayment(source.getAdditionalProductTotalPayment());
            map().setRentDay(source.getRentDay());
            map().setRentPayment(source.getRentPayment());
            map().setRentLocationPayment(source.getRentLocationPayment());
            map().setTotalPayment(source.getTotalPayment());
            map().setRentalCarResponse(rentalCarMapper.entityToResponse(source.getRentalCar()));
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreateInvoiceRequest, Invoice>() {
          protected void configure() {
            map().setRentalCar(rentalCarService.getRentalCarById(source.getRentalCarId()));
          }
        });
  }

  @Override
  public List<InvoiceResponse> entityListToResponseList(final List<Invoice> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public Invoice requestToEntity(final CreateInvoiceRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public InvoiceResponse entityToResponse(final Invoice source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
