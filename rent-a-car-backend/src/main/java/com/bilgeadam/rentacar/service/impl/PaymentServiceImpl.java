package com.bilgeadam.rentacar.service.impl;

import com.bilgeadam.rentacar.api.models.CreateDelayedPaymentModel;
import com.bilgeadam.rentacar.api.models.CreatePaymentModel;
import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.common.entity.BaseEntity;
import com.bilgeadam.rentacar.entity.Payment;
import com.bilgeadam.rentacar.request.CreateInvoiceRequest;
import com.bilgeadam.rentacar.entity.Invoice;
import com.bilgeadam.rentacar.mapper.PaymentMapper;
import com.bilgeadam.rentacar.request.CreateRentalCarRequest;
import com.bilgeadam.rentacar.entity.RentalCar;
import com.bilgeadam.rentacar.repository.PaymentRepository;
import com.bilgeadam.rentacar.request.CreatePaymentRequest;
import com.bilgeadam.rentacar.response.PaymentResponse;
import com.bilgeadam.rentacar.service.*;
import com.bilgeadam.rentacar.util.businessException.BusinessException;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessDataResult;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final RentalCarService rentalCarService;
  private final InvoiceService invoiceService;
  private final PosService posService;
  private final CarService carService;
  private final PaymentMapper paymentMapper;

  public PaymentServiceImpl(
      PaymentRepository paymentRepository,
      final RentalCarService rentalCarService,
      InvoiceService invoiceService,
      PosService posService,
      CarService carService,
      final PaymentMapper paymentMapper) {
    this.paymentRepository = paymentRepository;
    this.rentalCarService = rentalCarService;
    this.paymentMapper = paymentMapper;
    this.invoiceService = invoiceService;
    this.posService = posService;
    this.carService = carService;
  }

  @Override
  public DataResult<List<PaymentResponse>> getAll() {
    final List<Payment> payments = paymentRepository.findAll();
    final List<PaymentResponse> paymentResponses = paymentMapper.entityListToResponseList(payments);
    return new SuccessDataResult<>(
        paymentResponses, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
  public Result paymentForIndividualCustomer(CreatePaymentModel createPaymentModel) {
    checkIfPaymentDone(
        preInvoiceCalculator(createPaymentModel.getCreateRentalCarRequest()),
        createPaymentModel.getCreatePaymentRequest());
    paymentSuccessorForIndividualCustomer(createPaymentModel);
    return new SuccessDataResult<>(
        createPaymentModel, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
  public void paymentSuccessorForIndividualCustomer(CreatePaymentModel createPaymentModel) {
    RentalCar rentalCar =
        this.rentalCarService
            .rentForIndividualCustomers(createPaymentModel.getCreateRentalCarRequest())
            .getData();
    CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
    createInvoiceRequest.setRentalCarId(rentalCar.getId());
    Invoice invoice = this.invoiceService.add(createInvoiceRequest).getData();
    final Payment payment =
        paymentMapper.requestToEntity(createPaymentModel.getCreatePaymentRequest());
    payment.setCustomer(rentalCar.getCustomer());
    payment.setPaymentAmount(invoice.getTotalPayment());
    payment.setRentalCar(rentalCar);
    this.paymentRepository.save(payment);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
  public Result paymentForCorporateCustomer(CreatePaymentModel createPaymentModel) {
    checkIfPaymentDone(
        preInvoiceCalculator(createPaymentModel.getCreateRentalCarRequest()),
        createPaymentModel.getCreatePaymentRequest());
    paymentSuccessorForCorporateCustomer(createPaymentModel);
    return new SuccessDataResult<>(
        createPaymentModel, BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
  public void paymentSuccessorForCorporateCustomer(CreatePaymentModel createPaymentModel) {
    RentalCar rentalCar =
        this.rentalCarService
            .rentForCorporateCustomers(createPaymentModel.getCreateRentalCarRequest())
            .getData();
    CreateInvoiceRequest createInvoiceRequest = new CreateInvoiceRequest();
    createInvoiceRequest.setRentalCarId(rentalCar.getId());
    Invoice invoice = this.invoiceService.add(createInvoiceRequest).getData();
    final Payment payment =
        paymentMapper.requestToEntity(createPaymentModel.getCreatePaymentRequest());
    payment.setCustomer(rentalCar.getCustomer());
    payment.setPaymentAmount(invoice.getTotalPayment());
    payment.setRentalCar(rentalCar);
    this.paymentRepository.save(payment);
  }

  @Override
  public Result delete(String id) {
    final Payment payment = checkIfPaymentIdExists(id);
    final PaymentResponse paymentResponse = paymentMapper.entityToResponse(payment);
    payment.setState((short) 0);
    this.paymentRepository.save(payment);
    return new SuccessDataResult<>(
        paymentResponse, BusinessMessages.GlobalMessages.DATA_STATE_CHANGED_PASSIVE_SITUATION);
  }

  @Override
  public DataResult<PaymentResponse> getById(String id) {
    final Payment payment = checkIfPaymentIdExists(id);
    final PaymentResponse paymentResponse = paymentMapper.entityToResponse(payment);
    return new SuccessDataResult<>(
        paymentResponse, BusinessMessages.GlobalMessages.DATA_BROUGHT_SUCCESSFULLY);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
  public Result additionalPaymentForDelaying(CreateDelayedPaymentModel createDelayedPaymentModel) {
    checkIfRentalCarIdExists(createDelayedPaymentModel.getRentalCarId());
    RentalCar rentalCar =
        this.rentalCarService.getRentalCarById(createDelayedPaymentModel.getRentalCarId());
    checkIfDelayedDateIsCorrect(rentalCar, createDelayedPaymentModel);
    checkIfDelayedKilometerIsCorrect(rentalCar, createDelayedPaymentModel);
    rentalCar.setRentDate(rentalCar.getReturnDate());
    rentalCar.setReturnDate(createDelayedPaymentModel.getDelayedReturnDate());
    final CreateRentalCarRequest createRentalCarRequest = getCreateRentalCarRequest(rentalCar);
    checkIfPaymentDone(
        preInvoiceCalculator(createRentalCarRequest),
        createDelayedPaymentModel.getCreatePaymentRequest());
    delayedPaymentSuccessor(createDelayedPaymentModel);

    return new SuccessDataResult(BusinessMessages.PaymentMessages.ADDITIONAL_PAYMENT_SUCCESSFULLY);
  }

  private CreateRentalCarRequest getCreateRentalCarRequest(RentalCar rentalCar) {
    return new CreateRentalCarRequest(
        rentalCar.getRentDate(),
        rentalCar.getReturnDate(),
        rentalCar.getRentCity().getId(),
        rentalCar.getReturnCity().getId(),
        rentalCar.getReturnKilometer(),
        rentalCar.getCar().getId(),
        rentalCar.getCustomer().getId(),
        rentalCar.getAdditionalProducts().stream().map(BaseEntity::getId).toList());
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, rollbackFor = BusinessException.class)
  public void delayedPaymentSuccessor(CreateDelayedPaymentModel createDelayedPaymentModel) {
    RentalCar rentalCar =
        this.rentalCarService.getRentalCarById(createDelayedPaymentModel.getRentalCarId());
    this.rentalCarService.setDelayedReturnDate(
        createDelayedPaymentModel.getRentalCarId(),
        createDelayedPaymentModel.getDelayedReturnDate());
    this.carService.carKilometerSetOperation(
        rentalCar.getCar().getId(), createDelayedPaymentModel.getCarDelayedKilometerInformation());
    Invoice invoice = this.invoiceService.generateDelayedRentalCarInvoice(rentalCar).getData();
    final Payment payment =
        paymentMapper.requestToEntity(createDelayedPaymentModel.getCreatePaymentRequest());
    payment.setCustomer(rentalCar.getCustomer());
    payment.setPaymentAmount(invoice.getTotalPayment());
    payment.setRentalCar(rentalCar);
    paymentRepository.save(payment);
  }

  private Double preInvoiceCalculator(CreateRentalCarRequest createRentalCarRequest) {
    return this.invoiceService.preInvoiceCalculator(createRentalCarRequest);
  }

  private void checkIfPaymentDone(Double paymentAmount, CreatePaymentRequest createPaymentRequest) {

    if (!posService.makePayment(paymentAmount, createPaymentRequest).isSuccess()) {
      throw new BusinessException(BusinessMessages.PaymentMessages.INVALID_PAYMENT);
    }
  }

  private Payment checkIfPaymentIdExists(String id) {
    return paymentRepository
        .findById(id)
        .orElseThrow(
            () -> new BusinessException(BusinessMessages.PaymentMessages.PAYMENT_NOT_FOUND));
  }

  private void checkIfRentalCarIdExists(String rentalCarId) {

    if (this.rentalCarService.getById(rentalCarId).getData() == null) {
      throw new BusinessException(
          BusinessMessages.PaymentMessages.RENTAL_CAR_NOT_FOUND + rentalCarId);
    }
  }

  private void checkIfDelayedKilometerIsCorrect(
      RentalCar rentalCar, CreateDelayedPaymentModel createDelayedPaymentModel) {

    if (createDelayedPaymentModel.getCarDelayedKilometerInformation()
        < rentalCar.getReturnKilometer()) {
      throw new BusinessException(
          BusinessMessages.PaymentMessages.DELAYED_RETURN_KILOMETER_IS_NOT_VALID);
    }
  }

  private void checkIfDelayedDateIsCorrect(
      RentalCar rentalCar, CreateDelayedPaymentModel createDelayedPaymentModel) {

    if (createDelayedPaymentModel.getDelayedReturnDate().isBefore(rentalCar.getReturnDate())) {
      throw new BusinessException(
          BusinessMessages.PaymentMessages.DELAYED_RETURN_DATE_IS_NOT_VALID);
    }
  }
}
