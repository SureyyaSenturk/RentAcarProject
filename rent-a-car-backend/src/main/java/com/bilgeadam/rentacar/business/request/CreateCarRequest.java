package com.bilgeadam.rentacar.business.request;

import com.bilgeadam.rentacar.common.BusinessMessages;
import com.bilgeadam.rentacar.common.request.BaseRequest;
import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest extends BaseRequest {

  @NotNull
  @Min(0)
  private double dailyPrice;

  @NotNull
  @Min(1900)
  private int modelYear;

  @NotNull
  @Pattern(
      regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,100}",
      message = BusinessMessages.CarRequestsMessages.CAR_DESCRIPTION_REGEX_MESSAGE)
  private String description;

  @NotNull
  @Min(0)
  private double kilometerInformation;

  @NotNull
  @Min(1)
  private String brandId;

  @NotNull
  @Min(1)
  private String colorId;

  private String imageUrl;

  @Nullable private List<String> carDamageIds;
}
