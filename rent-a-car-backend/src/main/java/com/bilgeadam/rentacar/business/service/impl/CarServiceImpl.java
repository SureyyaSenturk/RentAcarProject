package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.entity.Car;
import com.bilgeadam.rentacar.entity.CarDamage;
import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.config.mapper.CarMapper;
import com.bilgeadam.rentacar.repository.CarRepository;
import com.bilgeadam.rentacar.business.request.CreateCarRequest;
import com.bilgeadam.rentacar.business.request.UpdateCarRequest;
import com.bilgeadam.rentacar.business.response.CarResponse;
import com.bilgeadam.rentacar.business.service.BrandService;
import com.bilgeadam.rentacar.business.service.CarDamageService;
import com.bilgeadam.rentacar.business.service.CarService;
import com.bilgeadam.rentacar.business.service.ColorService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {
  private final CarRepository carRepository;
  private final BrandService brandService;
  private final ColorService colorService;
  private final CarDamageService carDamageService;
  private final CarMapper carMapper;

  public CarServiceImpl(
      CarRepository carRepository,
      BrandService brandService,
      ColorService colorService,
      CarDamageService carDamageService,
      final CarMapper carMapper) {
    this.carRepository = carRepository;
    this.brandService = brandService;
    this.colorService = colorService;
    this.carMapper = carMapper;
    this.carDamageService = carDamageService;
  }

  @Override
  public DataResult<List<CarResponse>> getAll()  {
    final List<Car> cars = carRepository.findAll();
    final List<CarResponse> carResponses = carMapper.entityListToResponseList(cars);
    return new SuccessDataResult<>(
        carResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateCarRequest createCarRequest)  {
    final Car car = carMapper.requestToEntity(createCarRequest);
    checkIfCarCreationParametersNotNull(car);
    checkIfCarDamageListIsNullOrEmpty(createCarRequest.getCarDamageIds(), car);
    this.carRepository.save(car);
    return new SuccessDataResult<>(
        createCarRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateCarRequest updateCarRequest)  {
    final Car car = getCarWithId(id);
    checkIfCarAndUpdateParameterIsNotEqual(car, updateCarRequest);
    updateCarOperations(car, updateCarRequest);
    final CarResponse carResponse = carMapper.entityToResponse(car);
    this.carRepository.save(car);
    return new SuccessDataResult<>(
        carResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id)  {
    final Car carWithId = getCarWithId(id);
    carWithId.setState((short) 0);
    this.carRepository.save(carWithId);
    final CarResponse carResponse = carMapper.entityToResponse(carWithId);
    return new SuccessDataResult<>(
        carResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public DataResult<List<CarResponse>> findByDailyPriceLessThanEqual(double dailyPrice)
       {
    checkIfDailyPriceValid(dailyPrice);
    final List<Car> cars = this.carRepository.findByDailyPriceLessThanEqual(dailyPrice);
    final List<CarResponse> carResponses = carMapper.entityListToResponseList(cars);
    return new SuccessDataResult<>(
        carResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public DataResult<List<CarResponse>> getAllPaged(int pageNo, int pageSize)
       {
    checkIfPageNoAndPageSizeValid(pageNo, pageSize);
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
    List<Car> cars = this.carRepository.findAll(pageable).getContent();
    final List<CarResponse> carResponses = carMapper.entityListToResponseList(cars);
    return new SuccessDataResult<>(
        carResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public DataResult<List<CarResponse>> getAllSortedByDailyPrice(Sort.Direction sortDirection) {
    Sort sort = Sort.by(sortDirection, "dailyPrice");
    List<Car> cars = this.carRepository.findAll(sort);
    final List<CarResponse> carResponses = carMapper.entityListToResponseList(cars);
    return new SuccessDataResult<>(
        carResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public void carKilometerSetOperation(String carId, double kilometer) {
    final Car car = getCarById(carId);
    car.setKilometerInformation(kilometer);
    this.carRepository.save(car);
  }

  @Override
  public Car getCarById(final String id) {
    return getCarWithId(id);
  }

  @Override
  public DataResult<CarResponse> getById(String id)  {
    final Car carWithId = getCarWithId(id);
    final CarResponse carResponse = carMapper.entityToResponse(carWithId);
    return new SuccessDataResult<>(
        carResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  private void updateCarOperations(Car car, UpdateCarRequest updateCarRequest)
       {
    car.setDailyPrice(updateCarRequest.getDailyPrice());
    car.setDescription(updateCarRequest.getDescription());
    checkIfCarDamageListIsNullOrEmpty(updateCarRequest.getCarDamageIds(), car);
  }

  private Car getCarWithId(String id)  {
    return this.carRepository
        .findById(id)
        .orElseThrow(() -> new BusinessException(BusinessMessages.CarMessages.CAR_NOT_FOUND + id));
  }

  private void checkIfCarAndUpdateParameterIsNotEqual(Car car, UpdateCarRequest updateCarRequest)
       {
    List<CarDamage> tempCarDamageList = new ArrayList<>();
    for (String carDamageId : updateCarRequest.getCarDamageIds()) {
      checkIfCarDamageIdExists(carDamageId);
      CarDamage carDamage = this.carDamageService.getCarDamageById(carDamageId);
      tempCarDamageList.add(carDamage);
    }
    if (car.getDailyPrice() == updateCarRequest.getDailyPrice()
        && car.getDescription().equals(updateCarRequest.getDescription())
        && car.getCarDamages() == tempCarDamageList) {
      throw new BusinessException(BusinessMessages.CarMessages.NO_CHANGES_NO_NEED_TO_UPDATE);
    }
  }

  private void checkIfCarCreationParametersNotNull(Car car)  {
    if (this.brandService.getById(car.getBrand().getId()) == null) {
      throw new BusinessException(
          BusinessMessages.CarMessages.BRAND_NOT_FOUND + car.getBrand().getId());
    }

    if (this.colorService.getById(car.getColor().getId()) == null) {
      throw new BusinessException(
          BusinessMessages.CarMessages.COLOR_NOT_FOUND + car.getColor().getId());
    }
  }

  private void checkIfPageNoAndPageSizeValid(int pageNo, int pageSize)  {
    if (pageNo <= 0) {
      throw new BusinessException(BusinessMessages.CarMessages.PAGE_NO_CANNOT_LESS_THAN_ZERO);
    }
    if (pageSize <= 0) {
      throw new BusinessException(BusinessMessages.CarMessages.PAGE_SIZE_CANNOT_LESS_THAN_ZERO);
    }
  }

  private void checkIfDailyPriceValid(double dailyPrice)  {
    if (dailyPrice < 0) {
      throw new BusinessException(BusinessMessages.CarMessages.DAILY_PRICE_CANNOT_LESS_THAN_ZERO);
    }
  }

  private void checkIfCarDamageListIsNullOrEmpty(List<String> carDamageIds, Car car)
       {
    if (carDamageIds == null || carDamageIds.isEmpty()) {
      car.setCarDamages(null);
    } else {
      List<CarDamage> tempCarDamageList = new ArrayList<>();

      for (String carDamageId : carDamageIds) {
        final CarDamage carDamage = carDamageService.getCarDamageById(carDamageId);
        tempCarDamageList.add(carDamage);
      }
      car.setCarDamages(tempCarDamageList);
    }
  }

  private void checkIfCarDamageIdExists(String carDamageId) throws BusinessException {
    if (this.carDamageService.getById(carDamageId).getData() == null) {
      throw new BusinessException(BusinessMessages.CarMessages.CAR_DAMAGE_NOT_FOUND + carDamageId);
    }
  }
}
