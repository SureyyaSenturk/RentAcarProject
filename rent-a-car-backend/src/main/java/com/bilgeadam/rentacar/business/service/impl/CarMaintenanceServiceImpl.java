package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.entity.CarMaintenance;
import com.bilgeadam.rentacar.config.mapper.CarMaintenanceMapper;
import com.bilgeadam.rentacar.business.response.CarMaintenanceResponse;
import com.bilgeadam.rentacar.business.response.RentalCarResponse;
import com.bilgeadam.rentacar.repository.CarMaintenanceRepository;
import com.bilgeadam.rentacar.business.request.CreateCarMaintenanceRequest;
import com.bilgeadam.rentacar.business.request.UpdateCarMaintenanceRequest;
import com.bilgeadam.rentacar.business.service.CarMaintenanceService;
import com.bilgeadam.rentacar.business.service.CarService;
import com.bilgeadam.rentacar.business.service.RentalCarService;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CarMaintenanceServiceImpl implements CarMaintenanceService {

  private final CarMaintenanceRepository carMaintenanceRepository;
  private final RentalCarService rentalCarService;
  private final CarService carService;
  private final CarMaintenanceMapper carMaintenanceMapper;

  public CarMaintenanceServiceImpl(
      CarMaintenanceRepository carMaintenanceRepository,
      @Lazy RentalCarService rentalCarService,
      CarService carService,
      final CarMaintenanceMapper carMaintenanceMapper) {
    this.carMaintenanceRepository = carMaintenanceRepository;
    this.rentalCarService = rentalCarService;
    this.carService = carService;
    this.carMaintenanceMapper = carMaintenanceMapper;
  }

  @Override
  public DataResult<List<CarMaintenanceResponse>> getAll() {
    final List<CarMaintenance> carMaintenances = carMaintenanceRepository.findAll();
    final List<CarMaintenanceResponse> carMaintenanceResponses =
        carMaintenanceMapper.entityListToResponseList(carMaintenances);
    return new SuccessDataResult<>(
        carMaintenanceResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
    checkCarExistence(createCarMaintenanceRequest.getCarId());
    checkIfCarIsRented(createCarMaintenanceRequest);
    checkIfCarUnderMaintenance(createCarMaintenanceRequest);
    final CarMaintenance carMaintenance =
        carMaintenanceMapper.requestToEntity(createCarMaintenanceRequest);
    this.carMaintenanceRepository.save(carMaintenance);
    return new SuccessDataResult<>(
        createCarMaintenanceRequest, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<CarMaintenanceResponse> getById(String id) {
    final CarMaintenance carMaintenance = getCarMaintenanceByUsingId(id);
    final CarMaintenanceResponse carMaintenanceResponse =
        carMaintenanceMapper.entityToResponse(carMaintenance);
    return new SuccessDataResult<>(
        carMaintenanceResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result update(String id, UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
    final CarMaintenance carMaintenance = getCarMaintenanceByUsingId(id);
    checkReturnDateIsBeforeLocalDateNow(updateCarMaintenanceRequest.getReturnDate());
    checkUpdateParametersNotEquality(carMaintenance, updateCarMaintenanceRequest);
    checkIfMaintenanceUpdateDateAndRentDateValid(carMaintenance, updateCarMaintenanceRequest);
    updateCarMaintenanceOperations(carMaintenance, updateCarMaintenanceRequest);
    final CarMaintenanceResponse carMaintenanceResponse =
        carMaintenanceMapper.entityToResponse(carMaintenance);
    this.carMaintenanceRepository.save(carMaintenance);
    return new SuccessDataResult(
        carMaintenanceResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id) {
    final CarMaintenance carMaintenance = getCarMaintenanceByUsingId(id);
    final CarMaintenanceResponse carMaintenanceResponse =
        carMaintenanceMapper.entityToResponse(carMaintenance);
    carMaintenance.setState((short) 0);
    this.carMaintenanceRepository.save(carMaintenance);
    return new SuccessDataResult(
        carMaintenanceResponse,
        BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public List<CarMaintenanceResponse> getAllCarMaintenancesByCarId(String carId) {
    List<CarMaintenance> carMaintenances = this.carMaintenanceRepository.getByCar_Id(carId);
    final List<CarMaintenanceResponse> carMaintenanceResponses =
        carMaintenanceMapper.entityListToResponseList(carMaintenances);
    return carMaintenanceResponses;
  }

  private void updateCarMaintenanceOperations(
      CarMaintenance carMaintenance, UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
    carMaintenance.setDescription(updateCarMaintenanceRequest.getDescription());
    carMaintenance.setReturnDate(updateCarMaintenanceRequest.getReturnDate());
  }

  private void checkCarExistence(@NotNull @Min(1) String carId) {
    carService.getCarById(carId);
  }

  private void checkIfCarIsRented(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
    List<RentalCarResponse> rentalCarResponses =
        this.rentalCarService
            .getAllRentalCarsByCarId(createCarMaintenanceRequest.getCarId())
            .getData();

    if (rentalCarResponses != null) {
      if (rentalCarResponses.isEmpty()) {
        throw new BusinessException(
            BusinessMessages.CarMaintenanceMessages.CAR_RENT_NOT_FOUND
                + createCarMaintenanceRequest.getCarId());
      }

      for (RentalCarResponse rentalCarResponse : rentalCarResponses) {
        if (rentalCarResponse.getReturnDate() == null) {
          throw new BusinessException(
              BusinessMessages.CarMaintenanceMessages.CAR_IS_UNDER_MAINTENANCE_UNKNOWN_RETURN_DATE);
        }

        if ((createCarMaintenanceRequest.getReturnDate().isEqual(rentalCarResponse.getRentDate())
                || createCarMaintenanceRequest
                    .getReturnDate()
                    .isAfter(rentalCarResponse.getRentDate()))
            && (createCarMaintenanceRequest
                    .getReturnDate()
                    .isEqual(rentalCarResponse.getReturnDate())
                || createCarMaintenanceRequest
                    .getReturnDate()
                    .isBefore(rentalCarResponse.getReturnDate()))) {
          throw new BusinessException(
              BusinessMessages.CarMaintenanceMessages.CAR_IS_RENTED
                  + rentalCarResponse.getRentDate()
                  + BusinessMessages.TO
                  + rentalCarResponse.getReturnDate());
        }
      }
    }
  }

  private CarMaintenance getCarMaintenanceByUsingId(String id) {
    return carMaintenanceRepository
        .findById(id)
        .orElseThrow(
            () ->
                new BusinessException(
                    BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_NOT_FOUND + id));
  }

  private void checkUpdateParametersNotEquality(
      CarMaintenance carMaintenance, UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
    if (carMaintenance.getDescription().equals(updateCarMaintenanceRequest.getDescription())
        && carMaintenance.getReturnDate().isEqual(updateCarMaintenanceRequest.getReturnDate())) {
      throw new BusinessException(
          BusinessMessages.CarMaintenanceMessages.NO_CHANGES_NO_NEED_TO_UPDATE);
    }
  }

  private void checkIfCarUnderMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
    List<CarMaintenance> carMaintenances =
        this.carMaintenanceRepository.findAllByCar_Id(createCarMaintenanceRequest.getCarId());
    if (!carMaintenances.isEmpty()) {
      for (CarMaintenance carMaintenance : carMaintenances) {
        if (carMaintenance.getReturnDate() == null) {
          throw new BusinessException(
              BusinessMessages.CarMaintenanceMessages.CAR_IS_UNDER_MAINTENANCE_UNKNOWN_RETURN_DATE);
        }
      }
      List<CarMaintenance> sortedCarMaintenances =
          carMaintenances.stream()
              .sorted(Comparator.comparing(CarMaintenance::getReturnDate).reversed())
              .toList();
      if (LocalDate.now().isBefore(sortedCarMaintenances.get(0).getReturnDate())) {
        throw new BusinessException(
            BusinessMessages.CarMaintenanceMessages.CAR_UNDER_MAINTENANCE_UNTIL
                + sortedCarMaintenances.get(0).getReturnDate());
      }
    }
  }

  private void checkReturnDateIsBeforeLocalDateNow(final LocalDate returnDate) {
    if (returnDate.isBefore(LocalDate.now())) {
      throw new BusinessException(
          BusinessMessages.CarMaintenanceMessages.RETURN_DATE_CANNOT_BEFORE_CURRENT_DAY);
    }
  }

  private void checkIfMaintenanceUpdateDateAndRentDateValid(
      CarMaintenance carMaintenance, UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
    List<RentalCarResponse> rentalCarResponses =
        this.rentalCarService.getAllRentalCarsByCarId(carMaintenance.getCar().getId()).getData();
    if (rentalCarResponses != null) {
      for (RentalCarResponse rentalCarResponse : rentalCarResponses) {
        if (rentalCarResponse.getReturnDate() == null) {
          throw new BusinessException(
              BusinessMessages.CarMaintenanceMessages.CAR_RENTED_RETURN_UNKNOWN);
        }
        if (updateCarMaintenanceRequest.getReturnDate().isAfter(rentalCarResponse.getRentDate())
            && updateCarMaintenanceRequest
                .getReturnDate()
                .isBefore(rentalCarResponse.getReturnDate())) {
          throw new BusinessException(
              BusinessMessages.CarMaintenanceMessages.CAR_IS_UNDER_MAINTENANCE_RETURN_DATE_KNOWN
                  + rentalCarResponse.getRentDate()
                  + BusinessMessages.TO
                  + rentalCarResponse.getReturnDate());
        }
      }
    }
  }
}
