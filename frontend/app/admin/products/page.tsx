import React from 'react';
import { fetchProducts } from './fetchProducts';
import ProductRenderer from './ProductRender';

export default async function ProductViewPage() {
  const products = await fetchProducts();

  return <ProductRenderer products={products} />;
}

