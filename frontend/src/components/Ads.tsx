'use client';
import * as React from 'react';
import { Card, CardContent } from '@/components/ui/card';
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from '@/components/ui/carousel';
import Autoplay from 'embla-carousel-autoplay';
import { useSession } from 'next-auth/react';
import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';

export function Ads() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const url = `/api/v2/product/filter?applyBoost=true`;
  console.log(url);
  console.log(userToken);

  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url, userToken));
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
    <div className="flex flex-row w-full bg-white pt-10 pb-10 gap-20 pl-40 pr-40">
      <div className="w-3/4 max-h-[50vh] max-w-6xl flex flex-col items-center">
        <Carousel
          plugins={[Autoplay({ delay: 4000 })]}
          className="w-full"
          setApi={setCarouselApi}
          opts={{ loop: true }}
        >
          <CarouselContent>
            {products.filter((product) => product.boostData?.boostType === 'ONE_MONTH').map((product, index) => (
              <CarouselItem key={index}>
                <div className="p-1">
                  <Card className="bg-brand-mid rounded-3xl overflow-hidden hover:bg-brand-light">
                    <CardContent className="flex items-center rounded-3xl overflow-hidden justify-center h-[50vh] p-0">
                      <img
                        src={product.images[0].imagePath}
                        alt={product.title}
                        className="h-full w-full object-cover"
                      />
                    </CardContent>
                  </Card>
                </div>
              </CarouselItem>
            ))}
          </CarouselContent>
          <CarouselPrevious className={'invisible'} />
          <CarouselNext className={'invisible'} />
        </Carousel>

        <div className="flex space-x-2 mt-1">
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
            delay: 3000,
          }),
        ]}
        opts={{
          align: 'start',
        }}
        orientation="vertical"
        className="w-1/4 max-w-xl max-h-[440px] mt-6"
      >
        <CarouselContent className="-mt-1 h-[440px]">
          {products.filter((product) => product.boostData?.boostType === 'ONE_WEEK').map((product, index) => (
            <CarouselItem key={index}>
              <div className="p-1">
                <Card className="w-full h-52 bg-brand-mid rounded-3xl hover:bg-brand-light">
                  <CardContent className="flex items-center justify-center h-[22vh] p-4">
                    <img
                      src={product.images[0].imagePath}
                      alt={product.title}
                      className="h-full w-full object-cover rounded-2xl"
                    />
                  </CardContent>
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
