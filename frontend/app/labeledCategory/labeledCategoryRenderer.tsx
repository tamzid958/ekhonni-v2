'use client';

import React from 'react';
import { CardDemo } from '@/components/Card';
import Link from 'next/link';
import PaginationComponent from '@/components/pagination/Pagination';

interface Product {
  id: number;
  title: string;
  price: number;
  category: {
    id: number;
    name: string;
  };
  images: { imagePath: string }[];
}

interface Props {
  products: Product[];
  category: string;
  totalPages: number;
  currentPage: number;
}

export default function LabeledCategoryRenderer({ products, category, totalPages, currentPage }: Props) {
  return (
    <div className="flex-1 min-h-screen flex-col">
      <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden flex-grow">
        <h1 className="text-5xl font-bold my-12">{category} Products</h1>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {products.length === 0 ? (
            <div className="col-span-full flex items-center justify-center">
              <p className="text-center text-gray-600 text-3xl">No products found</p>
            </div>
          ) : (
            products.map((product) => (
              <div key={product.id} className="flex items-center justify-center pt-8">
                <Link href={`/productDetails?id=${product.id}`} className="cursor-pointer">
                  <CardDemo {...product} />
                </Link>
              </div>
            ))
          )}
        </div>
      </div>

      <div className="flex justify-center m-8">
        <PaginationComponent totalPages={totalPages} currentPage={currentPage} />
      </div>

    </div>
  );
}
