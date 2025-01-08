import React from 'react';
import { CardDemo } from '@/components/Card';

interface Product {
  id: number;
  title: string;
  description: string;
  img: string;
  price: number;
}

export default async function SellerShopPage({ searchParams }: { searchParams: { category?: string } }) {
  const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || `http://${process.env.HOST || 'localhost:3000'}`;
  const url = `${baseUrl}/api/allProduct`;

  let products: Product[] = [];

  try {
    const response = await fetch(url, { cache: 'no-store' });

    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }

    products = await response.json();
  } catch (error) {
    console.error('Error fetching products:', error);
  }

  // Filter products by category if provided
  const filteredProducts = searchParams.category
    ? products.filter((product) => product.title.includes(searchParams.category))
    : products;

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
      {filteredProducts.map((product) => (
        <CardDemo
          key={product.id}
          title={product.title}
          description={product.description}
          img={product.img}
          price={product.price}
        />
      ))}
    </div>
  );
}
