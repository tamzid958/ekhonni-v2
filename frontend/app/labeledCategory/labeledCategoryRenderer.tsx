'use client';

import React from 'react';
import { CardDemo } from '@/components/Card';
import Link from 'next/link';
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
} from '@/components/ui/pagination';

interface Data {
  id: number;
  title: string;
  description: string;
  img: string;
  price: number;
}

interface Props {
  products: Data[];
  category: string;
  totalPages: number;
  currentPage: number;
}

export default function LabeledCategoryRenderer({ products, category, totalPages, currentPage }: Props) {
  return (
    <div className="bg-gray-100 flex-1 min-h-screen flex-col">
      <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden flex-grow">
        <h1 className="text-5xl font-bold my-12">{category} Products</h1>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {products.length === 0 ? (
            <div className="col-span-full flex items-center justify-center">
              <p className="text-center text-gray-600 text-3xl">No products found</p>
            </div>
          ) : (
            products.map((product) => (
              <div key={product.id} className="bg-white flex items-center justify-center pt-8">
                <Link href={`/productDetails?id=${product.id}`} className="cursor-pointer">
                  <CardDemo {...product} />
                </Link>
              </div>
            ))
          )}
        </div>
      </div>

      <div className="flex justify-center mt-8">
        <Pagination>
          <PaginationContent>
            {/* First Page */}
            <PaginationItem>
              <Link href={`?category=${category}&currentPage=1`}>
                <PaginationLink
                  className={`px-4 py-2 rounded-md transition-colors ${
                    currentPage === 1 ? 'bg-blue-500 text-white' : 'hover:bg-gray-200'
                  }`}
                >
                  1
                </PaginationLink>
              </Link>
            </PaginationItem>

            {/* Left Ellipsis */}
            {currentPage > 3 && (
              <PaginationItem>
                <PaginationEllipsis />
              </PaginationItem>
            )}

            {/* Pages around current */}
            {Array.from({ length: 3 }, (_, i) => currentPage - 1 + i)
              .filter((page) => page > 1 && page < totalPages)
              .map((page) => (
                <PaginationItem key={page}>
                  <Link href={`?category=${category}&currentPage=${page}`}>
                    <PaginationLink
                      className={`px-4 py-2 rounded-md transition-colors ${
                        currentPage === page ? 'bg-blue-500 text-white' : 'hover:bg-gray-200'
                      }`}
                    >
                      {page}
                    </PaginationLink>
                  </Link>
                </PaginationItem>
              ))}

            {/* Right Ellipsis */}
            {currentPage < totalPages - 2 && (
              <PaginationItem>
                <PaginationEllipsis />
              </PaginationItem>
            )}

            {/* Last Page */}
            <PaginationItem>
              <Link href={`?category=${category}&currentPage=${totalPages}`}>
                <PaginationLink
                  className={`px-4 py-2 rounded-md transition-colors ${
                    currentPage === totalPages ? 'bg-blue-500 text-white' : 'hover:bg-gray-200'
                  }`}
                >
                  {totalPages}
                </PaginationLink>
              </Link>
            </PaginationItem>
          </PaginationContent>
        </Pagination>
      </div>

    </div>
  );
}
