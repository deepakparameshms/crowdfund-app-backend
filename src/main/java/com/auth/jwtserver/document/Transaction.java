package com.auth.jwtserver.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.auth.jwtserver.document.enums.TransactionType;
import com.auth.jwtserver.dto.PaymentInfoDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "transaction")
@Getter
@Setter
public class Transaction {
    
    @Id
    @NotBlank
    private String id;
    
    private boolean paid;
    @NotBlank
    private double amount;

    @DBRef
    private User user;

    @DBRef
    private Project project;
    @NotBlank
    private TransactionType transactionType;

    private PaymentInfoDto paymentInfo;

    @NotBlank
    private Date date;
}