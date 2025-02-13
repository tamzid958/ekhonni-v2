import Link from 'next/link';
import { CardDemo } from '@/components/Card';
import React from 'react';
import { Button } from '@/components/ui/button';

interface Data {
  id: number;
  price: number;
  title: string;
  subTitle: string;
  description: string;
  status: string;
  condition: string;
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
  label: string;
}

interface ProductSectionProps {
  title: string;
  products: Data[];
  selectedCategory: string;
}

export function ProductSection({ title, products, selectedCategory }: ProductSectionProps) {
  return (
    <div className="w-full mb-6">
      {products.length === 0 ? (
        <p className="text-center text-gray-500">No products found.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 py-4">
          {products.map((product) => (
            <Link key={product.id} href={`/productDetails?id=${product.id}`} className="cursor-pointer">
              <CardDemo {...product} />
            </Link>
          ))}
        </div>
      )}
      {products.length !== 0 && (
        <div className="flex justify-center m-8">
          <Link
            href={{
              pathname: '/labeledCategory',
              query: { category: selectedCategory },
            }}
          >
            <Button
              variant="secondary" size="lg"
              className="px-6 py-3 font-bold text-xl hover:bg-gray-200 text-gray-700 transition-all shadow-md hover:shadow-lg"
            >
              See More Products
            </Button>
          </Link>
        </div>
      )}
    </div>
  );
}
