package com.bilgeadam.rentacar.payment;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.common.request.BaseRequest;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest extends BaseRequest {


    @Pattern(regexp = "^[0-9]{16}", message = BusinessMessages.PaymentRequestsMessages.CARD_NO_REGEX_MESSAGE)
    private String cardNo;

    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{5,50}", message = BusinessMessages.PaymentRequestsMessages.CARD_HOLDER_REGEX_MESSAGE)
    private String cardHolder;

    @Range(min = 1, max = 12, message = BusinessMessages.PaymentRequestsMessages.EXPIRATION_MONTH_REGEX_MESSAGE)
    private int expirationMonth;

    @Range(min = 2022, max = 2032, message = BusinessMessages.PaymentRequestsMessages.EXPIRATION_YEAR_REGEX_MESSAGE)
    private int expirationYear;

    @Range(min = 100, max = 999, message = BusinessMessages.PaymentRequestsMessages.CVV_REGEX_MESSAGE)
    private int cvv;

}
