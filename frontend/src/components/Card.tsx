import * as React from 'react';
import Image from 'next/image';
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardTitle,
} from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';

interface data {
  title: string;
  description: string;
  img: string;
  price: number;
}

export function CardDemo({ title, description, img, price }: data) {
  return (
    <Card className="bg-transparent shadow-none">
      <CardContent className="pt-6">
        <AspectRatio ratio={4 / 3} className="bg-muted">
          <Image
            src={img}
            alt="Image of ${title}"
            fill
            className="h-full w-full rounded-md object-cover"
          />
        </AspectRatio>
      </CardContent>
      <CardFooter className="flex-col items-start">
        <CardTitle className="mb-2 text-xl">{title}</CardTitle>
        <CardDescription className="text-md">{description}</CardDescription>
        <CardDescription className="text-lg mt-2">৳ {price}</CardDescription>
      </CardFooter>
    </Card>
  );
}
