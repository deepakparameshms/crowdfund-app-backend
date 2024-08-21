package com.auth.jwtserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.utility.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    

    @Operation(summary ="Records a Payment transaction")
    @PostMapping("/payment")
    public ResponseEntity<Object> recordPayment(){
        
        return ResponseBuilder.build(null, null, "Payment Recorded", getClass());
    }
}
