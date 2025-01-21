'use client';

import * as React from 'react';
import Image from 'next/image';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Button } from '@/components/ui/button';
import { useRouter } from 'next/navigation';
import { ShoppingCart } from 'lucide-react';

interface CardDemoProps {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  status: string;
  condition: string;
  createdAt: string;
  updatedAt: string;
  seller: {
    name: string;
  };
  category: {
    name: string;
  };
  bids: any;
}

export function CardDemo({
                           id,
                           title,
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
    router.push(`/productDetails?id=${id}`); // Navigate to productDetails with the id as a query parameter
  };

  return (
    <Card className="w-64 h-auto cursor-pointer bg-transparent shadow-none transition-shadow border-none">
      <CardContent className="px-0">
        <AspectRatio ratio={1} className="bg-muted">
          <Image
            src={img}
            alt={`Image of ${title}`}
            fill
            className="rounded-md object-cover"
          />

          <Button
            className="absolute mb-48 ml-40 mt-2 px-4 py-2 rounded shadow"
            variant="default"
            onClick={handleBidNow} // Trigger navigation with product id
          >
            Bid Now
          </Button>
        </AspectRatio>
      </CardContent>
      <CardFooter className="px-0 flex-col items-start">
        <CardTitle className="mb-2 text-lg font-sans font-medium hover:underline">
          {title}
        </CardTitle>
        <CardTitle className="text-2xl">${price}</CardTitle>
        <Button
          className="absolute mb-48 ml-40 mt-2 px-4 py-2 rounded shadow"
          variant="default"
        >
          <ShoppingCart />
        </Button>
      </CardFooter>
    </Card>
  );
}
