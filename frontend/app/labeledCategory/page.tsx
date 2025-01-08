import React from 'react';
import { CardDemo } from '@/components/Card';

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
  searchParams: {
    category?: string,
    label?: string
  };
}

export default async function labeledCategory({ searchParams }: Props) {
  const category = searchParams.category || 'All';
  const label = searchParams.label || 'All';

  const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || `http://${process.env.HOST || 'localhost:3000'}`;
  const url = `${baseUrl}/api/mock-data?category=${encodeURIComponent(category)}&label=${encodeURIComponent(label)}`;

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

  return (
    <div className="bg-gray-100 flex flex-col">
      <div className="space-y-6 container mx-auto px-4 w-full overflow-hidden flex-grow">
        <h1 className="text-5xl font-bold my-12">
          {category} Category : {label} Products
        </h1>
        {/*<Separator />*/}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
          {products.length === 0 ? (
            <div className="col-span-full flex items-center justify-center">
              <p className="text-center text-gray-600 text-3xl">No products found</p>
            </div>
          ) : (
            products.map((product) => (
              <div key={product.id} className="bg-white flex items-center justify-center pt-8">
                <CardDemo {...product} />
              </div>
            )))}
        </div>
      </div>
    </div>
  );

}