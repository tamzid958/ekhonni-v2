import React from 'react';
import { fetchProducts } from './fetchProducts';
import ProductRenderer from './ProductRender';

interface Props {
  searchParams: { page?: string };
}

export default async function ProductViewPage({ searchParams }: Props) {
  const currentPage = Number(searchParams.page) || 1;
  const products = await fetchProducts(currentPage);

  return <ProductRenderer products={products} currentPage={currentPage} totalPages={10} />;
}

