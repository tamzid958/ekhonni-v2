// app/product/[id]/page.tsx
import React from 'react';
import Image from 'next/image';
import Loading from '@/components/Loading';
import Error from '@/components/ErrorBoundary';
import { Inter } from 'next/font/google';
import { Button } from '@/components/ui/button';

const inter = Inter({ subsets: ['latin'] });

interface ProductData {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  category: string;
}

const ProductPage = async ({ params }: { params: { id: string } }) => {
  const { id } = params;

  // Fetch the product data using the dynamic `id`
  const res = await fetch(`http://localhost:3000/api/mock-data/${id}`);

  if (!res.ok) {
    return <Error message="Failed to load product details." />;
  }

  const data: ProductData = await res.json();

  // Ensure that we have valid data before rendering
  if (!data) {
    return <Loading />;
  }

  return (
    <div className="flex flex-row justify-center items-center bg-gray-100 min-h-screen">
      <div className="flex h-auto bg-white w-full">
        {/* Left Section: Image and Thumbnails */}
        <div className="flex flex-col w-1/2 h-96">
          <div className="flex-1 bg-white rounded-xl items-center justify-center relative">
            <Image
              src={data.img} // Corrected from activeImg to data.img
              alt={data.title || 'Product Image'}
              fill
              className="object-cover rounded-xl p-1 shadow-lg"
            />
          </div>

          <div className="flex bg-white rounded w-full gap-1 h-16 overflow-hidden p-1">
            {/* Example of thumbnail images */}
            {Array(5)
              .fill(null)
              .map((_, idx) => (
                <div
                  key={idx}
                  className="flex-1 bg-white flex items-center justify-center border border-gray-400 rounded-xl relative"
                >
                  <Image
                    src={data.img} // Corrected this too
                    alt={`Thumbnail ${idx + 1}`}
                    fill
                    className="object-cover rounded-xl p-1"
                  />
                </div>
              ))}
          </div>
        </div>

        {/* Right Section: Product Information */}
        <div className="flex flex-col w-1/2 p-4">
          <div className="flex-1 bg-white flex items-center justify-center relative mb-1">
            <div
              className={`flex-1 flex-col w-fit bg-white shadow-lg rounded-lg p-2 pl-3 pr-5 mr-2 ${inter.className}`}
            >
              <h1 className="text-2xl font-bold text-gray-800 mb-1">
                {data.title || 'Product Details'} {/* Corrected to data.title */}
              </h1>
              <p className="text-gray-600 mb-1">{data.description}</p>

              <div className="flex justify-between items-center">
                <span className="text-[0.9rem] text-gray-800 font-semibold">
                  Base Price:
                </span>
                <span className="text-[0.9rem] text-green-600 font-bold">
                  ${data.price}
                </span>
              </div>
            </div>
          </div>

          {/* Bid Information */}
          <div className="flex-1 bg-white flex items-center justify-center relative mb-2">
            <div
              className={`flex-1 flex-col w-fit bg-white shadow-lg rounded-lg p-2 pl-3 pr-5 mr-2 ${inter.className}`}
            >
              <h2 className="text-xl font-bold text-gray-800 mb-2">Bid Information</h2>
              <div>
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-700">Bid Type:</span>
                  <span className="text-gray-600">Online Auction</span>
                </div>

                <div className="flex justify-between">
                  <span className="font-semibold text-gray-700">
                    Eligibility Status:
                  </span>
                  <span className="text-green-500">Eligible</span>
                </div>

                <div className="flex justify-between">
                  <span className="font-semibold text-gray-700">
                    Bid Starting Time:
                  </span>
                  <span className="text-gray-600">Dec 18, 2024, 10:00 AM</span>
                </div>

                <div className="flex justify-between">
                  <span className="font-semibold text-gray-700">
                    Bid Ending Time:
                  </span>
                  <span className="text-red-500">Dec 20, 2024, 5:00 PM</span>
                </div>

                <div className="flex justify-between">
                  <span className="font-semibold text-gray-700">
                    Last Updated:
                  </span>
                  <span className="text-gray-600">Dec 17, 2024</span>
                </div>

                <div className="flex justify-between items-center">
                  <span className="font-semibold text-gray-700">
                    Bid History:
                  </span>
                  <button className="text-blue-500 hover:text-blue-700 underline">
                    Check Now
                  </button>
                </div>

                <div className="flex justify-between">
                  <span className="font-semibold text-gray-700">Location:</span>
                  <span className="text-gray-600">New York, USA</span>
                </div>
              </div>
              <div className="flex justify-center">
                <Button
                  className="flex align-middle shadow-lg mt-2 w-[15rem] bg-black text-white px-4 py-2 rounded hover:bg-green-500 hover:text-black transition">
                  Place Your Bid
                </Button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductPage;
