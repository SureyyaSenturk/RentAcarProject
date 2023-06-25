package com.bilgeadam.rentacar.business.service;

import com.bilgeadam.rentacar.business.request.CreatePaymentRequest;
import com.bilgeadam.rentacar.util.results.Result;

public interface PosService {

    Result makePayment(double paymentAmount, CreatePaymentRequest createPaymentRequest);
}
