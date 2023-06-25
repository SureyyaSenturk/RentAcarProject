package com.bilgeadam.rentacar.service;

import com.bilgeadam.rentacar.request.CreatePaymentRequest;
import com.bilgeadam.rentacar.util.results.Result;

public interface PosService {

    Result makePayment(double paymentAmount, CreatePaymentRequest createPaymentRequest);
}
