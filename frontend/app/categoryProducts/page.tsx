import React from 'react';
import Sidebar from '@/components/CategorySidebar';
import { Separator } from '@/components/ui/separator';
import CustomErrorBoundary from '@/components/ErrorBoundary';
import { ProductSection } from '@/components/ProductSection';

interface Data {
  id: string;
  price: number;
  name: string;
  description: string;
  status: string;
  condition: string;
  category: {
    id: number;
    name: string;
  };
  label: string;
}

interface Props {
  searchParams: { category?: string };
}

export default async function CategoryProductPage({ searchParams }: Props) {
  const selectedCategory = searchParams.category || 'All';

  // Directly fetch products data
  const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || `http://${process.env.HOST || 'localhost:3000'}`;
  const url =
    selectedCategory === 'All'
      ? `${baseUrl}/api/mock-data`
      : `${baseUrl}/api/mock-data?category=${encodeURIComponent(selectedCategory)}`;

  let products: Data[] = [];
  try {
    const response = await fetch(url, { cache: 'no-store' });

    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }

    products = await response.json();
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

            <ProductSection title={'All'} products={products} selectedCategory={selectedCategory} />

            {/*{labels.map((label) => {*/}
            {/*  const filteredProducts = products.filter((product) => product.label === label).slice(0, 10);*/}
            {/*  return (*/}
            {/*    <div key={label}>*/}
            {/*      <ProductSection*/}
            {/*        key={label}*/}
            {/*        title={label}*/}
            {/*        products={filteredProducts}*/}
            {/*        selectedCategory={selectedCategory} />*/}
            {/*      <Separator />*/}
            {/*    </div>*/}
            {/*  );*/}
            {/*})}*/}
          </div>
        </div>
      </div>
    </div>
  );
}
