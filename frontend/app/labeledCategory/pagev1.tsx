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
  searchParams: {
    category?: string,
    totalPages?: number;
    currentPage?: number;
  };
}

export default async function labeledCategory({ searchParams }: Props) {
  const category = searchParams.category || 'All';
  const totalPages = searchParams.totalPages || 10;
  const currentPage = searchParams.currentPage || 1;

  const url = category === 'All' ?
    `http://localhost:8080/api/v2/product/filter?page=${currentPage}` :
    `http://localhost:8080/api/v2/product/filter?categoryName=${encodeURIComponent(category)}&page=${currentPage}`;

  let products: Data[] = [];
  try {
    const response = await fetch(url, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    const json = await response.json();
    products = json.data.content;
  } catch (error) {
    console.error('Error fetching products:', error);
  }

  return (
    <div className="bg-gray-100 flex-1 min-h-screen flex-col">
      <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden flex-grow">
        <h1 className="text-5xl font-bold my-12">
          {category} Products
        </h1>
        {/*<Separator />*/}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {products.length === 0 ? (
            <div className="col-span-full flex items-center justify-center">
              <p className="text-center text-gray-600 text-3xl">No products found</p>
            </div>
          ) : (
            products.map((product) => (
              <div key={product.id} className="bg-white flex items-center justify-center pt-8">
                <Link key={product.id} href={`/productDetails?id=${product.id}`} className="cursor-pointer">
                  <CardDemo {...product} /> </Link>
              </div>
            )))}
        </div>
      </div>

      {/*TODO: Pagination fix*/}

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
  );

}