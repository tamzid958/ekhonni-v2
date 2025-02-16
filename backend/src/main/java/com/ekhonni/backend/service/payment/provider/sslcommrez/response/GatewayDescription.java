package com.ekhonni.backend.service.payment.provider.sslcommrez.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GatewayDescription {
    public String name;
    public String type;
    public String logo;
    public String gw;
    private String r_flag;
    private String redirectGatewayURL;
}
