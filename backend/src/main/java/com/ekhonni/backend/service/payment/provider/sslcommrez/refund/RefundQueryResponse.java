package com.ekhonni.backend.service.payment.provider.sslcommrez.refund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Author: Asif Iqbal
 * Date: 1/21/25
 */

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundQueryResponse extends RefundResponse {

    @JsonProperty("initiated_on")
    private String initiatedOn;

    @JsonProperty("refunded_on")
    private String refundedOn;

}


