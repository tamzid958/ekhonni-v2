package com.ekhonni.backend.payment.sslcommerz.refund;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Author: Asif Iqbal
 * Date: 1/20/25
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundResponse {

    @JsonProperty("APIConnect")
    private String apiConnect;

    @JsonProperty("bank_tran_id")
    private String bankTranId;

    @JsonProperty("trans_id")
    private String transId;

    @JsonProperty("refund_ref_id")
    private String refundRefId;

    @JsonProperty("status")
    private String status;

    @JsonProperty("errorReason")
    private String errorReason;

}
