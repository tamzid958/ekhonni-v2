package com.ekhonni.backend.service.payment.provider.sslcommrez.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class InitialResponse {
    public String status;
    public String sessionkey;
    public String GatewayPageURL;
    public String failedReason;
    public Gateway gw;
    public String redirectGatewayURL;
    public String redirectGatewayURLFailed;
    public String storeBanner;
    public String storeLogo;
    public List<GatewayDescription> desc;
    public String is_direct_pay_enable;
}
