package com.bilgeadam.rentacar.business.service.impl;

import com.bilgeadam.rentacar.business.service.*;
import com.bilgeadam.rentacar.entity.AdditionalProduct;
import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.business.request.CreateInvoiceRequest;
import com.bilgeadam.rentacar.business.request.CreateRentalCarRequest;
import com.bilgeadam.rentacar.entity.Invoice;
import com.bilgeadam.rentacar.entity.RentalCar;
import com.bilgeadam.rentacar.config.mapper.InvoiceMapper;
import com.bilgeadam.rentacar.config.mapper.RentalCarMapper;
import com.bilgeadam.rentacar.repository.InvoiceRepository;
import com.bilgeadam.rentacar.business.response.InvoiceResponse;
import com.bilgeadam.rentacar.business.response.RentalCarResponse;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

  private final InvoiceRepository invoiceRepository;
  private final RentalCarService rentalCarService;
  private final AdditionalProductService additionalProductService;
  private final InvoiceMapper invoiceMapper;
  private final RentalCarMapper rentalCarMapper;
  private final CityService cityService;
  private final CarService carService;
  private final CustomerService customerService;

  public InvoiceServiceImpl(
      InvoiceRepository invoiceRepository,
      RentalCarService rentalCarService,
      final AdditionalProductService additionalProductService,
      final InvoiceMapper invoiceMapper,
      final RentalCarMapper rentalCarMapper,
      final CityService cityService,
      final CarService carService,
      final CustomerService customerService) {
    this.invoiceRepository = invoiceRepository;
    this.rentalCarService = rentalCarService;
    this.additionalProductService = additionalProductService;
    this.invoiceMapper = invoiceMapper;
    this.rentalCarMapper = rentalCarMapper;
    this.cityService = cityService;
    this.carService = carService;
    this.customerService = customerService;
  }

  @Override
  public DataResult<List<InvoiceResponse>> getAll() {
    final List<Invoice> invoices = invoiceRepository.findAll();
    final List<InvoiceResponse> invoiceResponses = invoiceMapper.entityListToResponseList(invoices);
    return new SuccessDataResult<>(
        invoiceResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public SuccessDataResult add(CreateInvoiceRequest createInvoiceRequest) {
    checkIfRentalCarIdExists(createInvoiceRequest.getRentalCarId());
    final Invoice invoice = invoiceMapper.requestToEntity(createInvoiceRequest);
    final RentalCar rentalCar =
        rentalCarService.getRentalCarById(createInvoiceRequest.getRentalCarId());
    invoice.setDate(LocalDate.now());
    invoice.setNumber(invoiceNumberCreator(createInvoiceRequest.getRentalCarId()));
    invoice.setAdditionalProductTotalPayment(
        invoiceAdditionalServiceTotalPaymentCalculator(rentalCar));
    invoice.setRentDay(invoiceRentDayCalculations(rentalCar));
    invoice.setRentPayment(invoiceRentPaymentCalculations(rentalCar));
    invoice.setRentLocationPayment(invoiceRentLocationPaymentCalculations(rentalCar));
    invoice.setTotalPayment(
        invoice.getAdditionalProductTotalPayment()
            + invoice.getRentPayment()
            + invoice.getRentLocationPayment());
    invoice.setCustomer(rentalCar.getCustomer());
    this.invoiceRepository.save(invoice);

    return new SuccessDataResult<>(
        invoice, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<InvoiceResponse> getById(String id) {
    final Invoice invoice = getInvoiceByUsingId(id);
    final InvoiceResponse invoiceResponse = invoiceMapper.entityToResponse(invoice);
    return new SuccessDataResult<>(
        invoiceResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  public Result delete(String id) {
    final Invoice invoice = getInvoiceByUsingId(id);
    final InvoiceResponse invoiceResponse = invoiceMapper.entityToResponse(invoice);
    invoice.setState((short) 0);
    this.invoiceRepository.save(invoice);
    return new SuccessDataResult<>(
        invoiceResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public DataResult<List<InvoiceResponse>> getAllInvoicesByRentalCarId(String id) {
    final List<Invoice> invoices = this.invoiceRepository.findInvoicesByRentalCar_Id(id);
    final List<InvoiceResponse> invoiceResponses = invoiceMapper.entityListToResponseList(invoices);
    return new SuccessDataResult<>(
        invoiceResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  public double preInvoiceCalculator(CreateRentalCarRequest createRentalCarRequest) {
    final RentalCar rentalCar = rentalCarMapper.requestToEntity(createRentalCarRequest);
    rentalCar.setRentCity(cityService.getCityById(createRentalCarRequest.getRentCityId()));
    rentalCar.setReturnCity(cityService.getCityById(createRentalCarRequest.getReturnCityId()));
    rentalCar.setCar(carService.getCarById(createRentalCarRequest.getCarId()));
    rentalCar.setCustomer(customerService.getCustomerById(createRentalCarRequest.getCustomerId()));
    rentalCar.setAdditionalProducts(
        createRentalCarRequest.getAdditionalServiceIds().stream()
            .map(additionalProductService::getAdditionalServiceById)
            .toList());
    checkIfAdditionalServiceListIsNullOrEmpty(rentalCar, createRentalCarRequest);
    Double preAdditionalServiceTotalPrice =
        invoiceAdditionalServiceTotalPaymentCalculator(rentalCar);
    Double preRentDayPrice = invoiceRentPaymentCalculations(rentalCar);
    Double preRentLocationPaymentPrice = invoiceRentLocationPaymentCalculations(rentalCar);
    return (preAdditionalServiceTotalPrice + preRentDayPrice + preRentLocationPaymentPrice);
  }

  @Override
  public DataResult<Invoice> generateDelayedRentalCarInvoice(RentalCar rentalCar) {
    checkIfRentalCarIdExists(rentalCar.getId());
    Invoice invoice = new Invoice();
    invoice.setRentalCar(rentalCar);
    invoice.setDate(LocalDate.now());
    invoice.setNumber(invoiceNumberCreator(rentalCar.getId()));
    invoice.setAdditionalProductTotalPayment(
        invoiceAdditionalServiceTotalPaymentCalculator(rentalCar));
    invoice.setRentDay(invoiceRentDayCalculations(rentalCar));
    invoice.setRentPayment(invoiceRentPaymentCalculations(rentalCar));
    invoice.setRentLocationPayment(invoiceRentLocationPaymentCalculations(rentalCar));
    invoice.setTotalPayment(
        invoice.getAdditionalProductTotalPayment()
            + invoice.getRentPayment()
            + invoice.getRentLocationPayment());
    invoice.setCustomer(rentalCar.getCustomer());
    this.invoiceRepository.save(invoice);
    return new SuccessDataResult<>(
        invoice, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  public DataResult<InvoiceResponse> reGenerateInvoiceForUpdatedRentalCar(RentalCar rentalCar) {
    checkIfInvoiceNumberExists(rentalCar);
    Invoice invoice =
        this.invoiceRepository.findInvoiceByNumber(invoiceNumberCreator(rentalCar.getId()));
    invoice.setRentalCar(rentalCar);
    invoice.setDate(LocalDate.now());
    invoice.setNumber(invoiceNumberCreator(rentalCar.getId()));
    invoice.setAdditionalProductTotalPayment(
        invoiceAdditionalServiceTotalPaymentCalculator(rentalCar));
    invoice.setRentDay(invoiceRentDayCalculations(rentalCar));
    invoice.setRentPayment(invoiceRentPaymentCalculations(rentalCar));
    invoice.setRentLocationPayment(invoiceRentLocationPaymentCalculations(rentalCar));
    invoice.setTotalPayment(
        invoice.getAdditionalProductTotalPayment()
            + invoice.getRentPayment()
            + invoice.getRentLocationPayment());
    invoice.setCustomer(rentalCar.getCustomer());
    final InvoiceResponse invoiceResponse = invoiceMapper.entityToResponse(invoice);
    this.invoiceRepository.save(invoice);
    return new SuccessDataResult<>(
        invoiceResponse, BusinessMessages.GlobalMessages.DATA_UPDATED_TO_NEW_DATA);
  }

  private void checkIfInvoiceNumberExists(RentalCar rentalCar) {
    if (this.invoiceRepository.findInvoiceByNumber(invoiceNumberCreator(rentalCar.getId()))
        == null) {
      throw new BusinessException(
          BusinessMessages.InvoiceMessages.INVOICE_NUMBER_NOT_FOUND
              + invoiceNumberCreator(rentalCar.getId()));
    }
  }

  private void checkIfRentalCarIdExists(String rentalCarId) {
    rentalCarService.getRentalCarById(rentalCarId);
  }

  private Invoice getInvoiceByUsingId(String id) {
    return invoiceRepository
        .findById(id)
        .orElseThrow(
            () -> new BusinessException(BusinessMessages.InvoiceMessages.INVOICE_NOT_FOUND + id));
  }

  private int invoiceRentDayCalculations(RentalCar rentalCar) {
    if (ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate()) == 0) {
      return 1;
    }
    return Integer.valueOf(
        (int) ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate()));
  }

  private double invoiceRentLocationPaymentCalculations(RentalCar rentalCar) {
    double rentLocationPayment = 0;
    if (!Objects.equals(rentalCar.getRentCity().getId(), rentalCar.getReturnCity().getId())) {
      return 750.0;
    }
    return rentLocationPayment;
  }

  private double invoiceRentPaymentCalculations(RentalCar rentalCar) {
    return invoiceRentDayCalculations(rentalCar) * rentalCar.getCar().getDailyPrice();
  }

  private double invoiceAdditionalServiceTotalPaymentCalculator(RentalCar rentalCar) {
    double additionalServicesTotalPayment = 0;
    if (rentalCar.getAdditionalProducts() == null || rentalCar.getAdditionalProducts().isEmpty()) {

      return additionalServicesTotalPayment;
    } else {
      for (AdditionalProduct additionalProduct : rentalCar.getAdditionalProducts()) {
        additionalServicesTotalPayment =
            additionalServicesTotalPayment + additionalProduct.getDailyPrice();
      }
      return (additionalServicesTotalPayment * invoiceRentDayCalculations(rentalCar));
    }
  }

  private Integer invoiceNumberCreator(String rentalCarId) {

    RentalCarResponse rentalCarDto = this.rentalCarService.getById(rentalCarId).getData();

    String invoiceNumber =
        rentalCarDto.getCustomerResponse().getId()
            + rentalCarId
            + rentalCarDto.getRentDate().getYear()
            + rentalCarDto.getRentDate().getMonthValue()
            + rentalCarDto.getRentDate().getDayOfMonth();

    return Integer.valueOf(invoiceNumber);
  }

  private void checkIfAdditionalServiceListIsNullOrEmpty(
      RentalCar rentalCar, CreateRentalCarRequest createRentalCarRequest) {

    if (createRentalCarRequest.getAdditionalServiceIds() == null) {
      rentalCar.setAdditionalProducts(null);
    } else {
      List<AdditionalProduct> tempAdditionalServiceList = new ArrayList<>();

      for (String additionalServiceId : createRentalCarRequest.getAdditionalServiceIds()) {
        final AdditionalProduct additionalProduct =
            additionalProductService.getAdditionalServiceById(additionalServiceId);
        tempAdditionalServiceList.add(additionalProduct);
      }
      rentalCar.setAdditionalProducts(tempAdditionalServiceList);
    }
  }
}
