'use client';

import * as React from 'react';
import Image from 'next/image';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { useRouter } from 'next/navigation';

interface Product {
  id: number;
  title: string;
  price: number;
  category: {
    id: number;
    name: string;
  };
  images: { imagePath: string }[];
}

export function CardDemo({ id, title, price, category, images }: Product) {

  const router = useRouter();

  return (
    <Card
      className="group w-72 h-96 cursor-pointer bg-white shadow-md hover:shadow-lg transition-shadow border border-gray-200 rounded-lg">
      {/* Product Image */}
      <CardContent className="p-0">
        <AspectRatio ratio={1} className="bg-muted">
          <Image
            src={images[0]?.imagePath || '/placeholder.jpg'}
            alt={title}
            fill
            className="rounded-t-lg object-cover"
          />
        </AspectRatio>
      </CardContent>

      {/* Two-Column Footer Layout */}
      <CardFooter className="p-3 flex justify-between items-center w-full">
        {/* Left Side (Title & Category) */}
        <div className="flex flex-col items-start">
          <CardTitle className="mb-2 text-lg font-sans font-medium hover:underline" onClick={() => router.push(`/productDetails/${id}`)}
          >
            {title}
          </CardTitle>
          <p className="text-md text-gray-500">{category.name}</p>
        </div>

        {/* Right Side (Price & Button) */}
        <div className="flex flex-col items-end">
          <CardTitle className="text-lg font-bold text-gray-800">${price}</CardTitle>
          {/*<Button size="sm" className="mt-1">Buy Now</Button>*/}
        </div>
      </CardFooter>
    </Card>
  );
}
