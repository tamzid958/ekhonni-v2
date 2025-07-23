'use client';
import * as React from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel';
import Autoplay from 'embla-carousel-autoplay';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import Link from 'next/link';

export function Ads() {

  const url = `/api/v2/product/filter?applyBoost=true&size=1000`;
  console.log(url);

  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url));
  const products = data?.data?.content || [];

  console.log(products);

  const [carouselApi, setCarouselApi] = React.useState(null);
  const [selectedIndex, setSelectedIndex] = React.useState(0);

  React.useEffect(() => {
    if (!carouselApi) return;
    const onSelect = () => {
      setSelectedIndex(carouselApi.selectedScrollSnap());
    };
    onSelect();
    carouselApi.on('select', onSelect);
    return () => {
      carouselApi.off('select', onSelect);
    };
  }, [carouselApi]);

  return (
    <div
      className="flex justify-center items-center flex-row w-full bg-brand-bright pt-10 pb-10 gap-4 2xl:px-40 xl:px-30 lg:px-20 md:px-10 sm:px-0 ">
      <div className="w-3/4 max-h-[50vh] max-w-6xl flex flex-col items-center">
        <Carousel
          plugins={[Autoplay({ delay: 4000 })]}
          className="w-full"
          setApi={setCarouselApi}
          opts={{ loop: true }}
        >
          <CarouselContent>
            {products
              .filter((product) => product.boostData?.boostType === 'ONE_MONTH')
              .map((product, index) => (
                <CarouselItem key={index}>
                  <div className="p-1 relative group">
                    <Card
                      className="bg-brand-mid rounded-3xl overflow-hidden group-hover:bg-brand-light transition-colors duration-300">
                      <Link key={product.id} href={`/productDetails?id=${product.id}`} className="cursor-pointer">
                        <CardContent
                          className="flex items-center rounded-3xl overflow-hidden justify-center h-[50vh] p-0 relative">
                          <img
                            src={product.images[0].imagePath}
                            alt={product.title}
                            className="h-full w-full object-cover"
                          />
                          <div
                            className="absolute inset-0 flex flex-col justify-top items-center text-top pt-8 text-3xl font-bold text-white">
                          </div>
                          <div
                            className="absolute inset-0 flex flex-col justify-center items-center text-center opacity-0 group-hover:opacity-100 group-hover:translate-y-0 translate-y-10 transition-all duration-1000 bg-black bg-opacity-50 p-4 rounded-3xl">
                            <h3 className="text-2xl font-semibold text-white">{product.title}</h3>
                            <p className="text-lg text-white">{`$${product.price}`}</p>
                            <p className="text-sm text-white">{product.details}</p>
                          </div>
                        </CardContent>
                      </Link>
                    </Card>

                  </div>
                </CarouselItem>
              ))}
          </CarouselContent>
          <CarouselPrevious className={'invisible'} />
          <CarouselNext className={'invisible'} />
        </Carousel>

        <div className="flex space-x-2 mt-1 ">
          {Array.from({ length: 5 }).map((_, index) => (
            <button
              key={index}
              className={`w-3 h-3 rounded-full transition-colors ${
                selectedIndex === index
                  ? 'bg-brand-dark'
                  : 'bg-gray-300 hover:bg-gray-400'
              }`}
              onClick={() => carouselApi?.scrollTo(index)}
            />
          ))}
        </div>
      </div>

      <Carousel
        plugins={[
          Autoplay({
            delay: 3000, // 3 seconds delay between slides
          }),
        ]}
        opts={{
          align: 'start',
        }}
        orientation="vertical"
        className="w-1/4 max-w-xl max-h-[50%] mt-2 "
      >
        <CarouselContent className="-mt-1 h-[50vh]">
          {products
            .filter((product) => product.boostData?.boostType === 'ONE_WEEK')
            .map((product, index) => (
              <CarouselItem key={index} className="pt-1 md:basis-1/2">
                <div>
                  <Card className="w-full h-54 bg-brand-mid hover:bg-brand-light">
                    <Link key={product.id} href={`/productDetails?id=${product.id}`} className="cursor-pointer">
                      <CardContent className="flex items-center justify-center h-52 p-2 relative">
                        <img
                          src={product.images[0].imagePath}
                          alt={product.title}
                          className="h-full w-full object-cover rounded-xl"
                        />
                        <div
                          className="absolute inset-0 flex flex-col justify-center items-center opacity-0 text-center transition-all hover:opacity-100 hover:bg-brand-dark  p-4 rounded-xl z-40">
                          <h3 className="text-2xl font-semibold text-white">{product.title}</h3>
                          <p className="text-lg text-white">{`$${product.price}`}</p>
                          <p className="text-sm text-white">{product.details}</p>
                        </div>
                      </CardContent>
                    </Link>
                  </Card>
                </div>
              </CarouselItem>
            ))}
        </CarouselContent>

        <CarouselPrevious className={'invisible'} />
        <CarouselNext className={'invisible'} />
      </Carousel>
    </div>
  );
}
