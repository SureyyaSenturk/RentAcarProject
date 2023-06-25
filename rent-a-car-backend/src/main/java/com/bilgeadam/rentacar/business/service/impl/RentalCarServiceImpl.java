package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.business.service.*;
import com.bilgeadam.rentacar.entity.AdditionalProduct;
import com.bilgeadam.rentacar.config.mapper.AdditionalProductMapper;
import com.bilgeadam.rentacar.business.response.AdditionalProductResponse;
import com.bilgeadam.rentacar.controllers.UpdatedRentalCarAndInvoiceDto;
import com.bilgeadam.rentacar.business.response.CarMaintenanceResponse;
import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.business.request.CreateRentalCarRequest;
import com.bilgeadam.rentacar.entity.RentalCar;
import com.bilgeadam.rentacar.business.request.UpdateRentalCarRequest;
import com.bilgeadam.rentacar.business.response.InvoiceResponse;
import com.bilgeadam.rentacar.config.mapper.RentalCarMapper;
import com.bilgeadam.rentacar.repository.RentalCarRepository;
import com.bilgeadam.rentacar.business.response.RentalCarResponse;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RentalCarServiceImpl implements RentalCarService {

  private final RentalCarRepository rentalCarRepository;
  private final CarMaintenanceService carMaintenanceService;
  private final CarService carService;
  private final RentalCarMapper rentalCarMapper;
  private final CityService cityService;
  private final InvoiceService invoiceService;
  private final AdditionalProductService additionalProductService;
  private final IndividualCustomerService individualCustomerService;
  private final CorporateCustomerService corporateCustomerService;
  private final AdditionalProductMapper additionalProductMapper;
  private final CustomerService customerService;
  public RentalCarServiceImpl(
          RentalCarRepository rentalCarRepository,
          @Lazy CarMaintenanceService carMaintenanceService,
          CarService carService,
          final RentalCarMapper rentalCarMapper,
          CityService cityService,
          @Lazy InvoiceService invoiceService,
          @Lazy AdditionalProductService additionalProductService,
          IndividualCustomerService individualCustomerService,
          CorporateCustomerService corporateCustomerService,
          final AdditionalProductMapper additionalProductMapper, final CustomerService customerService) {

    this.rentalCarRepository = rentalCarRepository;
    this.carMaintenanceService = carMaintenanceService;
    this.carService = carService;
    this.rentalCarMapper = rentalCarMapper;
    this.cityService = cityService;
    this.invoiceService = invoiceService;
    this.additionalProductService = additionalProductService;
    this.individualCustomerService = individualCustomerService;
    this.corporateCustomerService = corporateCustomerService;
    this.additionalProductMapper = additionalProductMapper;
    this.customerService = customerService;
  }

  @Override
  public DataResult<List<RentalCarResponse>> getAll() {
    final List<RentalCar> rentalCars = rentalCarRepository.findAll();
    final List<RentalCarResponse> rentalCarResponses = rentalCarMapper.entityListToResponseList(rentalCars);
    return new SuccessDataResult<>(
        rentalCarResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public SuccessDataResult<RentalCar> rentForCorporateCustomers(
      CreateRentalCarRequest createRentalCarRequest) {
    final RentalCar rentalCar = rentalCarMapper.requestToEntity(createRentalCarRequest);
    carExistenceControl(createRentalCarRequest.getCarId());
    datesIntervalControlForRent(
        createRentalCarRequest.getRentDate(), createRentalCarRequest.getReturnDate());
    checkIfRentalCarCityIdsExists(
        createRentalCarRequest.getRentCityId(), createRentalCarRequest.getReturnCityId());
    checkIfCorporateCustomerExists(createRentalCarRequest.getCustomerId());
    checkIfCarInMaintenance(createRentalCarRequest);
    checkIfCarUnderRental(createRentalCarRequest);
    checkIfAdditionalServiceListIsNullOrEmpty(
        rentalCar, createRentalCarRequest.getAdditionalServiceIds());
    rentalCar.setRentStartKilometer(
        this.carService.getCarById(createRentalCarRequest.getCarId()).getKilometerInformation());
    checkIfKilometerInformationsValid(rentalCar);
    this.carService.carKilometerSetOperation(
        rentalCar.getCar().getId(), rentalCar.getReturnKilometer());
    this.rentalCarRepository.save(rentalCar);
    return new SuccessDataResult<>(
        rentalCar, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  private void datesIntervalControlForRent(final LocalDate rentDate, final LocalDate returnDate) {
    if (returnDate != null) {
      if (returnDate.isBefore(rentDate)) {
        throw new BusinessException(
            BusinessMessages.RentalCarMessages.RETURN_DATE_CANNOT_BEFORE_RENT_DAY);
      }
    }
  }

  @Override
  public SuccessDataResult<RentalCar> rentForIndividualCustomers(
      CreateRentalCarRequest createRentalCarRequest) {
    final RentalCar rentalCar = rentalCarMapper.requestToEntity(createRentalCarRequest);
    carExistenceControl(createRentalCarRequest.getCarId());
    datesIntervalControlForRent(
        createRentalCarRequest.getRentDate(), createRentalCarRequest.getReturnDate());
    checkIfRentalCarCityIdsExists(
        createRentalCarRequest.getRentCityId(), createRentalCarRequest.getReturnCityId());
    checkIfIndividualCustomerExists(createRentalCarRequest.getCustomerId());
    checkIfCarInMaintenance(createRentalCarRequest);
    checkIfCarUnderRental(createRentalCarRequest);
    checkIfAdditionalServiceListIsNullOrEmpty(
        rentalCar, createRentalCarRequest.getAdditionalServiceIds());
    rentalCar.setRentStartKilometer(
        this.carService.getCarById(createRentalCarRequest.getCarId()).getKilometerInformation());
    checkIfKilometerInformationsValid(rentalCar);
    this.carService.carKilometerSetOperation(
        rentalCar.getCar().getId(), rentalCar.getReturnKilometer());
    this.rentalCarRepository.save(rentalCar);
    return new SuccessDataResult<>(
        rentalCar, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<List<AdditionalProductResponse>> getOrderedAdditionalServicesByRentalCarId(
      String rentalCarId) {
    final RentalCar rentalCar = getRentalCarByUsingId(rentalCarId);
    List<AdditionalProduct> additionalServices = rentalCar.getAdditionalProducts();
    final List<AdditionalProductResponse> additionalProductResponses =
        additionalProductMapper.entityListToResponseList(additionalServices);
    return new SuccessDataResult<>(
        additionalProductResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public DataResult<RentalCarResponse> getById(String id) {
    final RentalCar rentalCar = getRentalCarByUsingId(id);
    final RentalCarResponse rentalCarResponse = rentalCarMapper.entityToResponse(rentalCar);
    return new SuccessDataResult<>(
        rentalCarResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
  public Result update(String id, UpdateRentalCarRequest updateRentalCarRequest) {
    final RentalCar rentalCar = getRentalCarByUsingId(id);
    LocalDate rentDate = rentalCar.getRentDate();
    carExistenceControl(rentalCar.getCar().getId());
    checkIfUpdateParametersNotEqual(rentalCar, updateRentalCarRequest);
    checkIfRentalUpdateDatesCorrect(updateRentalCarRequest);
    checkIfRentDateAndMaintenanceUpdateDateValid(rentalCar, updateRentalCarRequest);
    checkIfUpdateCitiesAreCorrect(updateRentalCarRequest);
    rentalCarUpdateOperations(rentalCar, updateRentalCarRequest);
    this.carService.carKilometerSetOperation(
        updateRentalCarRequest.getCarId(), updateRentalCarRequest.getReturnKilometer());
    final RentalCarResponse rentalCarResponse = rentalCarMapper.entityToResponse(rentalCar);
    this.rentalCarRepository.save(rentalCar);
    rentalCarResponse.setRentDate(rentDate);
    rentalCar.setRentDate(rentDate);
    InvoiceResponse invoiceResponse =
        this.invoiceService.reGenerateInvoiceForUpdatedRentalCar(rentalCar).getData();
    UpdatedRentalCarAndInvoiceDto updatedRentalCarAndInvoiceDto =
        new UpdatedRentalCarAndInvoiceDto();
    updatedRentalCarAndInvoiceDto.setUpdatedRentalCar(rentalCarResponse);
    updatedRentalCarAndInvoiceDto.setUpdatedInvoice(invoiceResponse);
    return new SuccessDataResult<>(
        updatedRentalCarAndInvoiceDto, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  @Override
  public Result delete(String id) {
    final RentalCar rentalCar = getRentalCarByUsingId(id);
    final RentalCarResponse rentalCarResponse = rentalCarMapper.entityToResponse(rentalCar);
    rentalCar.setState((short) 0);
    this.rentalCarRepository.save(rentalCar);
    return new SuccessDataResult<>(
        rentalCarResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public DataResult<List<RentalCarResponse>> getAllRentalCarsByCarId(String carId) {
    List<RentalCar> rentalCars = this.rentalCarRepository.getByCar_Id(carId);
    final List<RentalCarResponse> rentalCarResponses =
        rentalCarMapper.entityListToResponseList(rentalCars);
    return new SuccessDataResult<>(
        rentalCarResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public List<RentalCar> getAllRentalCars() {
    return rentalCarRepository.findAll();
  }

  @Override
  public void setDelayedReturnDate(String rentalCarId, LocalDate delayedReturnDate) {
    final RentalCar rentalCar = getRentalCarByUsingId(rentalCarId);
    rentalCar.setReturnDate(delayedReturnDate);
    this.rentalCarRepository.save(rentalCar);
  }

  @Override
  public RentalCar getRentalCarById(final String id) {
    return getRentalCarByUsingId(id);
  }

  private void checkIfCarInMaintenance(CreateRentalCarRequest createRentalCarRequest) {

    final List<CarMaintenanceResponse> carMaintenanceListDtos =
        this.carMaintenanceService.getAllCarMaintenancesByCarId(createRentalCarRequest.getCarId());

    if (carMaintenanceListDtos != null) {
      if (carMaintenanceListDtos.isEmpty()) {
        throw new BusinessException(
            BusinessMessages.RentalCarMessages.CAR_MAINTENANCE_NOT_FOUND
                + createRentalCarRequest.getCarId());
      }

      for (CarMaintenanceResponse carMaintenanceListDto : carMaintenanceListDtos) {
        if (carMaintenanceListDto.getReturnDate() == null) {
          throw new BusinessException(
              BusinessMessages.RentalCarMessages.CAR_IS_UNDER_MAINTENANCE_UNKNOWN_RETURN_DATE);
        }

        if (createRentalCarRequest.getRentDate().isBefore(carMaintenanceListDto.getReturnDate())
            || createRentalCarRequest.getRentDate().isEqual(carMaintenanceListDto.getReturnDate())
            || createRentalCarRequest
                .getReturnDate()
                .isBefore(carMaintenanceListDto.getReturnDate())
            || createRentalCarRequest
                .getReturnDate()
                .isEqual(carMaintenanceListDto.getReturnDate())) {
          throw new BusinessException(
              BusinessMessages.RentalCarMessages.CAR_IS_UNDER_MAINTENANCE_UNTIL
                  + carMaintenanceListDto.getReturnDate());
        }
      }
    }
  }

  private void carExistenceControl(String carId) {
    carService.getCarById(carId);
  }

  private void checkIfUpdateParametersNotEqual(
      RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) {
    List<AdditionalProduct> additionalServices = new ArrayList<>();
    for (String id : updateRentalCarRequest.getAdditionalServiceIds()) {
      final AdditionalProduct additionalProduct =
          additionalProductService.getAdditionalServiceById(id);
      additionalServices.add(additionalProduct);
    }
    if (updateRentalCarRequest.getRentDate().isEqual(rentalCar.getRentDate())
        && updateRentalCarRequest.getReturnDate().isEqual(rentalCar.getReturnDate())
        && updateRentalCarRequest.getCarId() == rentalCar.getId()
        && updateRentalCarRequest.getCustomerId() == rentalCar.getCustomer().getId()
        && updateRentalCarRequest.getRentCityId() == rentalCar.getRentCity().getId()
        && updateRentalCarRequest.getReturnCityId() == rentalCar.getReturnCity().getId()
        && updateRentalCarRequest.getReturnKilometer() == rentalCar.getReturnKilometer()
        && additionalServices.equals(rentalCar.getAdditionalProducts())) {
      throw new BusinessException(BusinessMessages.RentalCarMessages.NO_CHANGES_NO_NEED_TO_UPDATE);
    }
  }

  private void checkIfCarUnderRental(CreateRentalCarRequest createRentalCarRequest) {
    List<RentalCar> rentalCars =
        this.rentalCarRepository.findAllByCar_Id(createRentalCarRequest.getCarId());
    if (!rentalCars.isEmpty()) {
      for (RentalCar rentalCar : rentalCars) {
        if (rentalCar.getReturnDate() == null) {
          throw new BusinessException(
              BusinessMessages.RentalCarMessages.CAR_IS_RENTED_UNKNOWN_RETURN_DATE);
        }
      }
      List<RentalCar> sortedRentalCars =
          rentalCars.stream()
              .sorted(Comparator.comparing(RentalCar::getReturnDate).reversed())
              .toList();

      if (createRentalCarRequest.getRentDate().isBefore(sortedRentalCars.get(0).getReturnDate())) {
        throw new BusinessException(
            BusinessMessages.RentalCarMessages.CAR_IS_RENTED_RETURN_DATE_KNOWN
                + sortedRentalCars.get(0).getRentDate()
                + BusinessMessages.TO
                + sortedRentalCars.get(0).getReturnDate());
      }
    }
  }

  private void checkIfRentDateAndMaintenanceUpdateDateValid(
      RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {

    List<CarMaintenanceResponse> carMaintenanceListDtos =
        this.carMaintenanceService.getAllCarMaintenancesByCarId(rentalCar.getCar().getId());

    if (carMaintenanceListDtos != null) {

      for (CarMaintenanceResponse carMaintenanceListDto : carMaintenanceListDtos) {
        if (carMaintenanceListDto.getReturnDate() == null) {
          throw new BusinessException(
              BusinessMessages.RentalCarMessages.CAR_IS_UNDER_MAINTENANCE_UNKNOWN_RETURN_DATE);
        }

        if (updateRentalCarRequest.getRentDate().isBefore(carMaintenanceListDto.getReturnDate())
            && updateRentalCarRequest
                .getReturnDate()
                .isAfter(carMaintenanceListDto.getReturnDate())) {
          throw new BusinessException(
              BusinessMessages.RentalCarMessages.CAR_UNDER_MAINTENANCE_RENT_NOT_POSSIBLE_UNTIL
                  + carMaintenanceListDto.getReturnDate());
        }
      }
    }
  }

  private void checkIfRentalUpdateDatesCorrect(UpdateRentalCarRequest updateRentalCarRequest)
      throws BusinessException {

    if (updateRentalCarRequest.getReturnDate() != null) {
      if (updateRentalCarRequest.getReturnDate().isBefore(updateRentalCarRequest.getRentDate())) {
        throw new BusinessException(
            BusinessMessages.RentalCarMessages.RETURN_DATE_CANNOT_BEFORE_RENT_DAY);
      }
    }
  }

  private void checkIfRentalCarCityIdsExists(String rentCity, String returnCity)
      throws BusinessException {

    if (!this.cityService.cityExistsById(rentCity)) {
      throw new BusinessException(BusinessMessages.RentalCarMessages.CITY_NOT_FOUND + rentCity);
    }

    if (!this.cityService.cityExistsById(returnCity)) {
      throw new BusinessException(BusinessMessages.RentalCarMessages.CITY_NOT_FOUND + returnCity);
    }
  }

  private void checkIfCorporateCustomerExists(@NotNull @Min(1) String corporateCustomerId)
      throws BusinessException {
    corporateCustomerService.getCorporateCustomerById(corporateCustomerId);
  }

  private void checkIfIndividualCustomerExists(String individualCustomerId)
      throws BusinessException {
    individualCustomerService.getIndividualCustomerById(individualCustomerId);
  }

  private void checkIfKilometerInformationsValid(RentalCar rentalCar) throws BusinessException {

    if (rentalCar.getRentStartKilometer() > rentalCar.getReturnKilometer()) {
      throw new BusinessException(
          BusinessMessages.RentalCarMessages.RETURN_KILOMETER_NOT_VALID_FOR_CAR
              + rentalCar.getCar().getId());
    }
  }

  private void checkIfAdditionalServiceListIsNullOrEmpty(
      RentalCar rentalCar, List<String> additionalServiceIds) throws BusinessException {

    if (additionalServiceIds == null || additionalServiceIds.isEmpty()) {
      rentalCar.setAdditionalProducts(null);
    } else {
      List<AdditionalProduct> additionalProducts = new ArrayList<>();
      for (String additionalServiceId : additionalServiceIds) {
        final AdditionalProduct additionalProduct = checkIfAdditionalServiceIdExists(additionalServiceId);
        additionalProducts.add(additionalProduct);
      }
      rentalCar.setAdditionalProducts(additionalProducts);
    }
  }

  private AdditionalProduct checkIfAdditionalServiceIdExists(String additionalProductId)
      throws BusinessException {
    return additionalProductService.getAdditionalServiceById(additionalProductId);
  }

  private void checkIfUpdateCitiesAreCorrect(UpdateRentalCarRequest updateRentalCarRequest)
      throws BusinessException {

    if (!this.cityService.cityExistsById(updateRentalCarRequest.getRentCityId())) {
      throw new BusinessException(
          BusinessMessages.RentalCarMessages.CITY_NOT_FOUND
              + updateRentalCarRequest.getRentCityId());
    }
    if (!this.cityService.cityExistsById(updateRentalCarRequest.getReturnCityId())) {
      throw new BusinessException(
          BusinessMessages.RentalCarMessages.CITY_NOT_FOUND
              + updateRentalCarRequest.getReturnCityId());
    }
  }

  private void rentalCarUpdateOperations(
      RentalCar rentalCar, UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {

    rentalCar.setRentDate(updateRentalCarRequest.getRentDate());
    rentalCar.setReturnDate(updateRentalCarRequest.getReturnDate());
    rentalCar.setRentCity(this.cityService.getCityById(updateRentalCarRequest.getRentCityId()));
    rentalCar.setReturnCity(this.cityService.getCityById(updateRentalCarRequest.getReturnCityId()));
    rentalCar.setRentStartKilometer(
        this.carService
            .getById(updateRentalCarRequest.getCarId())
            .getData()
            .getKilometerInformation());
    rentalCar.setReturnKilometer(updateRentalCarRequest.getReturnKilometer());
    rentalCar.setCustomer(customerService.getCustomerById(updateRentalCarRequest.getCustomerId()));

    if (rentalCar.getRentStartKilometer() > rentalCar.getReturnKilometer()) {
      throw new BusinessException(
          BusinessMessages.RentalCarMessages.RENT_AND_RETURN_KILOMETER_NOT_VALID);
    }
    checkIfAdditionalServiceListIsNullOrEmpty(
        rentalCar, updateRentalCarRequest.getAdditionalServiceIds());
  }

  private RentalCar getRentalCarByUsingId(final String rentalCarId) {
    return rentalCarRepository
        .findById(rentalCarId)
        .orElseThrow(
            () -> new BusinessException(BusinessMessages.RentalCarMessages.RENTAL_CAR_NOT_FOUND));
  }
}
