'use client';

import * as React from 'react';
import Image from 'next/image';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Button } from '@/components/ui/button';
import { useRouter } from 'next/navigation';
import { ShoppingCart } from 'lucide-react';
import {  Toaster, toast } from "sonner";
import { useSession } from 'next-auth/react';
const { data: session, status } = useSession();



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

  const token = session?.user?.token;


  const handleBidNow = () => {
    router.push(`/productDetails?id=${id}`);
  };

  const handleClick = async () => {

    const url = `http://localhost:8080/api/v2/user/watchlist?productId=${id}`;
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      console.log('Response Status:', response.status);

      if (response.ok) {
        const responseData = await response.json();
        console.log('Parsed bid response:', responseData);

        if (responseData.success) {
          toast.success('Added to wishlist successfully!');
          window.location.reload();

        } else {
          toast.error(responseData.message || 'Failed to add to wishlist.');
        }
      } else {
        toast.error('Received an invalid response from the server.');
      }
    } catch (error) {
      console.error('Error adding to wishlist:', error);
      toast.error('An error occurred while adding to wishlist.');
    }
    // toast.success('Product has been added to cart!');
  };


  return (
    <Card className="w-64 h-auto cursor-pointer bg-transparent shadow-none transition-shadow border-none">
      <CardContent className="px-0">
        <AspectRatio ratio={1} className="bg-muted">
          <Image
            src={img}
            alt={`Image of ${title}`}
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
          {title}
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
