import Link from 'next/link';
import { CardDemo } from '@/components/Card';
import React from 'react';

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
      <div className="flex justify-between items-center">
        <h2 className="text-3xl font-semibold py-4">{title}</h2>
        <span>
          <Link
            href={{
              pathname: '/labeledCategory',
              query: { category: selectedCategory },
            }}
            className="text-xl"
          >
            See All
          </Link>
        </span>
      </div>
      {products.length === 0 ? (
        <p className="text-center text-gray-500">No products found in this label.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4 py-4 overflow-y-auto">
          {products.map((product) => (
            <Link key={product.id} href={`/productDetails?id=${product.id}`} className="cursor-pointer">
              <CardDemo {...product} />
            </Link>
          ))}
        </div>
      )}
    </div>
  );
}
