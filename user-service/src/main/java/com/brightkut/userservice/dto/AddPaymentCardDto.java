package com.brightkut.userservice.dto;

import com.brightkut.userservice.enums.CardTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddPaymentCardDto {
    @NotBlank
    @Size(min = 1, max = 20)
    private String cardHolderName;
    @NotBlank
    @Size(min = 16, max = 16)
    private String cardNumber;
    private CardTypeEnum cardType;
}
