package com.ekhonni.backend.payment.sslcommerz;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SSLCommerzInitResponse {
    public String status;
    public String failedreason;
    public String sessionkey;
    public Gateway gw;
    public String redirectGatewayURL;
    public String redirectGatewayURLFailed;
    public String GatewayPageURL;
    public String storeBanner;
    public String storeLogo;
    public List<GatewayDescription> desc;
    public String is_direct_pay_enable;
}
