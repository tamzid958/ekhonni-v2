package com.ekhonni.backend.factory.payout;

import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.service.payout.PayoutProvider;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
public interface PayoutProviderFactory {
    PayoutCategory getPayoutCategory();
    PayoutMethod getPayoutMethod();
    PayoutProvider createPayoutProvider();
}
