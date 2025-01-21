package com.ekhonni.backend.payment.sslcommerz.refund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 1/21/25
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundQueryResponse {

    @JsonProperty("APIConnect")
    private String APIConnect;

    @JsonProperty("bank_tran_id")
    private String bankTranId;

    @JsonProperty("trans_id")
    private String transId;

    @JsonProperty("refund_ref_id")
    private String refundRefId;

    @JsonProperty("initiated_on")
    private String initiatedOn;

    @JsonProperty("refunded_on")
    private String refundedOn;

    @JsonProperty("status")
    private String status;

    @JsonProperty("errorReason")
    private String errorReason;

}
