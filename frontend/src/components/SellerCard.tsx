'use client';

import * as React from 'react';
import Image from 'next/image';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Button } from '@/components/ui/button';
import { useRouter } from 'next/navigation';
import { ShoppingCart } from 'lucide-react';
import {  Toaster, toast } from "sonner"


interface CardDemoProps {
  id: string;
  name: string;
  description: string;
  img: string;
  price: number;
  status: string;
  condition: string;
  createdAt: string;
  updatedAt: string;
  seller: {
    id: string;
    name: string;
  };
  category: {
    name: string;
  };
  bids: never;
}

export function CardDemo({
                           id,
                           name,
                           description,
                           img,
                           price,
                           status,
                           condition,
                           createdAt,
                           updatedAt,
                           seller,
                           category,
                           bids,
                         }: CardDemoProps) {
  const router = useRouter();

  const handleBidNow = () => {
    router.push(`/productDetails?id=${id}`);
  };

  const handleClick = () => {
    toast.success("Product has been added to cart!");
  };

  return (
    <Card className="w-64 h-auto cursor-pointer bg-transparent shadow-none transition-shadow border-none">
      <Toaster position="top-right" />
      <CardContent className="px-0">
        <AspectRatio ratio={1} className="bg-muted"><Toaster position="top-right" />
          <Image
            src={img}
            alt={`Image of ${name}`}
            fill
            className="rounded-md object-fill"
          />

          <Button
            className="absolute mb-48 ml-40 mt-2 px-4 py-2 rounded shadow"
            variant="default"
            onClick={handleBidNow}
          >
            Bid Now
          </Button>
        </AspectRatio>
      </CardContent>
      <CardFooter className="px-0 flex-col items-start">
        <CardTitle className="mb-2 text-lg font-sans font-medium hover:underline">
          {name}
        </CardTitle>
        <CardTitle className="text-2xl">{`৳ ${price}`}</CardTitle>
        <Button
          className="absolute mb-48 ml-44 mt-2 px-4 py-2 rounded shadow"
          variant="default"
          onClick={handleClick}
        >
          <ShoppingCart />
        </Button>
      </CardFooter>
    </Card>
  );
}
