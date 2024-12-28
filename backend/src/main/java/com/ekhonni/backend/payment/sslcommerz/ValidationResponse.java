package com.ekhonni.backend.payment.sslcommerz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Author: Asif Iqbal
 * Date: 12/26/24
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationResponse extends PaymentResponse {
    @JsonProperty("emi_instalment")
    private String emiInstalment;

    @JsonProperty("emi_amount")
    private String emiAmount;

    @JsonProperty("emi_description")
    private String emiDescription;

    @JsonProperty("emi_issuer")
    private String emiIssuer;

    @JsonProperty("account_details")
    private String accountDetails;

    @JsonProperty("APIConnect")
    private String apiConnect;

    @JsonProperty("validated_on")
    private String validatedOn;

    @JsonProperty("gw_version")
    private String gwVersion;
}
