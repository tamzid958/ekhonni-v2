'use client';

import React, { useState } from 'react';
import { Separator } from '@/components/ui/separator';
import { Package } from 'lucide-react';
import { CardDemo } from '@/components/Card';
import { Badge } from '@/components/ui/badge';
import { Button } from '@/components/ui/button';
import { useRouter } from 'next/navigation';
import { DrawerDemo } from './Components/Drawer';

interface ProductData {
  id: number;
  price: number;
  title: string;
  subTitle: string;
  description: string;
  createdAt: string;
  updatedAt: string;
  status: string;
  seller: {
    id: string;
    name: string;
  };
  condition: string;
  boostData?: {
    boostType: string;
    boostedAt: string;
    expiresAt: string;
  } | null;
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
  label: string;
  bids: never;
}

interface MyProductPageProps {
  products: ProductData[];
  filter: string;
  setFilter: React.Dispatch<React.SetStateAction<string>>;
}


export default function MyProducts({ products, filter, setFilter }: MyProductPageProps) {
  const [searchQuery, setSearchQuery] = useState<string>('');
  const router = useRouter();
  // const { setProduct } = useProduct();


  const getStatusBadge = (status: string) => {
    switch (status) {
      case 'APPROVED':
        return <Badge variant="default">Approved</Badge>;
      case 'DECLINED':
        return <Badge variant="destructive">Declined</Badge>;
      case 'PENDING_APPROVAL':
        return <Badge variant="secondary">Pending Approval</Badge>;
      case 'ARCHIVED':
        return <Badge variant="outline">Archived</Badge>;
      case 'SOLD':
        return <Badge variant="default">Sold</Badge>;
      default:
        return <Badge variant="default">Default</Badge>;
    }
  };

  const getStatusBoostBadge = (id, boostData) => {
    switch (boostData) {
      case null:
        return <DrawerDemo id={id} />;
      default:
        return <span>{id}</span>;
    }
  };

  return (
    <div className="space-y-6 h-screen container mx-12 p-4">
      <div className="flex flex-col justify-between mt-4">
        <div className="flex justify-between">
          <h1 className="text-3xl font-semibold mb-6 text-gray-700">Products</h1>
          <div>
            <input
              type="text"
              placeholder="Search by name"
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-700 shadow-md focus:outline-none focus:ring-2 focus:ring-blue-200"
            />
          </div>
        </div>

        {/*Button Group*/}
        <div className="flex mb-6 space-x-4">
          {['APPROVED', 'PENDING_APPROVAL', 'DECLINED', 'ARCHIVED', 'SOLD'].map((status) => (
            <button
              key={status}
              onClick={() => setFilter(status)}
              className={`text-sm font-medium pb-2 ${
                filter === status ? 'border-b-2 border-blue-500' : ' text-gray-700 hover:bg-gray-100'
              }`}
            >
              {status.replace('_', ' ')}
            </button>
          ))}
        </div>
        <div className="w-full"><Separator />
        </div>
      </div>
      {/* Products */}

      <div className="space-y-6 mx-16">
        {products.length === 0 ? (
          <div className="flex flex-col mx-60 items-center justify-center text-gray-600">
            <Package className="w-24 h-24 mb-4 text-gray-400" />
            <p className="text-center font-semibold text-3xl">No products found</p>
          </div>
        ) : (
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {products
              .filter((product) => product.title.toLowerCase().includes(searchQuery.toLowerCase()))
              .map((product) => (
                <div key={product.id} className="relative flex flex-col">
                  <span className="absolute top-2 left-2 z-10">{getStatusBadge(product.status)}</span>
                  <span
                    className="absolute top-2 right-8 z-10">{getStatusBoostBadge(product.id, product.boostData)}</span>
                  <CardDemo {...product}
                  />
                  {(filter === 'APPROVED' || 'SOLD') && <Button
                    variant="link"
                    className="absolute right-8 bottom-2"
                    onClick={() => {
                      // setProduct(product); // Store product in context
                      router.push(`/myProducts/bidList?id=${product.id}`);
                    }}
                  >
                    View Bids
                  </Button>}
                </div>
              ))}
          </div>
        )}
      </div>
    </div>
  );
}
