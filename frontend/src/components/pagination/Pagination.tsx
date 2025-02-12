'use client';

import React from 'react';
import { Pagination, PaginationContent, PaginationEllipsis } from '@/components/ui/pagination';
import PaginationItem from './PaginationItem'; // Use the new reusable component

interface PaginationProps {
  totalPages: number;
  currentPage: number;
}

export default function PaginationComponent({ totalPages, currentPage }: PaginationProps) {
  return (
    <Pagination>
      <PaginationContent>
        {/* First Page */}
        <PaginationItem page={1} currentPage={currentPage} />

        {/* Left Ellipsis */}
        {currentPage > 3 && <PaginationEllipsis />}

        {/* Pages around current */}
        {Array.from({ length: 3 }, (_, i) => currentPage - 1 + i)
          .filter((page) => page > 1 && page < totalPages)
          .map((page) => (
            <PaginationItem key={page} page={page} currentPage={currentPage} />
          ))}

        {/* Right Ellipsis */}
        {currentPage < totalPages - 2 && <PaginationEllipsis />}

        {/* Last Page */}
        <PaginationItem page={totalPages} currentPage={currentPage} />
      </PaginationContent>
    </Pagination>
  );
}
