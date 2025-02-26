'use client';
import * as React from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel';
import Link from 'next/link';
import { Button } from '@/components/ui/button';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';

export function Category() {
  const url = `/api/v2/category/all-v2`;
  console.log(url);

  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url));
  const categories = data?.data?.subCategories || [];

  console.log('brrrrr');
  console.log(categories);

  return (
    <div className="bg-brand-mid pl-40 pr-40 pt-10 pb-16">
      <div>
        <span className="flex justify-center text-4xl font-sans text-black">PRODUCT CATEGORY</span>
        <Link href="/categoryProducts">
          <Button variant="link" className="text-xl text-bold">SEE ALL</Button>
        </Link>
      </div>
      <Carousel
        opts={{
          align: 'start',
        }}
        className="w-full pt-10"
      >
        <CarouselContent className="max-w-3xl">
          {categories
            .map((category, index) => (
              <CarouselItem key={index} className="md:basis-1/2 lg:basis-1/3">
                <div className="p-1">
                  <Link
                    href={`/categoryProducts?category=${encodeURIComponent(category.name)}`}
                    passHref
                    className="">
                    <Card className="w-[230px] h-[230px] rounded-full">
                      <CardContent className="flex items-center justify-center p-1">
                        <img
                          src={category.imagePath}
                          alt={category.name}
                          className="h-full w-full object-cover rounded-full"
                        />
                      </CardContent>
                    </Card>
                  </Link>
                  <div className="flex flex-row w-full pt-2 justify-center">
                    <h2 className="justify-center text-xl font-sans font-bold text-black">{category.name}</h2>
                  </div>
                </div>
              </CarouselItem>
            ))}
        </CarouselContent>
        <CarouselPrevious
          className="w-6 h-6 sm:w-8 sm:h-8 md:w-10 md:h-10 lg:w-12 lg:h-12 xl:w-12 xl:h-12 left-[81%] sm:left-[84%] md:left-[85%] lg:left-[89%] xl:left-[91%] -top-[4%]"
          variant={'default'} />
        <CarouselNext
          className="w-6 h-6 sm:w-8 sm:h-8 md:w-10 md:h-10 lg:w-12 lg:h-12 xl:w-12 xl:h-12 left-[97%] -top-[4%]"
          variant={'default'} />
      </Carousel>
    </div>
  );
}