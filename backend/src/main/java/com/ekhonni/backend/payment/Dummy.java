package com.ekhonni.backend.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dummy {
    @JsonProperty("trx_id")
    private String trxId;
    @JsonProperty("payable_amount")
    private String payableAmount;
    private String currency;
}
