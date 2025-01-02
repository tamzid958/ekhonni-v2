'use client';

import React, { useEffect, useState } from 'react';
import Sidebar from '@/components/CategorySidebar';
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area';
import { CardDemo } from '@/components/Card';
import { Separator } from '@/components/ui/separator';

interface Data {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  category: string;
  label: string;
}

export default function CategoryProductPage() {
  const [products, setProducts] = useState<Data[]>([]);
  const [selectedCategory, setSelectedCategory] = useState<string>('All');
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true); // Show loading state while fetching data

      try {
        const url =
          selectedCategory === 'All'
            ? '/api/mock-data'
            : `/api/mock-data?category=${encodeURIComponent(selectedCategory)}`;

        const response = await fetch(url, { cache: 'no-store' });

        if (response.ok) {
          const data: Data[] = await response.json();
          console.log(data); // Debugging fetched data
          setProducts(data);
        } else {
          console.error('Failed to fetch products');
        }
      } catch (error) {
        console.error('Error fetching products:', error);
      } finally {
        setLoading(false); // Remove loading state once data is fetched
      }
    };

    fetchData();
  }, [selectedCategory]); // Re-fetch data when the selected category changes

  return (
    <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden">
      <h1 className="text-2xl font-bold my-6">All Products</h1>

      <div className="flex">
        {/* Sidebar */}
        <Sidebar
          selectedCategory={selectedCategory}
          setSelectedCategory={setSelectedCategory}
        />

        {/* Main Content */}
        <div className="flex-1 ml-6">
          {loading ? (
            <p>Loading products...</p>
          ) : (
            <div className="container mx-auto px-4 w-full space-y-6">
              {/* Best Selling Section */}
              <div className="w-full mb-6">
                <h2 className="text-xl font-semibold space-x-4 p-4">Best Selling</h2>
                <ScrollArea className="w-full overflow-x-auto">
                  {products.filter((product) => product.label === 'Best Selling').length === 0 ? (
                    // If no products are found, show this message
                    <p className="text-center text-gray-500">No products found in this label.</p>
                  ) : (
                    <div className="flex w-max space-x-4 p-4">
                      {products
                        .filter((product) => product.label === 'Best Selling')
                        .map((item) => (
                          <CardDemo key={item.id} {...item} />
                        ))}
                    </div>
                  )}
                  <ScrollBar orientation="horizontal" />
                </ScrollArea>
              </div>

              <Separator />

              {/* Limited Time Deals Section */}
              <div className="w-full mb-6">
                <h2 className="text-xl font-semibold space-x-4 p-4">Limited Time Deals</h2>
                <ScrollArea className="w-full overflow-x-auto">
                  {products.filter((product) => product.label === 'Limited Time Deals').length === 0 ? (
                    <p className="text-center text-gray-500">No products found in this label.</p>
                  ) : (
                    <div className="flex w-max space-x-4 p-4">
                      {products
                        .filter((product) => product.label === 'Limited Time Deals')
                        .map((item) => (
                          <CardDemo key={item.id} {...item} />
                        ))}
                    </div>
                  )}
                  <ScrollBar orientation="horizontal" />
                </ScrollArea>
              </div>

              <Separator />

              {/* Top Rated Section */}
              <div className="w-full mb-6">
                <h2 className="text-xl font-semibold space-x-4 p-4">Top Rated</h2>
                <ScrollArea className="w-full overflow-x-auto">
                  {products.filter((product) => product.label === 'Top Rated').length === 0 ? (
                    <p className="text-center text-gray-500">No products found in this label.</p>
                  ) : (
                    <div className="flex w-max space-x-4 p-4">
                      {products
                        .filter((product) => product.label === 'Top Rated')
                        .map((item) => (
                          <CardDemo key={item.id} {...item} />
                        ))}
                    </div>
                  )}
                  <ScrollBar orientation="horizontal" />
                </ScrollArea>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
