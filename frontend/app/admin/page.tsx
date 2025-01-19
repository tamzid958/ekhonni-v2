import React from 'react';
import { CardDemo } from '@/components/Card';

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

export default async function AdminPage() {

  // const baseUrl = process.env.NEXT_PUBLIC_BASE_URL || `http://${process.env.HOST || 'localhost:3000'}`;
  //
  // const url = `${baseUrl}/api/mock-data`;
  const url = `http://192.168.68.164:8080/api/v2/product/filter?categoryName=Antiques`;

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

  console.log(products);


  return (
    <div className="space-y-6 min-h-screen container mx-12 px-4 w-full">
      <h1 className="text-5xl font-bold my-8">
        Admin Page
      </h1>

      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-4">
        {products.length === 0 ? (
          <div className="col-span-full flex items-center justify-center">
            <p className="text-center text-gray-600 text-3xl">No products found</p>
          </div>
        ) : (
          products.map((product) => (
            <div key={product.id} className="bg-white flex items-center justify-center pt-8">
              <CardDemo
                id={product.id}
                title={product.name}
                description={product.description}
                img={product.images[0].imagePath}
                price={product.price}
              />
            </div>
          )))}
      </div>
    </div>
  );
}

