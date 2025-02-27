import React from 'react';
import LabeledCategoryRenderer from './labeledCategoryRenderer';

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

export default async function LabeledCategory({ searchParams }: {
  searchParams: { category?: string; page?: string };
}) {
  const category = searchParams?.category || 'All';
  const currentPage = Number(searchParams?.page) || 1; // ✅ Correctly parsing `page`

  console.log('currentPage =>', currentPage);

  // Build URL with the correct query parameters
  const url =
    category === 'All'
      ? `http://localhost:8080/api/v2/product/filter?page=${currentPage}&size=5`
      : `http://localhost:8080/api/v2/product/filter?categoryName=${encodeURIComponent(category)}&page=${currentPage}`;

  let products: Product[] = [];
  let totalPages = 1;

  try {
    const response = await fetch(url, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    const json = await response.json();
    products = json.data.content;
    totalPages = json?.data?.page?.totalPages || 1;
  } catch (error) {
    console.error('Error fetching products:', error);
  }

  return (
    <LabeledCategoryRenderer
      products={products}
      category={category}
      totalPages={totalPages}
      currentPage={currentPage}
    />
  );
}
