import React from 'react';
import Sidebar from '@/components/CategorySidebar';
import { Separator } from '@/components/ui/separator';
import CustomErrorBoundary from '@/components/ErrorBoundary';
import { ProductSection } from '@/components/ProductSection';
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb';
import Link from 'next/link';

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

interface categories {
  category: {
    name: string;
  };
  subCategories: {
    name: string;
  }[];
  chainCategories: string[];
}

interface Props {
  searchParams: { category?: string };
}

export default async function CategoryProductPage({ searchParams }: Props) {
  const selectedCategory = searchParams?.category || 'All';

  const productUrl = selectedCategory === 'All'
    ? `http://localhost:8080/api/v2/product/filter`
    : `http://localhost:8080/api/v2/product/filter?categoryName=${encodeURIComponent(selectedCategory)}`;

  const categoryUrl = selectedCategory === 'All'
    ? `http://localhost:8080/api/v2/category/all-v2`
    : `http://localhost:8080/api/v2/category/${encodeURIComponent(selectedCategory)}/subcategories-v2`;

  let products: Data[] = [];
  let categories: categories = null;

  try {
    const [productRes, categoryRes] = await Promise.all([
      fetch(productUrl, { cache: 'no-store' }),
      fetch(categoryUrl, { cache: 'no-store' }),
    ]);

    if (!productRes.ok || !categoryRes.ok) {
      throw new Error('Failed to fetch data');
    }

    const productJson = await productRes.json();
    const categoryJson = await categoryRes.json();

    products = productJson.data.content;
    categories = categoryJson.data;

    // console.log(categories);
  } catch (error) {
    console.error('Error fetching data:', error);
  }

  // const labels = ['Best Selling', 'Limited Time Deals', 'Top Rated'];

  return (
    <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden">
      <h1 className="text-5xl font-bold my-12">
        {selectedCategory === 'All' ? 'All Products' : `${selectedCategory} Products`}
      </h1>

      <div className="flex">
        {/* Sidebar */}
        <CustomErrorBoundary>
          <Sidebar selectedCategory={selectedCategory} categories={categories} />
        </CustomErrorBoundary>

        {/* Main Content */}
        <div className="flex-1 ml-6">
          <div className="container mx-auto px-4 w-full space-y-6">
            <Breadcrumb>
              <BreadcrumbList>
                <BreadcrumbItem className="text-xl">
                  <BreadcrumbLink asChild>
                    <Link href="/categoryProducts">..</Link>
                  </BreadcrumbLink>
                </BreadcrumbItem>
                {categories.chainCategories.slice().reverse().map((cat, index) => (
                  <React.Fragment key={index}>
                    <BreadcrumbSeparator />
                    <BreadcrumbItem className="text-xl">
                      <BreadcrumbLink asChild>
                        <Link href={`/categoryProducts?category=${encodeURIComponent(cat)}`}>{cat}</Link>
                      </BreadcrumbLink>
                    </BreadcrumbItem>
                  </React.Fragment>
                ))}
              </BreadcrumbList>
            </Breadcrumb>
            <Separator className="mt-4" />
            <ProductSection title={selectedCategory} products={products} selectedCategory={selectedCategory} />
          </div>
        </div>
      </div>
    </div>
  );
}
