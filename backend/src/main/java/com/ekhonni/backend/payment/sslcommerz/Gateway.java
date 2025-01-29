package com.ekhonni.backend.payment.sslcommerz;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Gateway {
    public String visa;
    public String master;
    public String amex;
    public String othercards;
    public String internetbanking;
    public String mobilebanking;
}
