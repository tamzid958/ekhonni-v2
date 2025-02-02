import React from 'react';
import MyProductRenderer from './MyProductRenderer';

interface ProductData {
  id: number;
  price: number;
  title: string;
  subTitle: string;
  description: string;
  createdAt: string;
  updatedAt: string;
  status: string;
  seller: {
    id: string;
    name: string;
  };
  condition: string;
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
  label: string;
  bids: never;
}

interface Props {
  searchParams: { page?: string };
}

interface CategoryNode {
  name: string;
  subCategories: string[];
  chainCategories: string[];
}

async function fetchMyProduct(userId: string, selectedCategory: string): Promise<ProductData[]> {
  const category = selectedCategory || 'All';
  const url =
    category === 'All'
      ? `http://localhost:8080/api/v2/product/user/filter`
      : `http://localhost:8080/api/v2/product/user/filter?userId=${encodeURIComponent(userId)}&categoryName=${encodeURIComponent(selectedCategory)}`;

  try {
    const response = await fetch(url, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    const json = await response.json();
    console.log('API Response Data:', json.data.content);
    return json.data.content;
  } catch (error) {
    console.error('Error fetching products:', error);
    return [];
  }
}

export default async function MyProductPage() {
  // const currentPage = Number(searchParams.page) || 1;
  const userId = '550e8400-e29b-41d4-a716-446655440006';
  const selectedCategory = 'All';
  const products = await fetchMyProduct(userId, selectedCategory);

  return <MyProductRenderer products={products} />;
}