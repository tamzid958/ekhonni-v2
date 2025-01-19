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

  // Directly fetch products data
  const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || `http://${process.env.HOST || 'localhost:3000'}`;
  // const url =
  //   selectedCategory === 'All'
  //     ? `${baseUrl}/api/mock-data`
  //     : `${baseUrl}/api/mock-data?category=${encodeURIComponent(selectedCategory)}`;

  const url = selectedCategory === 'All' ?
    `http://192.168.68.164:8080/api/v2/product/filter`
    : `http://192.168.68.164:8080/api/v2/product/filter?categoryName=${encodeURIComponent(selectedCategory)}`;

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

            <ProductSection title={selectedCategory} products={products} selectedCategory={selectedCategory} />

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
