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
    public ResponseEntity<?> success(@RequestParam Map<String, String> params) {
//        System.out.println("Received Parameters:");
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            System.out.println(entry.getKey() + " = " + entry.getValue());
//        }
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


    @PostMapping("/ipn")
    public ResponseEntity<String> handleIPN(@RequestParam Map<String, String> params) {

        System.out.println("IPN Received Parameters:");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }


        String status = params.get("status");
        if ("VALID".equalsIgnoreCase(status)) {

            String tranId = params.get("tran_id");
            String amount = params.get("amount");

            System.out.println("Transaction ID: " + tranId + ", Amount: " + amount);

        } else {
            System.out.println("Transaction Failed or Invalid");
        }

        return ResponseEntity.ok("IPN Processed Successfully");
    }
}
