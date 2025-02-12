'use client';

import React from 'react';
import { usePathname, useSearchParams } from 'next/navigation';
import Link from 'next/link';
import { PaginationLink } from '@/components/ui/pagination';

interface PaginationItemProps {
  page: number;
  currentPage: number;
}

export default function PaginationItem({ page, currentPage }: PaginationItemProps) {
  const searchParams = useSearchParams();
  const pathname = usePathname();

  // Clone existing query parameters
  const params = new URLSearchParams(searchParams.toString());
  params.set('currentPage', page.toString()); // Update only `currentPage`

  return (
    <Link href={`${pathname}?${params.toString()}`} passHref>
      <PaginationLink
        className={`px-4 py-2 rounded-md transition-colors ${
          currentPage === page ? 'bg-blue-500 text-white' : 'hover:bg-gray-200'
        }`}
      >
        {page}
      </PaginationLink>
    </Link>
  );
}
