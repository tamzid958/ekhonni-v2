'use client';

import React from 'react';
import { usePathname, useSearchParams } from 'next/navigation';
import { PaginationLink } from '@/components/ui/pagination';

interface PaginationItemProps {
  page: number;
  currentPage: number;
}

export default function CustomPaginationItem({ page, currentPage }: PaginationItemProps) {
  const searchParams = useSearchParams();
  const pathname = usePathname();

  // Clone existing query parameters
  const params = new URLSearchParams(searchParams.toString());
  params.set('page', page.toString());
  // params.delete('currentPage'); // ✅ Remove `currentPage` from the URL

  return (
    <PaginationLink
      href={`${pathname}?${params.toString()}`} // Directly set href
      className={`px-4 py-2 rounded-md transition-colors ${
        currentPage === page ? 'bg-blue-500 text-white' : 'hover:bg-gray-200'
      }`}
    >
      {page}
    </PaginationLink>
  );
}
