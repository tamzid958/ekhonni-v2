import React from 'react';
import {QuickBid} from "@/components/QuickBid";
import {Ads} from "@/components/Ads";
import {Category} from "@/components/Category";


export default function Home() {
    return (
        <div className="flex flex-col min-h-screen poppins.classname bg-white">
            <Ads/>
            <QuickBid/>
            <Category/>
        </div>
    );
}
