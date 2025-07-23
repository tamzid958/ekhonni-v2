'use client';

import React from 'react';
import { Pagination, PaginationContent, PaginationEllipsis, PaginationItem } from '@/components/ui/pagination';
import CustomPaginationItem from '@/components/pagination/CustomPaginationItem';

interface PaginationProps {
  totalPages: number;
  currentPage: number;
}

export default function PaginationComponent({
                                              totalPages = 1,
                                              currentPage,
                                            }: PaginationProps) {
  if (totalPages <= 1) {
    return (
      <Pagination>
        <PaginationContent>
          {/* Display the single page */}
          <PaginationItem>
            <CustomPaginationItem page={1} currentPage={currentPage} />
          </PaginationItem>
        </PaginationContent>
      </Pagination>
    );
  }

  return (
    <Pagination>
      <PaginationContent>
        {/* First Page */}
        <PaginationItem>
          <CustomPaginationItem page={1} currentPage={currentPage} />
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
              <CustomPaginationItem page={page} currentPage={currentPage} />
            </PaginationItem>
          ))}

        {/* Right Ellipsis */}
        {currentPage < totalPages - 2 && (
          <PaginationItem>
            <PaginationEllipsis />
          </PaginationItem>
        )}

        {/* Last Page */}
        {totalPages > 1 && (
          <PaginationItem>
            <CustomPaginationItem page={totalPages} currentPage={currentPage} />
          </PaginationItem>
        )}
      </PaginationContent>
    </Pagination>
  );
}
