package com.bilgeadam.rentacar.api.controllers;

import com.bilgeadam.rentacar.api.models.CreateDelayedPaymentModel;
import com.bilgeadam.rentacar.api.models.CreatePaymentModel;
import com.bilgeadam.rentacar.payment.PaymentResponse;
import com.bilgeadam.rentacar.payment.PaymentService;
import com.bilgeadam.rentacar.util.results.DataResult;
import com.bilgeadam.rentacar.util.results.Result;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentsController")
@CrossOrigin
public class PaymentsController {

  private final PaymentService paymentService;

  public PaymentsController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping("/getAll")
  DataResult<List<PaymentResponse>> getAll() {
    return this.paymentService.getAll();
  }

  @PostMapping("/paymentForIndividualCustomer")
  Result paymentForIndividualCustomer(@RequestBody @Valid CreatePaymentModel createPaymentModel) {
    return this.paymentService.paymentForIndividualCustomer(createPaymentModel);
  }

  @PostMapping("/paymentForCorporateCustomer")
  Result paymentForCorporateCustomer(@RequestBody @Valid CreatePaymentModel createPaymentModel) {
    return this.paymentService.paymentForCorporateCustomer(createPaymentModel);
  }

  @DeleteMapping("/delete")
  Result delete(@RequestParam String id) {
    return this.paymentService.delete(id);
  }

  @GetMapping("/getById")
  DataResult<PaymentResponse> getById(@RequestParam String id) {
    return this.paymentService.getById(id);
  }

  @PostMapping("/additionalPaymentForDelaying")
  Result additionalPaymentForDelaying(
      @RequestBody @Valid CreateDelayedPaymentModel createDelayedPaymentModel) {
    return this.paymentService.additionalPaymentForDelaying(createDelayedPaymentModel);
  }
}
