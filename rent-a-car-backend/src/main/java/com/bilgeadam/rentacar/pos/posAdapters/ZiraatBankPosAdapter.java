package com.bilgeadam.rentacar.pos.posAdapters;

import com.bilgeadam.rentacar.business.outServices.ZiraatBankPosManager;
import com.bilgeadam.rentacar.payment.CreatePaymentRequest;
import com.bilgeadam.rentacar.pos.PosService;
import com.bilgeadam.rentacar.util.results.ErrorResult;
import com.bilgeadam.rentacar.util.results.Result;
import com.bilgeadam.rentacar.util.results.SuccessResult;
import org.springframework.stereotype.Service;

@Service
public class ZiraatBankPosAdapter implements PosService {

  @Override
  public Result makePayment(double paymentAmount, CreatePaymentRequest createPaymentRequest) {
    ZiraatBankPosManager ziraatBankPosManager = new ZiraatBankPosManager();
    boolean makePaymentResult =
        ziraatBankPosManager.makePayment(
            paymentAmount,
            createPaymentRequest.getCardNo(),
            createPaymentRequest.getCardHolder(),
            createPaymentRequest.getExpirationMonth(),
            createPaymentRequest.getExpirationYear(),
            createPaymentRequest.getCvv());
    if (makePaymentResult) {
      return new SuccessResult();
    }
    return new ErrorResult();
  }
}
