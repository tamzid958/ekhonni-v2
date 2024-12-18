import * as React from 'react';
import Image from 'next/image';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { Separator } from '@/components/ui/separator';
import { Card, CardContent, CardFooter } from '@/components/ui/card';
import { Checkbox } from '@/components/ui/checkbox';
import { Button } from '@/components/ui/button';
import { CardDemo } from '@/components/Card';
import { dataDemo } from '@/data/products';
import { AspectRatio } from '@/components/ui/aspect-ratio';

// Mock data for products
const watchlistItems = [
  {
    id: 1,
    title: 'JUDE BELLINGHAM 2023-24 Topps Inception',
    img: 'https://static.nike.com/a/images/t_PDP_1728_v1/f_auto,q_auto:eco,u_126ab356-44d8-4a06-89b4-fcdcc8df0245,c_scale,fl_relative,w_1.0,h_1.0,fl_layer_apply/916e938d-cf8b-4120-8e68-10187499d3da/AIR+JORDAN+12+RETRO.png',
    price: 112.5,
    shipping: 27.59,
    timeLeft: '1d 17h left',
    condition: 'Ungraded',
  },
];

export default function MyProfile() {
  return (
    <div className="container mx-40 p-4 min-h-screen">
      {/* Heading */}
      <header className="text-4xl mb-6 font-bold text-gray-800 mt-6">
        My Profile
      </header>

      <Tabs defaultValue="summary" className="mb-6">
        <TabsList>
          <TabsTrigger value="summary">Summary</TabsTrigger>
          <TabsTrigger value="recently-viewed">Recently Viewed</TabsTrigger>
          <TabsTrigger value="bids">Bids & Offers</TabsTrigger>
          <TabsTrigger value="watchlist">Watchlist</TabsTrigger>
          <TabsTrigger value="purchases">Purchases</TabsTrigger>
          <TabsTrigger value="selling">Selling</TabsTrigger>
        </TabsList>

        <TabsContent value="summary">
          {/* Section Title */}
          <h1 className="text-2xl font-bold mb-4">Watchlist</h1>
          <Separator className="mb-6" />
          {/* Product Watchlist */}
          <div className="space-y-6">
            {watchlistItems.map((item) => (
              <Card key={item.id} className="flex items-center gap-4">
                {/* Checkbox */}
                <Checkbox className="ml-4" />

                {/* Product Image */}
                <CardContent className="w-24 h-24 relative">
                  <Image
                    src={item.img}
                    alt={item.title}
                    layout="fill"
                    className="rounded-md object-cover"
                  />
                </CardContent>

                {/* Product Details */}
                <div className="flex-1 flex flex-col space-y-2">
                  {/* Title */}
                  <h2 className="text-lg font-semibold">{item.title}</h2>

                  {/* Details */}
                  <div className="flex justify-between text-gray-500 text-sm">
                    <div className="mr-10">
                      <p>Condition: {item.condition}</p>
                      <p>{item.timeLeft}</p>
                    </div>
                    <Separator className="mx-12" orientation="vertical" />
                    <div className="mx-20">
                      <p className="text-lg font-bold text-gray-800">
                        ${item.price}
                      </p>
                      <p>+ ${item.shipping} shipping</p>
                    </div>
                    <div className="mx-20">
                      <p> Seller Name</p>
                      <p className="text-lg font-bold text-gray-800 py-1">
                        Seller Profile
                      </p>
                    </div>
                  </div>
                </div>
                <CardFooter>
                  <div className="flex flex-col mt-4 space-y-2">
                    <Button>Bid now</Button>
                    <Button variant="secondary">
                      View seller&#39;s other items
                    </Button>
                  </div>
                </CardFooter>
              </Card>
            ))}
          </div>
        </TabsContent>

        <TabsContent value="recently-viewed" className="flex-1 overflow-y-auto">
          <div className="flex mt-6 justify-center align-middle min-h-screen">
            {/* Render each card using map */}
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
              {dataDemo.map((item, index) => (
                <div
                  key={index}
                  className="w-60 h-80 bg-transparent rounded-lg overflow-hidden"
                >
                  <CardDemo {...item} />
                </div>
              ))}
            </div>
          </div>
        </TabsContent>

        <TabsContent value="bids">
          <h1>Bids & Offers</h1>
        </TabsContent>

        <TabsContent value="watchlist">
          {/* Product Watchlist */}
          <div className="space-y-6">
            {watchlistItems.map((item) => (
              <Card key={item.id} className="flex items-center gap-4">
                {/* Checkbox */}
                <Checkbox className="ml-4" />

                {/* Product Image */}
                <CardContent className="w-24 h-24 relative">
                  <AspectRatio ratio={1} className="bg-muted">
                    <Image
                      src={item.img}
                      alt={item.title}
                      layout="fill"
                      className="rounded-md object-cover h-full w-full"
                    />
                  </AspectRatio>
                </CardContent>

                {/* Product Details */}
                <div className="flex-1 flex flex-col space-y-2">
                  {/* Title */}
                  <h2 className="text-lg font-semibold">{item.title}</h2>

                  {/* Details */}
                  <div className="flex justify-between text-gray-500 text-sm">
                    <div className="mr-10">
                      <p>Condition: {item.condition}</p>
                      <p>{item.timeLeft}</p>
                    </div>
                    <Separator className="mx-12" orientation="vertical" />
                    <div className="mx-20">
                      <p className="text-lg font-bold text-gray-800">
                        ${item.price}
                      </p>
                      <p>+ ${item.shipping} shipping</p>
                    </div>
                    <div className="mx-20">
                      <p> Seller Name</p>
                      <p className="text-lg font-bold text-gray-800 py-1">
                        Seller Profile
                      </p>
                    </div>
                  </div>
                </div>
                <CardFooter>
                  <div className="flex flex-col mt-4 space-y-2">
                    <Button>Bid now</Button>
                    <Button variant="secondary">
                      View seller&#39;s other items
                    </Button>
                  </div>
                </CardFooter>
              </Card>
            ))}
          </div>
        </TabsContent>

        <TabsContent value="purchases">
          <h1>Purchases</h1>
        </TabsContent>

        <TabsContent value="selling">
          <h1>Selling</h1>
        </TabsContent>
      </Tabs>
    </div>
  );
}
