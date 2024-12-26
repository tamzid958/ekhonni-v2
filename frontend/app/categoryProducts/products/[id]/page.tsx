'use client';

import React from 'react';
import { useParams } from 'next/navigation';
import useSWR from 'swr';
import Image from 'next/image';
import Loading from '@/components/Loading';
import Error from '@/components/ErrorBoundary';
import { Inter } from 'next/font/google';
import { Button } from '@/components/ui/button';

const inter = Inter({ subsets: ['latin'] });

interface data {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  category: string;
}

const ProductPage: React.FC = () => {
  const { id } = useParams(); // Dynamic route parameter for product ID
  const { data: data, error, isLoading } = useSWR<data>(
    `http://localhost:3000/api/mock-data/${id}`,
  );

  if (isLoading) {
    return <Loading />;
  }

  if (error) {
    return <Error message="Failed to load product details." />;
  }

  return (
    <div className="flex flex-col items-center bg-gray-100 min-h-screen">
      {/* Product Details */}
      <div className="flex flex-row w-3/4 bg-white rounded-lg shadow-lg p-6 mt-8 gap-6">
        {/* Left Section: Image */}
        <div className="flex flex-col w-1/2">
          <div className="relative w-full h-96 bg-gray-200 rounded-lg overflow-hidden">
            <Image
              src={data.img}
              alt={data.title}
              fill
              className="object-cover"
            />
          </div>
        </div>

        {/* Right Section: Product Information */}
        <div className="flex flex-col w-1/2">
          {/* Product Title and Description */}
          <div
            className={`flex flex-col bg-white rounded-lg shadow-md p-4 ${inter.className}`}
          >
            <h1 className="text-2xl font-bold text-gray-800 mb-2">
              {data.title}
            </h1>
            <p className="text-gray-600 mb-4">{data.description}</p>
            <div className="flex justify-between items-center">
              <span className="text-lg text-gray-800 font-semibold">
                Category:
              </span>
              <span className="text-lg text-blue-600 font-bold">
                {data.category}
              </span>
            </div>
            <div className="flex justify-between items-center mt-4">
              <span className="text-lg text-gray-800 font-semibold">
                Price:
              </span>
              <span className="text-lg text-green-600 font-bold">
                ${data.price}
              </span>
            </div>
          </div>

          {/* Action Button */}
          <div className="flex justify-center mt-6">
            <Button
              className="w-1/2 bg-black text-white px-4 py-2 rounded hover:bg-green-500 hover:text-black transition">
              Buy Now
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductPage;
