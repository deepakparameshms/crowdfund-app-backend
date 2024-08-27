package com.auth.jwtserver.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoDto {
    @NotBlank
    private String paymentId;
    @NotBlank
    private String paymentService;
    @NotBlank
    private String signature;
    @NotBlank
    private String paymentServiceMessage;
    @NotBlank
    private String paymentMode;
    @NotBlank
    private String currencyType;
}
