package com.postech.mspayment.controller;

import com.postech.mspayment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping("")
    @Operation(summary = "Iniciate a payment for an user", responses = {
            @ApiResponse(description = "The payment was executed", responseCode = "201")
    })
    public ResponseEntity<?> initiatePayment(@RequestBody PaymentRequest request) {
        try {
            logger.info(" * Payment request received: " + request.toString());
            paymentService.processPayment(request.getCustomerId(), request.getBasketId(), request.getAmount());
            return ResponseEntity.ok("Payment processed successfully");
        } catch (Exception e) {
            logger.error(" * Error initiating payment", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Data
    public static class PaymentRequest {
        private Long customerId;
        private Long basketId;
        private BigDecimal amount;
    }

    @GetMapping("")
    public ResponseEntity<?> getPaymentStatus() {
        return ResponseEntity.ok("okokokok");
    }
}