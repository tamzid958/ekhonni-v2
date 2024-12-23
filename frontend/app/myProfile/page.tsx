import * as React from 'react';
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs';
import { CardDemo } from '@/components/Card';
import { dataDemo } from '@/data/products';
import WatchlistPage from '../watchlist/page';

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
    <div className="mx-40 p-4 min-h-screen">
      {/* Heading */}
      <div className="mb-8">
        <header className="text-4xl mb-6 font-bold text-gray-800 mt-6">
          My Profile
        </header>
      </div>
      <div className="mt-10">
        <Tabs defaultValue="summary" className="flex flex-row ">
          <div className="mr-10"><TabsList className="flexbox flex-col space-y-2 items-start h-auto">
            <TabsTrigger value="summary">Summary</TabsTrigger>
            <TabsTrigger value="recently-viewed">Recently Viewed</TabsTrigger>
            <TabsTrigger value="bids">Bids & Offers</TabsTrigger>
            <TabsTrigger value="watchlist">Watchlist</TabsTrigger>
            <TabsTrigger value="purchases">Purchases</TabsTrigger>
            <TabsTrigger value="selling">Selling</TabsTrigger>
          </TabsList></div>

          <TabsContent value="summary">
            <WatchlistPage />
          </TabsContent>

          <TabsContent
            value="recently-viewed"
            className="flex-1 overflow-y-auto"
          >
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
            <WatchlistPage />
          </TabsContent>

          <TabsContent value="purchases">
            <h1>Purchases</h1>
          </TabsContent>

          <TabsContent value="selling">
            <h1>Selling</h1>
          </TabsContent>
        </Tabs>
      </div>
    </div>
  );
}
