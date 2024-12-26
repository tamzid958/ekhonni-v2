import React from 'react';
import { CardDemo } from '@/components/Card';

interface data {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  category: string;
}

export default async function CategoryProductPage() {
  const response = await fetch('http://localhost:3000/api/mock-data', { cache: 'no-store' });

  if (!response.ok) {
    throw new Error('Failed to fetch data');
  }

  const allProductsItems: data[] = await response.json();

  return (
    <div className="space-y-6 container mx-auto my-4 px-4">
      <h1 className="text-2xl font-bold my-6">All Products</h1>

      {/* Best Selling Section */}
      <div>
        <h2 className="text-xl font-semibold my-4">Best Selling</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          {allProductsItems.map((item) => (
            <CardDemo key={item.id} {...item} />
          ))}
        </div>
      </div>

      {/* Limited Time Deals Section */}
      <div>
        <h2 className="text-xl font-semibold my-4">Limited Time Deals</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          {allProductsItems.map((item) => (
            <CardDemo key={item.id} {...item} />
          ))}
        </div>
      </div>

      {/* Top Rated Section */}
      <div>
        <h2 className="text-xl font-semibold my-4">Top Rated</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          {allProductsItems.map((item) => (
            <CardDemo key={item.id} {...item} />
          ))}
        </div>
      </div>
    </div>
  );
}
