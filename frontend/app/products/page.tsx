'use client';
import React, { useState } from 'react';
import Image from 'next/image';

interface ImageState {
  img1: string;
  img2: string;
  img3: string;
  img4: string;
  img5: string;
  img6: string;
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

          <div className="my-4">
            <button className="bg-violet-800 text-white font-semibold py-3 px-16 rounded-xl w-full">
              Bid Now
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductPage;
