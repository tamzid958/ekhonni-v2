'use client';

import React, { useEffect, useState } from 'react';
import { usePathname, useRouter, useSearchParams } from 'next/navigation';
import { HorizontalAdminCard } from '../components/HorizontalAdminCard';
import { Package } from 'lucide-react';
import { Data } from './page';
import { Separator } from '@/components/ui/separator';
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
} from '@/components/ui/pagination';
import { Toaster } from 'sonner';

interface Props {
  products: Data[];
  totalPages: number;
  currentPage: number;
  filter: string;
  setFilter: React.Dispatch<React.SetStateAction<string>>;
}

export default function ProductRenderer({ products, totalPages, currentPage, filter, setFilter }: Props) {
  const [searchQuery, setSearchQuery] = useState<string>('');
  const searchParams = useSearchParams();
  const pathname = usePathname();
  const router = useRouter();

  // Handle Pagination Changes
  const handlePageChange = (page: number) => {
    const params = new URLSearchParams(searchParams.toString());
    params.set('page', page.toString());
    router.push(`${pathname}?${params.toString()}`);
  };

  // Ensure Search Query Only Updates on Client
  useEffect(() => {
    setSearchQuery(searchParams.get('searchTerm') || '');
  }, [searchParams]);

  return (
    <div className="space-y-6 container mx-12 p-4">
      <div className="flex flex-col justify-between mt-4">
        <h1 className="text-3xl font-semibold mb-6 text-gray-700">Products</h1>
        <Toaster position="top-right" />

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
        <div className="w-full">
          <Separator />
        </div>
      </div>

      {/* Products */}
      <div className="flex items-start gap-4">
        <div className="w-7/10 space-y-6 mx-16">
          {products.length === 0 ? (
            <div className="flex flex-col mx-60 items-center justify-center text-gray-600">
              <Package className="w-24 h-24 mb-4 text-gray-400" />
              <p className="text-center font-semibold text-3xl">No products found</p>
            </div>
          ) : (
            products
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
          )}

          {/* Pagination */}
          <div className="flex justify-center mt-8">
            <Pagination>
              <PaginationContent>
                {/* First Page */}
                <PaginationItem>
                  <PaginationLink isActive={currentPage === 1} onClick={() => handlePageChange(1)}>
                    1
                  </PaginationLink>
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
                      <PaginationLink isActive={currentPage === page} onClick={() => handlePageChange(page)}>
                        {page}
                      </PaginationLink>
                    </PaginationItem>
                  ))}

                {/* Right Ellipsis (if needed) */}
                {currentPage < totalPages - 2 && (
                  <PaginationItem>
                    <PaginationEllipsis />
                  </PaginationItem>
                )}

                {/* Last Page */}
                {totalPages > 1 && (
                  <PaginationItem>
                    <PaginationLink isActive={currentPage === totalPages} onClick={() => handlePageChange(totalPages)}>
                      {totalPages}
                    </PaginationLink>
                  </PaginationItem>
                )}
              </PaginationContent>
            </Pagination>
          </div>
        </div>

        {/* Search Input */}
        <div className="w-3/10 p-4">
          <input
            type="text"
            placeholder="Search by name"
            value={searchQuery}
            onChange={(e) => {
              setSearchQuery(e.target.value);
              const params = new URLSearchParams(searchParams.toString());
              params.set('searchTerm', e.target.value);
              router.push(`${pathname}?${params.toString()}`);
            }}
            className="w-full border border-gray-300 rounded-lg py-3 px-4 text-gray-700 shadow-md focus:outline-none focus:ring-2 focus:ring-blue-200"
          />
        </div>
      </div>
    </div>
  );
}
