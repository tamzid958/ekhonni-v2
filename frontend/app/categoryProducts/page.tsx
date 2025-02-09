import React from 'react';
import Sidebar from '@/components/CategorySidebar';
import { Separator } from '@/components/ui/separator';
import CustomErrorBoundary from '@/components/ErrorBoundary';
import { ProductSection } from '@/components/ProductSection';

interface Data {
  id: number;
  price: number;
  title: string;
  subTitle: string;
  description: string;
  status: string;
  condition: string;
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
  label: string;
}

interface Props {
  searchParams: { category?: string };
}

export default async function CategoryProductPage({ searchParams }: Props) {
  const selectedCategory = searchParams.category || 'All';
  
  const url = selectedCategory === 'All' ?
    `http://localhost:8080/api/v2/product/filter`
    : `http://localhost:8080/api/v2/product/filter?categoryName=${encodeURIComponent(selectedCategory)}`;

  let products: Data[] = [];
  try {
    const response = await fetch(url, { cache: 'no-store' });

    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }

    const json = await response.json();
    products = json.data.content;
  } catch (error) {
    console.error('Error fetching products:', error);
  }

  const labels = ['Best Selling', 'Limited Time Deals', 'Top Rated'];

  return (
    <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden">
      <h1 className="text-5xl font-bold my-12">
        {selectedCategory === 'All' ? 'All Products' : `${selectedCategory} Products`}
      </h1>

      <div className="flex">
        {/* Sidebar */}
        <CustomErrorBoundary>
          <Sidebar selectedCategory={selectedCategory} />
        </CustomErrorBoundary>

        {/* Main Content */}
        <div className="flex-1 ml-6">
          <div className="container mx-auto px-4 w-full space-y-6">
            <Separator className="mt-4" />

            {/* product view horizontal card, not slide box */}

            <ProductSection title={selectedCategory} products={products} selectedCategory={selectedCategory} />
          </div>
        </div>
      </div>
    </div>
  );
}
