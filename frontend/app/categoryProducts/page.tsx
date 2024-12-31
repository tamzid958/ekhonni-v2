'use client';

import React, { useEffect, useState } from 'react';
import { CardDemo } from '@/components/Card';
import Sidebar from '@/components/CategorySidebar';
import { Separator } from '@/components/ui/separator';
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area';

interface data {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  category: string;
}

export default function CategoryProductPage() {
  const [products, setProducts] = useState<data[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>('All');

  useEffect(() => {
    const fetchData = async () => {
      const response = await fetch('http://localhost:3000/api/mock-data', { cache: 'no-store' });

      if (response.ok) {
        const data: data[] = await response.json();
        console.log(data);  // Add this to check the fetched data
        setProducts(data);
      } else {
        console.error('Failed to fetch products');
      }
    };

    fetchData();
  }, []);

  const filteredProducts =
    selectedCategory === 'All'
      ? products
      : products.filter((product) => product.category === selectedCategory);

  return (
    <div className="space-y-6 container mx-auto my-4 px-4">
      <h1 className="text-2xl font-bold my-6">All Products</h1>

      {/* Best Selling Section */}
      <div>
        <Sidebar
          selectedCategory={selectedCategory}
          setSelectedCategory={setSelectedCategory}
        />
        <h2 className="text-xl font-semibold my-4">Best Selling</h2>
        <ScrollArea className="whitespace-nowrap rounded-md border">
          <div className="flex w-max space-x-4 p-4">
            {filteredProducts.map((item) => (
              <CardDemo key={item.id} {...item} />
            ))}
          </div>
          <ScrollBar orientation="horizontal" />
        </ScrollArea>
      </div>

      <Separator></Separator>

      {/* Limited Time Deals Section */}
      <div>
        <h2 className="text-xl font-semibold my-4">Limited Time Deals</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          {filteredProducts.map((item) => (
            <CardDemo key={item.id} {...item} />
          ))}
        </div>
      </div>

      {/* Top Rated Section */}
      <div>
        <h2 className="text-xl font-semibold my-4">Top Rated</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-6">
          {filteredProducts.map((item) => (
            <CardDemo key={item.id} {...item} />
          ))}
        </div>
      </div>
    </div>
  );
}