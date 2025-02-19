'use client';

import * as React from 'react';
import { useState } from 'react';
import { Rocket } from 'lucide-react';

import { Button } from '@/components/ui/button';
import {
  Drawer,
  DrawerClose,
  DrawerContent,
  DrawerDescription,
  DrawerHeader,
  DrawerTitle,
  DrawerTrigger,
} from '@/components/ui/drawer';
import { useRouter } from 'next/navigation';
import { Card, CardContent } from '@/components/ui/card';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Loading from '@/components/Loading';
import { z } from 'zod';

const packagePrices = {
  general: 100,
  premium: 350,
};
const balanceSchema = z.object({
  balance: z.number().min(0, 'Balance cannot be negative'),
});


export function DrawerDemo({ id }: { id: string }) {
  const [selectedPackage, setSelectedPackage] = useState(null);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const router = useRouter();
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;


  const { data: userBalance, error, isLoading } = useSWR(
    userId ? `/api/v2/account/user/balance` : null,
    (url) => fetcher(url, userToken),
  );

  if (status === 'loading' || isLoading) {
    return (<div className="flex justify-center items-center h-screen"><Loading /></div>);
  }
  const balance = userBalance?.data ?? 0;

  const handleSubmit = async () => {
    if (!selectedPackage) return;
    const selectedCost = packagePrices[selectedPackage];

    console.log('User ID:', userId);
    console.log('User Token:', userToken);
    console.log('Product ID:', id);
    console.log('Selected Package:', selectedPackage);

    const validationResult = balanceSchema.safeParse({ balance: balance - selectedCost });
    if (!validationResult.success) {
      setErrorMessage('Insufficient balance to purchase this package.');
      return;
    }
    setErrorMessage(null);
    const requestBody = {
      productId: id,
      boostType: selectedPackage === 'general' ? 'ONE_WEEK' : 'ONE_MONTH',
    };
    try {
      console.log('Making API request...');
      const response = await fetch('http://localhost:8080/api/v2/product/boost', {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${userToken}`,
          'Content-type': 'application/json',
        },
        body: JSON.stringify(requestBody),
      });
      if (!response.ok) {
        throw new Error('Failed to boost the product.');
      }
    } catch (error) {
      setErrorMessage(error.message);
      console.log(error);
      console.error('Error boosting product:', error);
      setErrorMessage(error.message || 'Something went wrong.');
    }
  };


  return (
    <Drawer>
      <DrawerTrigger asChild className="size-1">
        <Button variant="destructive" className="top-12 right-12"><Rocket /></Button>
      </DrawerTrigger>
      <DrawerContent>
        <div className="mx-auto w-full">
          <DrawerHeader className="p-4">
            <DrawerTitle className="flex justify-center items-center">YOUR CURRENT BALANCE : ৳{balance}</DrawerTitle>
            <DrawerTitle className="flex justify-center items-center">product id : {id}</DrawerTitle>
            <br />
            <DrawerTitle className="flex justify-center items-center">Select Your Boost Package</DrawerTitle>
            <DrawerDescription className="flex justify-center items-center">Choose the best package for your
              needs.</DrawerDescription>
          </DrawerHeader>
          <div className="flex flex-row">
            <div className="w-1/4"></div>
            <div className="p-4 pb-0 space-y-4 w-1/2">
              <div
                className={`p-4 border rounded-xl transition cursor-pointer${
                  selectedPackage === 'general' ? 'border-blue-500' : 'border-gray-300'
                } hover:shadow-lg`}
                onClick={() => setSelectedPackage('general')}
              >
                <Card>
                  <CardContent className="p-4 bg-brand-bright">
                    <h3 className="text-lg font-semibold flex justify-center items-center">General Boost</h3>
                    <p className="text-sm text-gray-600 flex justify-center items-center">Boost visibility
                      moderately.</p>
                    <p className="text-sm text-gray-600 flex justify-center items-center">Duration : 1 Week</p>
                    <p className="text-sm text-gray-600 flex justify-center items-center">Cost : 100 BDT</p>
                  </CardContent>
                </Card>
              </div>

              <div
                className={`p-4 border rounded-xl transition cursor-pointer ${
                  selectedPackage === 'premium' ? 'border-blue-500' : 'border-gray-300'
                } hover:shadow-lg`}
                onClick={() => setSelectedPackage('premium')}
              >
                <Card>
                  <CardContent className="p-4 bg-brand-bright">
                    <h3 className="text-lg font-semibold flex justify-center items-center">Premium Boost</h3>
                    <p className="text-sm text-gray-600 flex justify-center items-center">Get maximum visibility.</p>
                    <p className="text-sm text-gray-600 flex justify-center items-center">Duration : 1 Month</p>
                    <p className="text-sm text-gray-600 flex justify-center items-center">Cost : 350 BDT</p>
                  </CardContent>
                </Card>
              </div>
            </div>
            <div className="w-1/4"></div>
          </div>

          {errorMessage && (
            <p className="text-red-500 text-center mt-2">{errorMessage}</p>
          )}

          <div className="w-full pb-12 pt-6 flex items-center justify-center">
            <Button className="w-1/4 m-2" onClick={handleSubmit} disabled={!selectedPackage}>
              Submit
            </Button>
            <DrawerClose asChild>
              <Button className="w-1/4 m-2" variant="outline">Cancel</Button>
            </DrawerClose>
          </div>
        </div>
      </DrawerContent>
    </Drawer>
  );
}
