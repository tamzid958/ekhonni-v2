import React from 'react';
import Sidebar from '@/components/CategorySidebar';
import { Separator } from '@/components/ui/separator';
import CustomErrorBoundary from '@/components/ErrorBoundary';
import { ProductSection } from '@/components/ProductSection';

interface Data {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  category: string;
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

  const bestSellingProducts = products.filter((product) => product.label === 'Best Selling').slice(0, 10);
  const limitedTimeDeals = products.filter((product) => product.label === 'Limited Time Deals').slice(0, 10);
  const topRatedProducts = products.filter((product) => product.label === 'Top Rated').slice(0, 10);

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
            {/* Best Selling Section */}

            <ProductSection
              title={'Best Selling'}
              products={bestSellingProducts}
              selectedCategory={selectedCategory} />

            <Separator />

            <ProductSection
              title={'Limited Time Deals'}
              products={limitedTimeDeals}

              selectedCategory={selectedCategory} />

            <Separator />

            <ProductSection
              title={'Top Rated'}
              products={topRatedProducts}
              selectedCategory={selectedCategory} />

            <Separator />
          </div>
        </div>
      </div>
    </div>
  );
}
