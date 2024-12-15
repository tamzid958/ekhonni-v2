/**
 * Author: Rifat Shariar Sakil
 * Time: 1:02 AM
 * Date: 12/12/2024
 * Project Name: backend
 */
package com.ekhonni.backend.controller.test;


import com.ekhonni.backend.service.test.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/products/test")
public class PaymentTestController {

    private final PaymentService paymentService;

    public PaymentTestController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/payment-initiate")
    public ResponseEntity<?> createSession() throws JsonProcessingException {

        String gatewayPageURL = paymentService.makePayment();
        System.out.println(gatewayPageURL);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Payment session created");
        response.put("gatewayPageURL", gatewayPageURL);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/payment-success")
    public ResponseEntity<?> success(@RequestBody String response) {
        return ResponseEntity.ok("transaction was successful");
    }

    @PostMapping("/payment-fail")
    public ResponseEntity<?> fail(@RequestBody String response) {
        return ResponseEntity.ok("OOOPS! Transaction was unsuccessful");
    }

    @PostMapping("/payment-cancel")
    public ResponseEntity<?> cancel(@RequestParam Map<String, String> requestParams) {
        return ResponseEntity.ok("Back to Checkout Page");
    }
}
