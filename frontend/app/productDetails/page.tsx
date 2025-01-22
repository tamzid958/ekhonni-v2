'use client';
import { CakeSlice, Star } from 'lucide-react';
import { Button } from "@/components/ui/button";
import { QuickBid } from "@/components/QuickBid";
import { Popover, PopoverContent, PopoverTrigger } from "@/components/ui/popover"
import React, { useEffect, useState } from 'react';
import { useSearchParams } from 'next/navigation';
import Link from 'next/link';
import {  Toaster, toast } from "sonner"



interface ProductDetailsData {
  id: string;
  name: string;
  description: string;
  images: Array<{ imagePath: string }>;
  price: number;
  status: string;
  condition: string;
  createdAt: string;
  updatedAt: string;
  seller: {
    id: string;
    name: string;
  };
  category: {
    name: string;
  };
  bids: never;
}

export default function ProductDetails() {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [productDetails, setProductDetails] = useState<ProductDetailsData | null>(null);
  const searchParams = useSearchParams();
  const productId = searchParams.get('id');



  useEffect(() => {
    const fetchProductDetails = async () => {
      if (!productId) return;

      try {
        const apiUrl = `http://localhost:8080/api/v2/product/${productId}`;
        const response = await fetch(apiUrl);
        if (!response.ok) {
          throw new Error('Failed to fetch product details');
        }
        const data = await response.json();
        setProductDetails(data.data);
      } catch (error) {
        console.error(error);
      }
    };
    fetchProductDetails();
  }, [productId]);

  if (!productDetails) {
    return <div>Loading...</div>;
  }

  const handleClick = () => {
    toast.success("Product has been added to cart!");
  };


  return (
    <div className="flex flex-col bg-brand-bright">
      <Toaster position="top-right" />
      <div className="flex flex-row ml-40 mr-40 pb-12 pt-12">
        <div className="flex flex-row">
          <div className="flex flex-col">
            {productDetails.images.map((image, index) => (
              <div key={index} className="pt-4">
                <img
                  src={image.imagePath}
                  alt={`Product Image ${index + 1}`}
                  className={`h-24 w-28 object-fill cursor-pointer ${
                    selectedIndex === index
                      ? "border-4 border-brand-dark"
                      : "border border-gray-200"
                  }`}
                  onClick={() => setSelectedIndex(index)}
                />
              </div>
            ))}
          </div>

          <div className="ml-4 mb-4 pt-4 pl-2 pr-2">
            <div className="relative">
              {/* Main Image with Fade Transition Effect */}
              <img
                src={productDetails.images[selectedIndex].imagePath}
                alt={`Product Image`}
                className="h-[36rem] w-[56rem] object-fill rounded-xl transition-opacity duration-500 ease-in-out"
                style={{
                  opacity: selectedIndex === -1 ? 0 : 1,
                }}
              />
            </div>
          </div>
        </div>


        <div className="w-4/12 mx-auto">
          <div className="pt-16 pl-12">
            <p className="text-4xl">{productDetails.name}</p>
            <p className="pt-2 italic">{productDetails.status}</p>
            <span className="inline-flex pt-4">
              <CakeSlice />
              <p className="text-gray-500 pl-2">Complimentary shipping</p>
            </span>
            <hr className="border-gray-400 border-1 mt-1" />
            <div className="inline-flex w-full pt-2 pb-4">
              <p className="text-xl font-bold">BASE PRICE</p>
              <p className="text-xl ml-auto text-right font-bold">
                {`৳ ${productDetails.price}`}
              </p>
            </div>
            <Popover>
              <PopoverTrigger asChild>
                <Button variant="custom" className="w-full font-bold">
                  START BIDDING
                </Button>
              </PopoverTrigger>
              <PopoverContent>Popover Content</PopoverContent>
            </Popover>

            <div className="pt-2">
              <Button variant="custom" className="w-full font-bold" onClick={handleClick}>
                ADD TO CART
              </Button>
            </div>
            <div className="pt-4 inline-flex">
              <div className="font-bold w-40">SELLER ID:</div>
              <Link href={`/sellerPage?id=${productDetails.seller.id}`} className=" hover:underline">
                {productDetails.seller.id}
              </Link>

            </div>
            <br />
            <div className="pt-2 font-bold inline-flex">
              <div className="w-40">SELLER RATING:</div>
              <div className="flex space-x-1 text-yellow-500">
                {[...Array(5)].map((_, i) => (
                  <Star key={i} />
                ))}
              </div>
            </div>
            <br />
            <div className="pt-2 inline-flex">
              <div className="font-bold w-40">DELIVERY FROM:</div>
              <div className="pl-2 italic">DHAKA</div>
            </div>
            <br />
            <div className="pt-2 inline-flex">
              <div className="font-bold w-40">POST CREATED AT:</div>
              <div className="pl-2 italic">{new Date(productDetails.createdAt).toLocaleDateString()}</div>
            </div>
          </div>
        </div>
      </div>

      {/* Description */}
      <div className="flex flex-col pt-8 pl-40 pr-40">
        <div className="flex justify-center text-4xl pb-4">PRODUCT DESCRIPTION</div>
        <div className="flex flex-row border-gray-400 border-[1px] rounded-2xl p-6 w-full">
          <div className="flex flex-col w-3/4 pl-4">
            <div className="text-xl font-bold">Description</div>
            <div className="pt-4">{productDetails.description}</div>

            <div className="text-xl font-bold pt-4">
              <div className="pb-4">Condition Report</div>
              <div className="pl-0 pb-2 inline-flex text-lg font-medium">
                {productDetails.condition}
              </div>
            </div>
          </div>

          <div className="flex flex-col w-1/4">
            <div className="text-xl font-bold pt-4">BIDS</div>
            <div className="pt-4">{productDetails.bids}</div>

            <div className="text-xl font-bold pt-4 pb-4">Dimensions</div>
            <div className="">Height: 6.1 inches / 15.5 cm</div>
            <div className="">Width: 7.87 inches / 20 cm</div>
            <div className="">Depth: 4.33 inches / 11 cm</div>
          </div>
        </div>
      </div>
      <QuickBid title={"SIMILAR PRODUCTS"} />
    </div>
  );
}
