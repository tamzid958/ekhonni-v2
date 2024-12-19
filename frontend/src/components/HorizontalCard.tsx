import { Separator } from '@/components/ui/separator';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardTitle,
} from '@/components/ui/card';
import { Checkbox } from '@/components/ui/checkbox';
import Image from 'next/image';
import { Button } from '@/components/ui/button';
import * as React from 'react';

export const HorizontalCard = () => {
  // Mock data for products
  const watchlistItems = [
    {
      id: 1,
      title: 'JUDE BELLINGHAM 2023-24 Topps Inception',
      img: 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/916e938d-cf8b-4120-8e68-10187499d3da/AIR+JORDAN+12+RETRO.png',
      price: 112.5,
      shipping: 27.59,
      timeLeft: '1d 17h left',
      condition: 'Ungraded',
      bidsAmount: 12,
      sellerProfile: 'Jerin Priya',
    },
  ];

  return (
    <div>
      <h1 className="text-2xl font-bold mb-4">Watchlist</h1>
      <Separator className="mb-6" />
      {/* Product Watchlist */}
      <div className="space-y-6">
        {watchlistItems.map((item) => (
          <Card key={item.id} className="flex items-center gap-4">
            {/* Checkbox */}
            <Checkbox className="ml-4" />

            {/* Product Image */}
            <CardContent className="w-24 h-24 relative">
              <Image
                src={item.img}
                alt={item.title}
                layout="fill"
                className="rounded-md object-cover"
              />
            </CardContent>

            {/* Product Details */}
            <CardContent className="p-3 flex-1 flex flex-col space-y-2">
              {/* Title */}
              <CardContent className="p-6 text-gray-600">
                <CardTitle>{item.title}</CardTitle>
                <CardDescription className="pt-2">
                  Condition: {item.condition}
                </CardDescription>
              </CardContent>
              {/* Details */}
              <ul className="flex flex-wrap justify-evenly text-sm gap-4 sm:justify-start">
                <li>
                  <div className="pr-2">
                    <p className="text-gray-600">Bids: {item.bidsAmount}</p>
                    <CardTitle className="py-0.5">US ${item.price}</CardTitle>
                  </div>
                </li>
                <li>
                  <Separator
                    orientation="vertical"
                    className="h-full bg-gray-300"
                  />
                </li>
                <li>
                  <div className="px-4">
                    <p className="text-gray-600">TIME ENDS:</p>
                    <p className="text-lg font-bold text-red-500">
                      {item.timeLeft}
                    </p>
                  </div>
                </li>
                <li>
                  <Separator
                    orientation="vertical"
                    className="h-full bg-gray-300"
                  />
                </li>
                <li>
                  <div className="px-4">
                    <p> Seller:</p>
                    <p className="text-lg font-bold text-gray-800 py-1">
                      {item.sellerProfile}
                    </p>
                  </div>
                </li>
              </ul>
            </CardContent>
            <CardFooter>
              <div className="flex flex-col mt-10 space-y-2">
                <Button>Bid now</Button>
                <Button variant="secondary">
                  View seller&#39;s other items
                </Button>
                <Button variant="link">More Action </Button>
              </div>
            </CardFooter>
          </Card>
        ))}
      </div>
    </div>
  );
};
