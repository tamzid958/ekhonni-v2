package com.ekhonni.backend.service;

import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.payment.sslcommerz.SSLCommerzInitResponse;
import com.ekhonni.backend.payment.sslcommerz.SSLCommerzValidatorResponse;
import com.ekhonni.backend.payment.sslcommerz.Util;
import com.ekhonni.backend.projection.GatewayResponseProjection;
import com.ekhonni.backend.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: Asif Iqbal
 * Date: 12/15/24
 */
@Service
@AllArgsConstructor
@Slf4j
public class PaymentService {

    TransactionService transactionService;
    private final ProjectionFactory projectionFactory;
    private final Util util;
    private final String sslcommerzApiUrl;

    public ApiResponse<?> initiatePayment(Long bidLogId) {
        Transaction transaction = transactionService.create(bidLogId);
        String requestBody;
        try {
            requestBody = util.getParamsString(transaction,true);
        } catch (UnsupportedEncodingException e) {
            transactionService.deletePermanently(transaction.getId());
            return new ApiResponse<>(false, "Error", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        SSLCommerzInitResponse response = restTemplate.postForEntity(sslcommerzApiUrl, requestEntity, SSLCommerzInitResponse.class).getBody();
        log.info("Response: {}", response);

        if (response != null && "SUCCESS".equals(response.getStatus())) {
            transaction.setSessionkey(response.getSessionkey());
            GatewayResponseProjection responseProjection = projectionFactory.createProjection(GatewayResponseProjection.class, response);
            return new ApiResponse<>(true, "Success", responseProjection, HttpStatus.OK);
        } else {
            transactionService.deletePermanently(transaction.getId());
            String message = "Unknown error";
            if (response != null) {
                message = response.getFailedReason();
            }
            return new ApiResponse<>(false, message, null, HttpStatus.BAD_REQUEST);
        }
    }

    public boolean verifyTransactionParameters(Map<String, String> validatorResponse) {
        SSLCommerzValidatorResponse response = projectionFactory.createProjection(SSLCommerzValidatorResponse.class, validatorResponse);
        long transactionId;
        double currencyRate;
        double responseAmount;
        try {
            transactionId = Long.parseLong(response.getTran_id());
            currencyRate = Double.parseDouble(response.getCurrency_rate());
            responseAmount = Double.parseDouble(response.getAmount());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Failed to parse transaction data");
        }

        Transaction transaction = transactionService.get(transactionId);
        double expectedBdtAmount = transaction.getAmount() * currencyRate;
        double marginOfError = 0.01;

        return transaction.getId().equals(transactionId)
                & response.getCurrency().equals(transaction.getCurrency())
                & response.getCurrency_amount().equals(String.valueOf(transaction.getAmount()))
                & (Math.abs(expectedBdtAmount - responseAmount) <= marginOfError);

    }

    public Boolean ipnHashVerify(final Map<String, String> requestParameters) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Check for the presence of verify_sign and verify_key
        if (requestParameters.containsKey("verify_sign") && requestParameters.containsKey("verify_key")) {
            String verifySign = requestParameters.get("verify_sign");
            String verifyKey = requestParameters.get("verify_key");

            if (verifySign != null && !verifySign.isEmpty() && verifyKey != null && !verifyKey.isEmpty()) {
                // Split the verify_key into an array
                String[] keyList = verifyKey.split(",");
                TreeMap<String, String> sortedMap = new TreeMap<>();

                // Populate the sorted map with the specified keys and their values
                for (String key : keyList) {
                    if (requestParameters.containsKey(key)) {
                        sortedMap.put(key, requestParameters.get(key));
                    }
                }

                // Hash the store password and add it to the map
                String hashedPass = md5(this.storePass);
                sortedMap.put("store_passwd", hashedPass);

                // Concatenate the parameters into a single string
                String hashString = ParameterBuilder.getParamsString(sortedMap, false);

                // Generate the hash of the concatenated string
                String generatedHash = md5(hashString);

                // Compare the generated hash with the verify_sign
                return generatedHash.equals(verifySign);
            }
        }
        return false;
    }

    // Method to generate an MD5 hash
    private String md5(String s) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] bytesOfMessage = s.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] theDigest = md.digest(bytesOfMessage);
        StringBuilder sb = new StringBuilder();
        for (byte b : theDigest) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    
}
