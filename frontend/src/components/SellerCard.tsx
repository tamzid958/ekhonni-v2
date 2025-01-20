import * as React from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Button } from '@/components/ui/button';
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
  return (
    <Link href={`/categoryProducts/products/${id}`} passHref>
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
            >
              Bid Now
            </Button>
          </AspectRatio>
        </CardContent>
        <CardFooter className="px-0 flex-col items-start">
          <CardTitle className="mb-2 text-lg font-sans font-medium hover:underline">{title}</CardTitle>
          {/*<div className="text-md font-sans">*/}
          {/*  <p><strong>Status:</strong> {status}</p>*/}
          {/*  <p><strong>Condition:</strong> {condition}</p>*/}
          {/*  <p><strong>Category:</strong> {category.name}</p>*/}
          {/*  <p><strong>Seller:</strong> {seller.name}</p>*/}
          {/*  <p><strong>Created At:</strong> {new Date(createdAt).toLocaleDateString()}</p>*/}
          {/*  <p><strong>Updated At:</strong> {new Date(updatedAt).toLocaleDateString()}</p>*/}

          {/* */}
          {/*</div>*/}
          <CardTitle className="text-2xl">${price}</CardTitle>
          <Button className="absolute mb-48 ml-40 mt-2 px-4 py-2 rounded shadow"
                  variant="default">
            <ShoppingCart/>
          </Button>
        </CardFooter>
      </Card>
    </Link>
  );
}
