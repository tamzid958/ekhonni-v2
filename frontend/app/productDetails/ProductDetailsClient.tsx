'use client';

import React, { useEffect, useState } from "react";
import { CakeSlice, Star } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Toaster, toast } from "sonner";
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Carousel, CarouselContent, CarouselItem, CarouselNext, CarouselPrevious } from "@/components/ui/carousel";
import { QuickBid } from '@/components/QuickBid';
import { z } from "zod";
import { useSession } from 'next-auth/react';
import { useRouter } from "next/navigation";
import Link from 'next/link';
import { Popover, PopoverTrigger, PopoverContent } from '@/components/ui/popover';



interface ProductDetailsProps {
  productDetails: {
    id: string;
    title: string;
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
  };
  biddingCount: number | null;
  biddingDetails: Array<{ id: number; productId: number; amount: number; currency: string; status: string; createdAt: string | null }>;
  sellerRating: number;
  sellerLocation: string;
  // prevBidding: number;

}


export default function ProductDetailsClient({ productDetails, biddingCount, biddingDetails,  sellerRating, sellerLocation }: ProductDetailsProps) {
  const [selectedIndex, setSelectedIndex] = useState(0);
  const [bidAmount, setBidAmount] = useState('');
  const [error, setError] = useState('');
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);
  const [isCheckboxChecked, setIsCheckboxChecked] = useState(false);
  const [previousBid, setPreviousBid] = useState<number | null>(null);

  const { data: session, status } = useSession();
  const bidSchema = z.string().regex(/^\d+$/, 'Bid amount must be a number');
  const router = useRouter();

  const token = session?.user?.token;


  useEffect(() => {
    const fetchPreviousBid = async () => {
      if (!session || !productDetails.id) return;

      try {
        const response = await fetch(`http://localhost:8080/api/v2/bid/user/product/${productDetails.id}`, {
          headers: {
            "Authorization": `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          console.log("Fetched previous bid:", data);
          if (data.success && data.data?.amount) {
            setPreviousBid(data.data.amount);
          } else {
            console.error("No bid amount returned in the response", data);
          }
        } else {
          console.error("Failed to fetch previous bid, Response Status:", response.status);
        }
      } catch (error) {
        console.error("Error fetching previous bid:", error);
      }
    };

    fetchPreviousBid();
  }, [session, productDetails.id, token]);



  const Checkbox = ({ checked, onChange }) => (
    <input
      type="checkbox"
      checked={checked}
      onChange={onChange}
    />
  );

  const handleClick = async () => {

    const url = `http://localhost:8080/api/v2/user/watchlist?productId=${productDetails.id}`;
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`,
        },
      });

      console.log('Response Status:', response.status);

      if (response.ok) {
        const responseData = await response.json();
        console.log('Parsed bid response:', responseData);

        if (responseData.success) {
          toast.success('Added to wishlist successfully!');
          window.location.reload();

        } else {
          toast.error(responseData.message || 'Failed to add to wishlist.');
        }
      } else {
        toast.error('Received an invalid response from the server.');
      }
    } catch (error) {
      console.error('Error adding to wishlist:', error);
      toast.error('An error occurred while adding to wishlist.');
    }
    // toast.success('Product has been added to cart!');
  };

  const handleBidChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;

    if (value === '') {
      setBidAmount(value);
      setError('');
      setIsButtonEnabled(false);
      return;
    }

    const result = bidSchema.safeParse(value);

    if (result.success) {
      const bidValue = Number(value);
      if (previousBid !== null && bidValue <= previousBid) {
        setError(`Bid must be higher than your previous bid (৳${previousBid})`);
        setIsButtonEnabled(false);
      } else {
        setError("");
        setIsButtonEnabled(bidValue > 0);
      }
      setBidAmount(value);
    } else {
      setError(result.error.errors[0].message);
    }
  };


  const handleBidSubmit = async () => {
    if (!bidAmount || !isCheckboxChecked) {
      toast.error('Please enter a valid bid amount and agree to the terms.');
      return;
    }

    const requestBody = {
      productId: productDetails.id,
      amount: parseFloat(bidAmount),
      currency: "BDT",
    };

    const token = session?.user?.token;
    console.log(token);
    try {
      const response = await fetch(`http://localhost:8080/api/v2/bid`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
        body: JSON.stringify(requestBody),
      });

      console.log("Response Status:", response.status);

      if (response.ok) {
        const responseData = await response.json();
        console.log("Parsed bid response:", responseData);

        if (responseData.success) {
          toast.success("Bid placed successfully!");
          setBidAmount("");
          setIsButtonEnabled(false);
          window.location.reload();

        } else {
          toast.error(responseData.message || "Failed to place bid.");
        }
      } else {
        toast.error("Received an invalid response from the server.");
      }
    } catch (error) {
      console.error("Error submitting bid:", error);
      toast.error("An error occurred while placing your bid.");
    }
  };


  const handleCheckboxChange = (event) => {
    setIsCheckboxChecked(event.target.checked);
  };

  if (!productDetails) {
    return <div>Loading...</div>;
  }

  return (
    <div className="flex flex-col bg-brand-bright">
      <div className="flex flex-row ml-40 mr-40 pb-12 pt-1    setIsButtonVisible(false);
2">
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
            <p className="text-4xl">{productDetails.title}</p>
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
            <div className="pt-2">
              <Dialog>
                {!session ? (
                  <Popover>
                    <PopoverTrigger asChild>
                      <Button variant="custom" className="w-full font-bold">
                        START BIDDING
                      </Button>
                    </PopoverTrigger>
                    <PopoverContent className="p-4 bg-white border border-gray-300 rounded-md shadow-lg">
                      <p className="text-gray-800 font-medium mb-2">Please login to start bidding.</p>
                      <Link href="/auth/login" className="text-black underline hover:text-brand-dark">
                        Go to Login
                      </Link>
                    </PopoverContent>
                  </Popover>
                ) : (
                  <>
                    <DialogTrigger asChild>
                      <Button variant="custom" className="w-full font-bold">
                        START BIDDING
                      </Button>
                    </DialogTrigger>
                    <DialogContent className="w-[460px] bg-white border border-gray-300 rounded-lg p-6">
                      <DialogHeader>
                        <DialogTitle className="text-xl font-bold mb-2">Place Your Bid</DialogTitle>
                        <div className="text-gray-700">
                          <>
                            <div className="text-lg font-semibold mb-1">৳ {productDetails.price}</div>
                            <div className="text-sm text-gray-500 mb-4">{biddingCount} bids</div>
                            <div>
                              <Carousel className="max-w-sm">
                                <CarouselPrevious className="-left-4" />
                                <CarouselContent className="-ml-0">
                                  {biddingDetails &&
                                    [...biddingDetails]
                                      .sort((a, b) => b.amount - a.amount)
                                      .slice(0, 5)
                                      .map((bid, index) => (
                                        <CarouselItem key={index} className="md:basis-1/2 lg:basis-1/3">
                                          <div className="p-1">
                                            <div
                                              className="flex items-center justify-center p-2 border rounded-lg border-black aspect-auto text-sm">
                                              <span className="text-l font-bold">৳ {bid.amount}</span>
                                            </div>
                                          </div>
                                        </CarouselItem>
                                      ))}
                                </CarouselContent>
                                <CarouselNext className="-right-8" />
                              </Carousel>
                            </div>

                            <div className="flex flex-col gap-4 mb-4 pt-4">
                              <div className="flex items-center gap-2 mb-4 pt-4">
                                <input
                                  type="text"
                                  placeholder="Enter Bid(৳)"
                                  value={bidAmount}
                                  onChange={handleBidChange}
                                  disabled={!isCheckboxChecked}
                                  className={`flex-grow border border-black rounded-md p-2 text-gray-700 ${
                                    isCheckboxChecked ? 'bg-white' : 'bg-gray-200 cursor-not-allowed'
                                  }`}
                                />
                                <button
                                  className={`px-4 py-2 rounded-md ${
                                    isButtonEnabled
                                      ? 'bg-blue-400 text-white font-bold'
                                      : 'bg-gray-300 text-gray-500'
                                  }`}
                                  onClick={handleBidSubmit}
                                  disabled={!isButtonEnabled || !isCheckboxChecked}
                                >
                                  Bid
                                </button>
                              </div>
                              <div className="pt-0">
                                {error && <p className="text-red-500 text-sm">{error}</p>}
                              </div>
                              <div className="text-sm text-gray-500">
                                <div className="flex items-center">
                                  <Checkbox checked={isCheckboxChecked} onChange={handleCheckboxChange} />
                                  <span className="ml-2">
                      By selecting Bid, you are committing to buy this item if you are the winning bidder.
                    </span>
                                </div>
                              </div>
                            </div>
                          </>
                        </div>
                      </DialogHeader>
                    </DialogContent>
                  </>
                )}
              </Dialog>
            </div>


            <div className="pt-2">
              <Button variant="custom" className="w-full font-bold" onClick={handleClick}>
                ADD TO WISHLISTS
              </Button>
            </div>
            <div className="pt-4 inline-flex">
              <div className="font-bold w-40">SELLER NAME:</div>
              <Link href={`/sellerPage/${productDetails.seller.id}`}>
                <div className="pl-2 italic cursor-pointer hover:underline flex items-center space-x-1">
                  <span>{productDetails.seller.name.toUpperCase()}</span>
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    className="w-4 h-4 text-brand-dark"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13.828 10H21v8a2 2 0 01-2 2H5a2 2 0 01-2-2V7a2 2 0 012-2h8v2H5v11h14v-6.172l-4.414 4.414a1 1 0 01-1.414-1.414L19.172 10h-5.344z" />
                  </svg>
                </div>
              </Link>

            </div>
            <br />
            <div className="pt-2 font-bold inline-flex">
              <div className="w-40">SELLER RATING:</div>
              <div className="flex space-x-1 text-yellow-500">
                {[...Array(5)].map((_, i) => (
                  <Star key={i} className={i < sellerRating ? "text-yellow-500" : "text-gray-300"} />
                ))}
              </div>
            </div>
            <br />
            <div className="pt-2 inline-flex">
              <div className="font-bold w-40">DELIVERY FROM:</div>
              <div className="pl-2 italic">{sellerLocation}</div>
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
            <div className="pt-4">{biddingCount !== null ? biddingCount : "Loading..."}</div>

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
