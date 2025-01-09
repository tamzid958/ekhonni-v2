import React from 'react';
import CustomErrorBoundary from '@/components/ErrorBoundary';

import {QuickBid} from '@/components/QuickBid';
import {Ads} from '@/components/Ads';
import {Category} from '@/components/Category';


export default function Home() {
  return (
    <div className="flex-col items-center justify-center min-h-screen poppins.classname bg-white">
        <CustomErrorBoundary>

            <Ads />
            <QuickBid title={"START YOUR BIDDING HERE"}/>
            <Category />

        </CustomErrorBoundary>
    </div>
  );
}
