package com.auth.jwtserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.jwtserver.utility.ResponseBuilder;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/request")
public class RequestController {
    
    @Operation(summary = "Create a New Request to a donor")
    @PostMapping("/")
    public ResponseEntity<Object> createNewRequest(){
        
        return ResponseBuilder.build(null, null, "Project Successfully Created", getClass());
    }

    @Operation(summary = "Get User's incoming Requests")
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserRequest(){
        
        return ResponseBuilder.build(null, null, "User's Projects", getClass());
    }

    @Operation(summary = "Get User's incoming Requests")
    @GetMapping("/sent/{userId}")
    public ResponseEntity<Object> getUserRquestsSent(){
        
        return ResponseBuilder.build(null, null, "User's Projects", getClass());
    }
}
