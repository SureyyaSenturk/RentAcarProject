package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.CarDamage;
import com.bilgeadam.rentacar.config.mapper.CarDamageMapper;
import com.bilgeadam.rentacar.repository.CarDamageRepository;
import com.bilgeadam.rentacar.business.request.CreateCarDamageRequest;
import com.bilgeadam.rentacar.business.request.UpdateCarDamageRequest;
import com.bilgeadam.rentacar.business.response.CarDamageResponse;
import com.bilgeadam.rentacar.business.service.CarDamageService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CarDamageServiceImpl implements CarDamageService {
  private final CarDamageRepository carDamageRepository;
  private final CarDamageMapper carDamageMapper;

  public CarDamageServiceImpl(
      final CarDamageRepository carDamageRepository, final CarDamageMapper carDamageMapper) {
    this.carDamageRepository = carDamageRepository;
    this.carDamageMapper = carDamageMapper;
  }

  @Override
  public DataResult<List<CarDamageResponse>> getAll() {
    final List<CarDamage> carDamages = carDamageRepository.findAll();
    final List<CarDamageResponse> carDamageResponses =
        carDamageMapper.entityListToResponseList(carDamages);
    return new SuccessDataResult(
        carDamageResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateCarDamageRequest createCarDamageRequest) {
    carDamageDuplicationCheck(createCarDamageRequest.getDescription());
    final CarDamage carDamage = carDamageMapper.requestToEntity(createCarDamageRequest);
    this.carDamageRepository.save(carDamage);
    return new SuccessDataResult<>(
        createCarDamageRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<CarDamageResponse> getById(String id) {
    final CarDamage carDamage = getCarDamageById(id);
    final CarDamageResponse carDamageResponse = carDamageMapper.entityToResponse(carDamage);
    return new SuccessDataResult<>(
        carDamageResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateCarDamageRequest updateCarDamageRequest) {
    carDamageDuplicationCheck(updateCarDamageRequest.getDamageDescription());
    final CarDamage carDamage = getCarDamageById(id);
    carDamageUpdateOperations(carDamage, updateCarDamageRequest);
    final CarDamageResponse carDamageResponse = carDamageMapper.entityToResponse(carDamage);
    this.carDamageRepository.save(carDamage);
    return new SuccessDataResult<>(
        carDamageResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id) {
    final CarDamage carDamage = getCarDamageById(id);
    final CarDamageResponse carDamageResponse = carDamageMapper.entityToResponse(carDamage);
    carDamage.setState((short) 0);
    this.carDamageRepository.save(carDamage);
    return new SuccessDataResult<>(
        carDamageResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public CarDamage getCarDamageById(String id) {
    return getCarDamageWithId(id);
  }

  private void carDamageDuplicationCheck(String damageDescription) {

    if (this.carDamageRepository.existsByDescription(damageDescription)) {
      throw new BusinessException(
          BusinessMessages.CarDamageMessages.CAR_DAMAGE_ALREADY_EXISTS + damageDescription);
    }
  }

  private CarDamage getCarDamageWithId(String id) {
    return carDamageRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    BusinessMessages.CarDamageMessages.CAR_DAMAGE_NOT_FOUND + id));
  }

  private void carDamageUpdateOperations(
      CarDamage carDamage, UpdateCarDamageRequest updateCarDamageRequest) {
    carDamage.setDescription(updateCarDamageRequest.getDamageDescription());
  }
}
