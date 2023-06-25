package com.bilgeadam.rentacar.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.AdditionalProduct;
import com.bilgeadam.rentacar.entity.RentalCar;
import com.bilgeadam.rentacar.mapper.AdditionalProductMapper;
import com.bilgeadam.rentacar.service.AdditionalProductService;
import com.bilgeadam.rentacar.service.RentalCarService;
import com.bilgeadam.rentacar.repository.AdditionalProductRepository;
import com.bilgeadam.rentacar.request.CreateAdditionalProductRequest;
import com.bilgeadam.rentacar.request.UpdateAdditionalProductRequest;
import com.bilgeadam.rentacar.response.AdditionalProductResponse;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdditionalProductServiceImp implements AdditionalProductService {

  private final AdditionalProductRepository additionalProductRepository;
  private final RentalCarService rentalCarService;

  private final AdditionalProductMapper additionalProductMapper;

  public AdditionalProductServiceImp(
      final AdditionalProductRepository additionalProductRepository,
      final RentalCarService rentalCarService,
      final AdditionalProductMapper additionalProductMapper) {
    this.additionalProductRepository = additionalProductRepository;
    this.rentalCarService = rentalCarService;
    this.additionalProductMapper = additionalProductMapper;
  }

  @Override
  public DataResult<List<AdditionalProductResponse>> getAll() {
    final List<AdditionalProduct> additionalProducts = additionalProductRepository.findAll();
    final List<AdditionalProductResponse> additionalProductResponses =
        additionalProductMapper.entityListToResponseList(additionalProducts);
    return new SuccessDataResult<>(
        additionalProductResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateAdditionalProductRequest createAdditionalProductRequest) {
    additionalProductDuplicationCheck(createAdditionalProductRequest.getName());
    final AdditionalProduct additionalProduct =
        additionalProductMapper.requestToEntity(createAdditionalProductRequest);
    this.additionalProductRepository.save(additionalProduct);
    return new SuccessDataResult<>(
        createAdditionalProductRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<AdditionalProductResponse> getById(String id) {
    final AdditionalProduct additionalProduct = getAdditionalProductById(id);
    final AdditionalProductResponse additionalProductResponse =
        additionalProductMapper.entityToResponse(additionalProduct);
    return new SuccessDataResult<>(
        additionalProductResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateAdditionalProductRequest updateAdditionalProductRequest) {
    final AdditionalProduct additionalProduct = getAdditionalProductById(id);
    additionalProductDuplicationCheck(updateAdditionalProductRequest.getAdditionalServiceName());
    additionalServiceUpdateOperations(additionalProduct, updateAdditionalProductRequest);
    final AdditionalProductResponse additionalProductResponse =
        additionalProductMapper.entityToResponse(additionalProduct);
    this.additionalProductRepository.save(additionalProduct);
    return new SuccessDataResult<>(
        additionalProductResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id) {
    final AdditionalProduct additionalProduct = getAdditionalProductById(id);
    checkIfRentalCarHasDeletedAdditionalServiceId(id);
    additionalProduct.setState((short) 0);
    AdditionalProductResponse additionalProductResponse =
        additionalProductMapper.entityToResponse(additionalProduct);
    additionalProductRepository.save(additionalProduct);
    return new SuccessDataResult<>(
        additionalProductResponse,
        BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public AdditionalProduct getAdditionalServiceById(String id) {
    return getAdditionalProductById(id);
  }

  private void additionalProductDuplicationCheck(String additionalServiceName) {
    if (this.additionalProductRepository.existsByName(additionalServiceName)) {
      throw new BusinessException(
          BusinessMessages.AdditionalProductMessages.ADDITIONAL_PRODUCT_ALREADY_EXISTS
              + additionalServiceName);
    }
  }

  private AdditionalProduct getAdditionalProductById(String id) {
    return additionalProductRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    BusinessMessages.AdditionalProductMessages.ADDITIONAL_PRODUCT_NOT_FOUND + id));
  }

  private void additionalServiceUpdateOperations(
      AdditionalProduct additionalProduct,
      UpdateAdditionalProductRequest updateAdditionalProductRequest) {

    additionalProduct.setName(updateAdditionalProductRequest.getAdditionalServiceName());
    additionalProduct.setDailyPrice(
        updateAdditionalProductRequest.getAdditionalServiceDailyPrice());
  }

  private void checkIfRentalCarHasDeletedAdditionalServiceId(String id) {

    List<RentalCar> rentalCars = this.rentalCarService.getAllRentalCars();

    List<String> rentalCarIds = new ArrayList<>();
    for (RentalCar rentalCar : rentalCars) {
      if (rentalCar
          .getAdditionalProducts()
          .contains(this.additionalProductRepository.getById(id))) {
        rentalCarIds.add(rentalCar.getId());
      }
    }

    if (!rentalCarIds.isEmpty()) {
      throw new BusinessException(
          BusinessMessages.AdditionalProductMessages.ADDITIONAL_PRODUCT_ORDERED_BY_RENTAL_CARS
              + rentalCarIds);
    }
  }
}
