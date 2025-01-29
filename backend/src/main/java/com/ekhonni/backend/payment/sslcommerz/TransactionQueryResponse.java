package com.ekhonni.backend.payment.sslcommerz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Author: Asif Iqbal
 * Date: 1/22/25
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionQueryResponse extends PaymentResponse {

    @JsonProperty("sessionkey")
    private String sessionKey;

    @JsonProperty("APIConnect")
    private String apiConnect;

    @JsonProperty("validated_on")
    private String validatedOn;

    @JsonProperty("gw_version")
    private String gwVersion;

}
