package com.bilgeadam.rentacar.car;

import com.bilgeadam.rentacar.common.BusinessMessages;
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
public class UpdateCarRequest {

  @NotNull
  @Min(0)
  private double dailyPrice;

  @NotNull
  @Pattern(
      regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ 0-9]{2,100}",
      message = BusinessMessages.CarRequestsMessages.CAR_DESCRIPTION_REGEX_MESSAGE)
  private String description;

  @NotNull
  @Min(0)
  private double kilometerInformation;

  private String imageUrl;
  @Nullable private List<String> carDamageIds;
}
