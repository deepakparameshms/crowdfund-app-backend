package com.auth.jwtserver.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto {

    private boolean paid = false;
    @NotBlank
    private double amount;
    @NotBlank
    private String userId;
    @NotBlank
    private String projectId;
    @NotBlank
    private String transactionId;
    @NotBlank
    private String paymentId;
    @NotBlank
    private String signature;
    @NotBlank
    private String paymentService;
    @NotBlank
    private String paymentServiceMessage;
    @NotBlank
    private String paymentMode;
    @NotBlank
    private String currencyType;
}