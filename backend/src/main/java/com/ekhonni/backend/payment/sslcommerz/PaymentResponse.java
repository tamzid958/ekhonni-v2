package com.ekhonni.backend.payment.sslcommerz;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentResponse {
    private String status;

    @JsonProperty("tran_id")
    private String tranId;

    @JsonProperty("val_id")
    private String valId;

    private String amount;

    @JsonProperty("store_amount")
    private String storeAmount;

    private String currency;

    @JsonProperty("bank_tran_id")
    private String bankTranId;

    @JsonProperty("tran_date")
    private String tranDate;

    @JsonProperty("card_type")
    private String cardType;

    @JsonProperty("card_no")
    private String cardNo;

    @JsonProperty("card_issuer")
    private String cardIssuer;

    @JsonProperty("card_brand")
    private String cardBrand;

    @JsonProperty("card_issuer_country")
    private String cardIssuerCountry;

    @JsonProperty("card_issuer_country_code")
    private String cardIssuerCountryCode;

    @JsonProperty("currency_type")
    private String currencyType;

    @JsonProperty("currency_amount")
    private String currencyAmount;

    @JsonProperty("currency_rate")
    private String currencyRate;

    @JsonProperty("base_fair")
    private String baseFair;

    @JsonProperty("value_a")
    private String valueA;

    @JsonProperty("value_b")
    private String valueB;

    @JsonProperty("value_c")
    private String valueC;

    @JsonProperty("value_d")
    private String valueD;

    @JsonProperty("risk_level")
    private String riskLevel;

    @JsonProperty("risk_title")
    private String riskTitle;
}