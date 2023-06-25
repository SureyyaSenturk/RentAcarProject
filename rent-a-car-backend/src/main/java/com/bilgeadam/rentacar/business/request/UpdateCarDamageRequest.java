package com.bilgeadam.rentacar.business.request;

import com.bilgeadam.rentacar.common.BusinessMessages;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDamageRequest {

    @NotNull
    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,100}", message = BusinessMessages.CarDamageRequestsMessages.CAR_DAMAGE_DESCRIPTION_REGEX_MESSAGE)
    private String damageDescription;
}
