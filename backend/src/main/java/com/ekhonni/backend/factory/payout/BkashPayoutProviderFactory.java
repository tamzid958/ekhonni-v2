package com.ekhonni.backend.factory.payout;

import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.service.payout.provider.bkash.BkashPayoutProvider;
import com.ekhonni.backend.service.payout.PayoutProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */


@Component
@AllArgsConstructor
public class BkashPayoutProviderFactory implements PayoutProviderFactory {

    private final BkashPayoutProvider bkashPayoutProvider;

    @Override
    public PayoutCategory getPayoutCategory() {
        return PayoutCategory.MOBILE_BANKING;
    }

    @Override
    public PayoutMethod getPayoutMethod() {
        return PayoutMethod.BKASH;
    }

    @Override
    public PayoutProvider getPayoutProvider() {
        return bkashPayoutProvider;
    }

}
