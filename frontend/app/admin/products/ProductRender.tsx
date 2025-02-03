'use client';

import React, { useState } from 'react';
import { HorizontalAdminCard } from '../components/HorizontalAdminCard';
import { Package } from 'lucide-react';
import { Data } from './fetchProducts';
import { Separator } from '@/components/ui/separator';
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
} from '@/components/ui/pagination';
import Link from 'next/link';

interface Props {
  products: Data[];
  totalPages: number;
  currentPage: number;
}

export default function ProductRenderer({ products, totalPages, currentPage }: Props) {

  const [filter, setFilter] = useState<string>('ALL');
  const [searchQuery, setSearchQuery] = useState<string>('');

  const filteredProducts =
    filter === 'ALL' ? products : products.filter((product) => product.status === filter);

  return (
    <div className="space-y-6 container mx-12 p-4">
      <div className="flex flex-col justify-between mt-4">
        <h1 className="text-3xl font-semibold mb-6 text-gray-700">Products</h1>

        {/* Button Group */}
        <div className="flex mb-6 space-x-4">
          {['ALL', 'APPROVED', 'PENDING_APPROVAL', 'DECLINED', 'ARCHIVED'].map((status) => (
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
      <div className="flex items-start gap-4">
        <div className="w-7/10 space-y-6 mx-16">
          {
            // Apply the filtering first, then check for the length
            filteredProducts
              .filter((product) => product.title.toLowerCase().includes(searchQuery.toLowerCase()))
              .length === 0 ? (
              <div className="flex flex-col mx-60 items-center justify-center text-gray-600">
                <Package className="w-24 h-24 mb-4 text-gray-400" />
                <p className="text-center font-semibold text-3xl">No products found</p>
              </div>
            ) : (
              filteredProducts
                .filter((product) => product.title.toLowerCase().includes(searchQuery.toLowerCase()))
                .map((product) => (
                  <div key={product.id} className="bg-white flex items-center justify-between pt-8">
                    <HorizontalAdminCard
                      id={parseInt(product.id)}
                      title={product.title}
                      img={product.images[0]?.imagePath || ''}
                      price={product.price}
                      shipping={0}
                      timeLeft="1d 5h"
                      condition={product.condition}
                      bidsAmount={0}
                      sellerProfile={product.seller.name}
                      status={product.status}
                    />
                  </div>
                ))
            )
          } {/* Pagination */}
          <div className="flex justify-center mt-8">
            <Pagination>
              <PaginationContent>
                {/* First Page */}
                <PaginationItem>
                  <Link href="?page=1">
                    <PaginationLink isActive={currentPage === 1}>1</PaginationLink>
                  </Link>
                </PaginationItem>

                {/* Left Ellipsis (if needed) */}
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
                      <Link href={`?page=${page}`}>
                        <PaginationLink isActive={currentPage === page}>{page}</PaginationLink>
                      </Link>
                    </PaginationItem>
                  ))}

                {/* Right Ellipsis (if needed) */}
                {currentPage < totalPages - 2 && (
                  <PaginationItem>
                    <PaginationEllipsis />
                  </PaginationItem>
                )}

                {/* Last Page */}
                <PaginationItem>
                  <Link href={`?page=${totalPages}`}>
                    <PaginationLink>{totalPages}</PaginationLink>
                  </Link>
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </div>
        </div>
        <div className="w-3/10 p-4">
          <input
            type="text"
            placeholder="Search by name"
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-700 shadow-md focus:outline-none focus:ring-2 focus:ring-blue-200"
          />
        </div>
      </div>
    </div>
  );
}
