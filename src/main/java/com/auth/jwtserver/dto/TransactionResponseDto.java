package com.auth.jwtserver.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponseDto {
    @NotBlank
    private String id;
    
    private boolean paid;
    @NotBlank
    private double amount;
    @NotBlank
    private String userId;
    @NotBlank
    private String userName;
    @NotBlank
    private String projectId;
    @NotBlank
    private String transactionId;

    private PaymentInfoDto paymentInfo;

    @NotBlank
    private Date date;
}
