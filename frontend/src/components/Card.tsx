import * as React from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { Card, CardContent, CardDescription, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';

interface data {
  id: string; // Add `id` for dynamic routing
  title: string;
  description: string;
  img: string;
  price: number;
}

export function CardDemo({ id, title, description, img, price }: data) {
  console.log('id: ' + id);
  return (
    <Link href={`/categoryProducts/products/${id}`} passHref>
      <Card className="w-64 h-96 cursor-pointer hover:shadow-md bg-transparent shadow-none transition-shadow">
        <CardContent className="pt-6">
          <AspectRatio ratio={4 / 3} className="bg-muted">
            <Image
              src={img}
              alt={`Image of ${title}`}
              fill
              // width={96} // Set fixed width
              // height={96} // Set fixed height
              className="rounded-md object-cover"
            />
          </AspectRatio>
        </CardContent>
        <CardFooter className="flex-col items-start">
          <CardTitle className="mb-2 text-xl">{title}</CardTitle>
          <CardDescription className="text-md">{description}</CardDescription>
          <CardDescription className="text-lg mt-2">${price}</CardDescription>
        </CardFooter>
      </Card>
    </Link>
  );
}
