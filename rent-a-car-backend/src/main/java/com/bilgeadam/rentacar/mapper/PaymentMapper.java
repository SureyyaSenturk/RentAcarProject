package com.bilgeadam.rentacar.mapper;

import com.bilgeadam.rentacar.common.mapper.BaseMapper;
import com.bilgeadam.rentacar.entity.Payment;
import com.bilgeadam.rentacar.request.CreatePaymentRequest;
import com.bilgeadam.rentacar.response.PaymentResponse;
import com.bilgeadam.rentacar.service.RentalCarService;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper extends BaseMapper<Payment, CreatePaymentRequest, PaymentResponse> {

  private final RentalCarMapper rentalCarMapper;
  private final RentalCarService rentalCarService;

  public PaymentMapper(
      final RentalCarMapper rentalCarMapper, final RentalCarService rentalCarService) {
    this.rentalCarMapper = rentalCarMapper;
    this.rentalCarService = rentalCarService;
  }

  @Override
  protected void configureEntityToResponse() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<Payment, PaymentResponse>() {
          protected void configure() {
            map().setId(source.getId());
            map().setPaymentAmount(source.getPaymentAmount());
            map().setCardNo(source.getCardNo());
            map().setRentalCarResponse(rentalCarMapper.entityToResponse(source.getRentalCar()));
          }
        });
  }

  @Override
  protected void configureRequestToEntity() {
    final ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(
        new PropertyMap<CreatePaymentRequest, Payment>() {
          protected void configure() {
            map().setCardNo(source.getCardNo());
            map().setCardHolder(source.getCardHolder());
            map().setExpirationMonth(source.getExpirationMonth());
            map().setExpirationYear(source.getExpirationYear());
            map().setCvv(source.getCvv());
          }
        });
  }

  @Override
  public List<PaymentResponse> entityListToResponseList(final List<Payment> source) {
    return super.entityListToResponseList(source);
  }

  @Override
  public Payment requestToEntity(final CreatePaymentRequest source) {
    return super.requestToEntity(source);
  }

  @Override
  public PaymentResponse entityToResponse(final Payment source) {
    return super.entityToResponse(source);
  }

  @Override
  protected void configure() {}
}
