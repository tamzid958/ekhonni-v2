import * as React from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';

interface data {
  id: string; // Add `id` for dynamic routing
  title: string;
  description: string;
  img: string;
  price: number;
}

export function CardDemo({ id, title, description, img, price }: data) {

  return (
    <Link href={`/categoryProducts/products/${id}`} passHref>
      <Card
        className="w-64 h-96 cursor-pointer bg-transparent shadow-none transition-shadow border-none">
        <CardContent className="px-0">
          <AspectRatio ratio={1} className="bg-muted">
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
        <CardFooter className="px-0 flex-col items-start">
          <CardTitle className="mb-2 text-lg font-sans font-medium hover:underline">{title}</CardTitle>
          {/*<CardDescription className="text-md line-clamp-3 overflow-hidden text-ellipsis"*/}
          {/*                 title={description}>{description}</CardDescription>*/}
          <CardTitle className="text-2xl">${price}</CardTitle>
        </CardFooter>
      </Card>
    </Link>
  );
}
