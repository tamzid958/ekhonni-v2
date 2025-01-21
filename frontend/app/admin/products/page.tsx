'use client';

import React, { useEffect, useState } from 'react';
import { HorizontalAdminCard } from '../components/HorizontalAdminCard';

interface Data {
  id: string;
  price: number;
  name: string;
  description: string;
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
}

export default function ProductView() {
  const [products, setProducts] = useState<Data[]>([]);
  const [loading, setLoading] = useState(true);
  const [filter, setFilter] = useState<string>('ALL'); // State to track the selected filter


  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const url = `http://localhost:8080/api/v2/product/filter`;
        const response = await fetch(url);
        const json = await response.json();
        setProducts(json.data.content);
      } catch (error) {
        console.error('Error fetching products:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  if (loading) {
    return <p className="text-center text-gray-600 text-3xl">Loading products...</p>;
  }

  const filteredProducts =
    filter === 'ALL' ? products : products.filter((product) => product.status === filter);

  return (
    <div className="space-y-6 min-h-screen container mx-12 px-4 w-full">
      <h1 className="text-5xl font-bold my-8">Products</h1>

      {/* Button Group */}
      <div className="flex space-x-4 mb-6">
        {['ALL', 'APPROVED', 'PENDING APPROVAL', 'DECLINED', 'ARCHIVED'].map((status) => (
          <button
            key={status}
            onClick={() => setFilter(status)}
            className={`px-4 py-2 rounded-lg ${
              filter === status ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'
            }`}
          >
            {status.replace('_', ' ')}
          </button>
        ))}
      </div>

      {/* Products */}
      <div className="space-y-6">
        {filteredProducts.length === 0 ? (
          <div className="col-span-full flex items-center justify-center">
            <p className="text-center text-gray-600 text-3xl">No products found</p>
          </div>
        ) : (
          filteredProducts.map((product) => (
            <div key={product.id} className="bg-white flex items-center justify-between pt-8">
              <HorizontalAdminCard
                id={parseInt(product.id)}
                title={product.name}
                img={product.images[0]?.imagePath || ''}
                price={product.price}
                shipping={0}
                timeLeft="1d 5h"
                condition={product.condition}
                bidsAmount={0}
                sellerProfile={product.seller.name}
                status={product.status}
              />
            </div>
          ))
        )}
      </div>
    </div>
  );
}