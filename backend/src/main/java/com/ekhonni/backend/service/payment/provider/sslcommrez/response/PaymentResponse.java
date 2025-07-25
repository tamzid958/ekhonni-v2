package com.ekhonni.backend.service.payment.provider.sslcommrez.response;

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

    /** Amount in SSL Commerz's transaction processing currency */
    private Double amount;

    @JsonProperty("store_amount")
    private Double storeAmount;

    /** SSL Commerz's transaction processing currency */
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

    /** The original currency sent in the payment request before any conversion */
    @JsonProperty("currency_type")
    private String currencyType;

    /** The original amount sent in payment request currency */
    @JsonProperty("currency_amount")
    private Double currencyAmount;

    /** Conversion rate of original currency sent in payment request to SSL Commerz's transaction processing currency */
    @JsonProperty("currency_rate")
    private Double currencyRate;

    @JsonProperty("base_fair")
    private Double baseFair;

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