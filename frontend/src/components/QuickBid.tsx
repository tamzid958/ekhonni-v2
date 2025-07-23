import * as React from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel';
import { Button } from '@/components/ui/button';
import { ShoppingCart } from 'lucide-react';
import Link from 'next/link';

type Props = {
  title?: string;
}

export function QuickBid({ title }: Props) {
  return (
    <div className="bg-brand-bright pl-40 pr-40 pt-10 pb-16 ">
      <div>
        <h2 className="flex justify-center text-4xl font-sans text-black">{title}</h2>
        <Link href="/categoryProducts">
          <Button variant="link"
                  className="text-xl text-bold border-2 border-black bg-brand-bright p-1">SEE
            ALL</Button>
        </Link>
      </div>
      <Carousel
        opts={{
          align: 'start',
        }}
        className="w-full pt-10"
      >
        <CarouselContent className="max-w-4xl">
          {Array.from({ length: 15 }).map((_, index) => (
            <CarouselItem key={index} className="md:basis-1/2 lg:basis-1/3">
              <div className="p-1">
                <Card className="realtive overflow-hidden w-[100%] h-[45%]">
                  <CardContent
                    className="flex aspect-square items-center justify-center rounded-2xl p-0">
                    <img
                      src={`ad${((index + 1) % 5) + 1}.png`}
                      alt={`public/AdsImage/ad ${index + 1}`}
                      className="h-full w-full object-cover"
                    />
                    <Button
                      className="absolute mb-48 ml-36 px-4 py-2 rounded shadow"
                      variant="default"
                    >
                      Bid Now
                    </Button>
                  </CardContent>
                </Card>
                <div className="flex flex-row w-full pt-2">
                  <div className="flex flex-col w-3/4">
                    <h2 className=" font-sans text-black">Library Tool</h2>
                    <h2 className="text-xl font-sans text-black">$45.00</h2>
                  </div>
                  <div>
                    <Button className="m-2 mr-4" variant="default">
                      <ShoppingCart />
                    </Button>
                  </div>
                </div>
              </div>
            </CarouselItem>
          ))}
        </CarouselContent>
        <CarouselPrevious className="w-12 h-12 left-[93%] -top-[4%]" variant={'default'} />
        <CarouselNext className="w-12 h-12 left-[97%] -top-[4%]" variant={'default'} />
      </Carousel>
    </div>
  );
}