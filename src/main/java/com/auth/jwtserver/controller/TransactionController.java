package com.auth.jwtserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.document.User;
import com.auth.jwtserver.dto.TransactionDto;
import com.auth.jwtserver.dto.TransactionResponseDto;
import com.auth.jwtserver.service.TransactionService;
import com.auth.jwtserver.utility.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @Operation(summary ="Records a Payment transaction")
    @PostMapping("/payment")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> recordPayment(@AuthenticationPrincipal User user, @RequestBody TransactionDto transactionDto){
        TransactionResponseDto responseDto = transactionService.recordPayment(transactionDto);
        return ResponseBuilder.build(HttpStatus.CREATED, null, "Payment Successful" , responseDto);
    }

    @Operation(summary ="Gets all transaction of a project")
    @GetMapping("/project/{projectId}")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> getProjectTransactions(@AuthenticationPrincipal User user, @PathVariable String projectId){
        List<TransactionResponseDto> transactions = transactionService.getProjectTransaction(projectId);
        return ResponseBuilder.build(HttpStatus.OK, null, "Project's Transactions", transactions);
    }

    @Operation(summary ="Gets all transaction of an user")
    @GetMapping("/user/{userId}")
    @SecurityRequirement(name = "bearerAuthToken")
    public ResponseEntity<Object> getUserTransactions(@AuthenticationPrincipal User user, @PathVariable String userId){
        List<TransactionResponseDto> transactions = transactionService.getUserTransaction(userId);
        return ResponseBuilder.build(HttpStatus.OK, null, "User's Transactions", transactions);
    }
}
