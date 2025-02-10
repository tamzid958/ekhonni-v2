import { Separator } from '@/components/ui/separator';
import { Card, CardContent, CardDescription, CardFooter, CardTitle } from '@/components/ui/card';
import { Checkbox } from '@/components/ui/checkbox';
import Image from 'next/image';
import { Button } from '@/components/ui/button';
import * as React from 'react';


interface WatchlistItem {
  id: number;
  title: string;
  img: string;
  price: number;
  yourBid?: number;
  shipping: number;
  timeLeft: string;
  condition: string;
  bidsAmount: number;
  sellerProfile: string;
}

// Props type for the component
interface HorizontalCardProps {
  watchlistItem: WatchlistItem;
}

export const HorizontalCard: React.FC<HorizontalCardProps> = ({ watchlistItem }) => {
  const {
    title,
    img,
    price,
    yourBid,
    shipping,
    timeLeft,
    condition,
    bidsAmount,
    sellerProfile,
  } = watchlistItem;

  let showBidSection;
  let showCheckBox;
  return (
    <Card className="flex items-center gap-4 mb-4">
      <div className="ml-4">
        {showCheckBox = true && (
          <Checkbox />
        )}
      </div>

      {/* Product Image */}
      <CardContent className="w-24 h-24 relative">
        <Image
          src={img}
          alt={title}
          layout="fill"
          className="rounded-md object-cover"
        />
      </CardContent>

      {/* Product Details */}
      <CardContent className="p-3 flex-1 flex flex-col space-y-2">
        {/* Title */}
        <CardContent className="p-6 text-gray-600">
          <CardTitle>{title}</CardTitle>
          <CardDescription className="pt-2">Condition: {condition}</CardDescription>
        </CardContent>
        {/* Details */}
        <ul className="flex flex-wrap px-6 md:justify-start text-sm gap-4 sm:justify-start">
          <li>
            <div className="pr-2">
              <p className="text-gray-600">Bids: {bidsAmount}</p>
              <CardTitle className="py-0.5">US ${price}</CardTitle>
            </div>
          </li>
          <li>
            <Separator orientation="vertical" className="h-full bg-gray-300" />
          </li>

          {showBidSection = true && (
            <>
              <li>
                <div className="pr-2">
                  <p className="text-gray-600">Your Bid: {bidsAmount}</p>
                  <CardTitle className="py-0.5">US ${yourBid}</CardTitle>
                </div>
              </li>
              <li>
                <Separator orientation="vertical" className="h-full bg-gray-300" />
              </li>
            </>
          )}

          <li>
            <div className="px-4">
              <p className="text-gray-600">TIME ENDS:</p>
              <p className="text-lg font-bold text-red-500">{timeLeft}</p>
            </div>
          </li>
          <li>
            <Separator orientation="vertical" className="h-full bg-gray-300" />
          </li>
          <li>
            <div className="px-4">
              <p>Seller:</p>
              <p className="text-lg font-bold text-gray-800 py-1">{sellerProfile}</p>
            </div>
          </li>
        </ul>
      </CardContent>
      <CardFooter>
        <div className="flex flex-col mt-10 space-y-2">
          <Button>Bid now</Button>
          <Button variant="secondary">View seller&#39;s other items</Button>
          <Button variant="link">More Action</Button>
        </div>
      </CardFooter>
    </Card>
  );
};
