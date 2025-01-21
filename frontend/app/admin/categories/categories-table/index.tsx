'use client';
import * as React from 'react';
import { useSearchParams } from 'next/navigation';
import { keepPreviousData, useQuery } from '@tanstack/react-query';

import { columns } from './columns';
import CategoriesTable from './Table';
import { fetchCategories } from '@/data/categories';

type Props = {
  perPage?: number;
};

export default function AllCategories({ perPage = 10 }: Props) {
  const categoriesPage = useSearchParams().get('page');
  const page = Math.trunc(Number(categoriesPage)) || 1;

  const {
    data: categories,
  } = useQuery({
    queryKey: ['categories', page],
    queryFn: () => fetchCategories({ page, perPage }),
    placeholderData: keepPreviousData,
    select: (categoriesData) => {
      const { data, pages, ...rest } = categoriesData;

      return {
        data: data,
        pagination: {
          ...rest,
          pages,
          current: page < 1 ? 1 : Math.min(page, pages),
          perPage,
        },
      };
    },
  });


  return (
    <CategoriesTable
      columns={columns}
      data={categories.data}
      pagination={categories.pagination}
    />
  );
}