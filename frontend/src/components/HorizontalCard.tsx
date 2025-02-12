import { Separator } from '@/components/ui/separator';
import { Card, CardContent, CardDescription, CardFooter, CardTitle } from '@/components/ui/card';
import { Checkbox } from '@/components/ui/checkbox';
import Image from 'next/image';
import { Button } from '@/components/ui/button';
import * as React from 'react';

// New data format
interface Seller {
  id: string;
  name: string;
}

interface Image {
  imagePath: string;
}

interface WatchlistItem {
  id: number;
  title: string;
  subTitle: string;
  description: string;
  price: number;
  condition: string;
  conditionDetails: string;
  timeLeft?: string; // Time left can be optional as it's not present in the new format
  bidsAmount?: number; // Bids may not be available
  seller: Seller;
  category: {
    id: number;
    name: string;
  };
  images: Image[];
}

// Props type for the component
interface HorizontalCardProps {
  watchlistItem: WatchlistItem;
}

export const HorizontalCard: React.FC<HorizontalCardProps> = ({ watchlistItem }) => {
  const {
    title,
    subTitle,
    description,
    price,
    condition,
    conditionDetails,
    seller,
    images,
  } = watchlistItem;

  return (
    <Card className="flex items-center gap-4 p-4 mb-4 bg-white shadow-md rounded-lg">
      {/* Checkbox */}
      <div className="ml-2">
        <Checkbox />
      </div>

      {/* Product Image */}
      <CardContent className="w-16 h-16 relative">
        <Image
          src={images[0]?.imagePath || '/default.jpg'} // Fallback if no image is provided
          alt={title}
          layout="fill"
          className="rounded-md object-cover"
        />
      </CardContent>

      {/* Product Details */}
      <CardContent className="flex-1 flex flex-col space-y-2">
        {/* Title */}
        <CardContent className="p-6 text-gray-600">
          <CardTitle>{title}</CardTitle>
          <CardDescription className="pt-2">Condition: {condition}</CardDescription>
        </CardContent>
        {/* Details */}
        <ul className="flex flex-wrap px-6 md:justify-start text-sm gap-4 sm:justify-start">
          <li>
            <div className="pr-2">
              <p className="text-gray-600">Price:</p>
              <CardTitle className="py-0.5">US ${price}</CardTitle>
            </div>
          </li>
          <li>
            <Separator orientation="vertical" className="h-full bg-gray-300" />
          </li>

          {/*<li>*/}
          {/*  <div className="px-4">*/}
          {/*    <p className="text-gray-600">Condition Details:</p>*/}
          {/*    <p>{conditionDetails}</p>*/}
          {/*  </div>*/}
          {/*</li>*/}
          {/*<li>*/}
          {/*  <Separator orientation="vertical" className="h-full bg-gray-300" />*/}
          {/*</li>*/}
          <li>
            <div className="px-4">
              <p>Seller:</p>
              <p className="text-lg font-bold text-gray-800 py-1">{seller.name}</p>
            </div>
          </li>
        </ul>
      </CardContent>
      <CardFooter>
        <div className="flex flex-col mt-10 space-y-2">
          <Button>Bid now</Button>
          <Button variant="secondary">View seller&#39;s other items</Button>
          {/*<Button variant="link">More Action</Button>*/}
        </div>
      </CardFooter>
    </Card>
  );
};
