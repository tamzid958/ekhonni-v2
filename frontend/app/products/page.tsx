'use client';
import React, { ReactNode, useState } from 'react';
import Image from 'next/image';
import Link from 'next/link';
import { useRouter } from 'next/navigation';
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"

import { Card, CardContent } from "@/components/ui/card"
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel"
import { Button } from '@/components/ui/button';
import { Input } from 'postcss';
import { bidData } from '../mockData/bidData';
import { event } from 'next/dist/build/output/log';
import { Checkbox } from "@/components/ui/checkbox"



interface ImageState {
  img1: string;
  img2: string;
  img3: string;
  img4: string;
  img5: string;
  img6: string;
}

function Label(props: { htmlFor: string, children: ReactNode }) {
  return null;
}

const ProductPage: React.FC = () => {
  const [images, setImages] = useState<ImageState>({
    img1: 'https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,b_rgb:f5f5f5/3396ee3c-08cc-4ada-baa9-655af12e3120/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img2: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/e44d151a-e27a-4f7b-8650-68bc2e8cd37e/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img3: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/44fc74b6-0553-4eef-a0cc-db4f815c9450/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img4: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/d3eb254d-0901-4158-956a-4610180545e5/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img5: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/d3eb254d-0901-4158-956a-4610180545e5/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
    img6: 'https://static.nike.com/a/images/f_auto,b_rgb:f5f5f5,w_440/d3eb254d-0901-4158-956a-4610180545e5/scarpa-da-running-su-strada-invincible-3-xk5gLh.png',
  });

  const [activeImg, setActiveImage] = useState<string>(images.img1);
  const [bidAmount, setBidAmount] = useState<number>(105);
  const [currentProductIndex, setCurrentProductIndex] = useState(0);
  const [biddingAmount, setBiddingAmount] = useState("");

  const handleBidSubmit = (productId: number) => {
    console.log(`Bid submitted for product ID: ${productId}`);
    alert(`Your bid has been submitted for product ID: ${productId}`);

    setBiddingAmount("");
  };

  const router = useRouter();

  const closeModal = () => {
    router.back();
  };

    return (
    <div className="flex flex-col lg:flex-row gap-16 lg:items-center">
      <div className="flex flex-col gap-6 lg:w-2/4">
        <Image
          src={activeImg}
          alt="Product image"
          width={500}
          height={500}
          className="w-full h-full aspect-square object-cover rounded-xl"
        />
        <div className="flex gap-3 mt-4">
          {Object.values(images).map((img, index) => (
            <Image
              key={index}
              src={img}
              alt={`Thumbnail ${index + 1}`}
              width={80}
              height={80}
              className="w-24 h-24 border-2 rounded-md cursor-pointer"
              onClick={() => setActiveImage(img)}
            />
          ))}
        </div>
      </div>

      <div className="flex flex-col gap-3 w-[180]">
        <div>
          <span className="text-violet-600 font-semibold">Special Sneaker</span>
          <h1 className="text-3xl font-bold">Nike Invincible 3</h1>
        </div>
        <p className="text-gray-700">
          Special addition to our collection. Limited stock available.
        </p>
        <h6 className="text-2xl font-semibold">100.00$</h6>

        <div className="bg-white p-6 rounded-xl shadow-lg">
          <h3 className="text-xl font-semibold">Bid Information</h3>
          <div className="flex justify-between items-center my-4">
            <span className="font-semibold">Bid Status:</span>
            <span className="text-yellow-500">Check Now</span>
          </div>
          <div className="flex justify-between items-center my-4">
            <span className="font-semibold">Eligibility Status:</span>
            <span className="text-green-500">Eligible</span>
          </div>
          <div className="flex justify-between items-center my-4">
            <span className="font-semibold">Sale Status:</span>
            <span className="text-blue-500">Minimum Bid</span>
          </div>
          <div className="flex justify-between items-center my-4">
            <span className="font-semibold">Time Left:</span>
            <span className="text-red-500">0D 10H 20min</span>
          </div>
          <div className="flex justify-between items-center my-4">
            <span className="font-semibold">Current Bid:</span>
            <span className="font-semibold text-green-500">
              ${bidAmount}.00 USD
            </span>
          </div>

          <div className="w-full sm:w-1/2 md:w-1/3 lg:w-64 ">
            <Popover>
              <PopoverTrigger asChild>
                <div className="flex items-center justify-center h-full">
                  <Button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700">
                    Bid Now
                  </Button>
                </div>
              </PopoverTrigger>

                <PopoverContent className="w-full sm:w-3/4 md:w-2/3 lg:w-1/2  xl:w-1/3 2xl:w-96">
                  <div >
                    <div className="flex  items-center justify-between p-0.5 ">
                      <h1 className="text-lg font-semibold">Place your Bid</h1>
                      <button
                        onClick={closeModal}
                        className="text-xl text-gray-700 hover:text-red-500 focus:outline-none"
                      >
                        &times;
                      </button>
                    </div>


                    <div>
                      <div key={bidData[currentProductIndex].id}>
                        <p className="mb-1.5 mt-2">
                          <strong>{bidData[currentProductIndex].price}৳ (BDT)</strong> +{" "}
                          <strong>{bidData[currentProductIndex].delivery}৳ (BDT)</strong> Delivery
                        </p>
                        <p className="text-sm ">
                          {bidData[currentProductIndex].bids} bids,{" "}
                          {bidData[currentProductIndex].timeLeft} left
                        </p>
                      </div>
                    </div>

                    <div className="mt-2 relative">
                      <Carousel
                        opts={{
                          align: "start",
                        }}
                        className="w-full max-w-sm px-10"
                      >
                        <CarouselContent>
                          {/* Map through products */}
                          {bidData.map((product) =>
                            product.suggestedBids.map((bid, index) => (
                              <CarouselItem key={`${product.id}-${index}`} className="md:basis-1/5 lg:basis-1/3">
                                <div className="flex space-x-1 items-center">
                                  <Card className="h-1/4 space-x-5 bg-blue-300 w-20">
                                    <CardContent className="flex items-center justify-center p-4">
                                      <div
                                        className=" text-black rounded-lg px-4 py-2 text-sm font-semibold whitespace-nowrap">
                                        Bid {bid}৳
                                      </div>
                                    </CardContent>
                                  </Card>
                                </div>
                              </CarouselItem>
                            ))
                          )}
                        </CarouselContent>
                        <CarouselPrevious />
                        <CarouselNext />
                      </Carousel>
                    </div>


                    <div className="mt-6">
                      <input
                        type="text"
                        placeholder="Enter Bid (৳)"
                        className="w-full p-3 border rounded-md mb-2"
                        value={biddingAmount}
                        onChange={(e) => setBiddingAmount(e.target.value)}
                      />
                    </div>
                    <div className="flex items-center justify-center h-full">
                      <Button className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-700"
                              onClick={() => handleBidSubmit(bidData[currentProductIndex].id)}>
                        Submit
                      </Button>
                    </div>

                    <div className="mt-4 flex space-x-2 justify-center items-center">
                      <Checkbox />
                      <p className="text-s text-black-300 ">By bidding, you commit to buy if you win.</p>
                    </div>
                  </div>

                </PopoverContent>

            </Popover>

          </div>
        </div>
      </div>
    </div>
  )
    ;
};

export default ProductPage;
