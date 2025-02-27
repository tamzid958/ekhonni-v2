'use client';
import * as React from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel';
import { Button } from '@/components/ui/button';
import { ShoppingCart } from 'lucide-react';
import Link from 'next/link';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import { useSession } from 'next-auth/react';

type Props = {
  title?: string;
}

const handleAddToWatchlist = async (productId, session) => {
  if (!session) return;
  try {
    const response = await fetch(`http://localhost:8080/api/v2/user/watchlist?productId=${productId}`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${session?.user?.token}`, // Include access token if required
      },
    });
    if (!response.ok) throw new Error('Failed to add to watchlist');
    console.log('Added to watchlist!');
  } catch (error) {
    console.error('Error:', error);
  }
};


export function QuickBid({ title }: Props) {
  const url = `/api/v2/product/filter?status=APPROVED&size=1000`;
  console.log(url);
  const { data: session } = useSession();

  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url));
  const products = data?.data?.content || [];

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
          {products
            .map((product, index) => (
              <CarouselItem key={index} className="md:basis-1/2 lg:basis-1/3">
                <div className="p-1">
                  <Card className="realtive overflow-hidden w-[100%] h-[45%]">
                    <Link key={product.id} href={`/productDetails?id=${product.id}`} className="cursor-pointer">
                      <CardContent
                        className="flex aspect-square items-center justify-center rounded-2xl p-0">
                        <img
                          src={product.images[0].imagePath}
                          alt={product.title}
                          className="h-full w-full object-cover"
                        />
                        {session && (
                          <Button
                            className="absolute mb-48 ml-36 px-4 py-2 rounded shadow"
                            variant="default"
                          >
                            Bid Now
                          </Button>
                        )}
                      </CardContent>
                    </Link>
                  </Card>
                  <div className="flex flex-row w-full pt-2">
                    <div className="flex flex-col w-3/4">
                      <h2 className=" font-sans text-black">{product.title}</h2>
                      <h2 className="text-xl font-sans text-black">${product.price}</h2>
                    </div>
                    <div>
                      {session && (
                        <Button className="m-2 mr-4" variant="default"
                                onClick={() => handleAddToWatchlist(product.id, session)}>
                          <ShoppingCart />
                        </Button>
                      )}
                    </div>
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