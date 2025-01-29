package com.ekhonni.backend.payment.sslcommerz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpnResponse extends PaymentResponse {

    @JsonProperty("store_id")
    private String storeId;

    @JsonProperty("verify_sign")
    private String verifySign;

    @JsonProperty("verify_key")
    private String verifyKey;

    @JsonProperty("cus_fax")
    private String cusFax;

}