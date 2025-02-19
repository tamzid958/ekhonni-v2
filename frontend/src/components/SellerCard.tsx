'use client';

import * as React from 'react';
import Image from 'next/image';
import { Card, CardContent, CardFooter, CardTitle } from '@/components/ui/card';
import { AspectRatio } from '@/components/ui/aspect-ratio';
import { Button } from '@/components/ui/button';
import { Toggle } from '@/components/ui/toggle';
import { useRouter } from 'next/navigation';
import { Heart, HeartOff, Star } from 'lucide-react';
import { Toaster, toast } from "sonner";
import { useSession } from 'next-auth/react';
import { useEffect, useState } from 'react';

interface CardDemoProps {
  id: string;
  title: string;
  description: string;
  img: string;
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

export function CardDemo({
                           id,
                           title,
                           description,
                           img,
                           price,
                           status,
                           condition,
                           createdAt,
                           updatedAt,
                           seller,
                           category,
                           bids,
                         }: CardDemoProps) {
  const router = useRouter();
  const { data: session } = useSession();
  const token = session?.user?.token;

  const [isWishlisted, setIsWishlisted] = useState(false);



  useEffect(() => {
    const checkWishlistStatus = async () => {
      if (!token || !id) return;

      try {
        const response = await fetch(`http://localhost:8080/api/v2/user/watchlist/contains?productId=${id}`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
          },
        });

        if (response.ok) {
          const data = await response.json();
          setIsWishlisted(data?.data);
        }
      } catch (error) {
        console.error("Error fetching wishlist status:", error);
      }
    };

    checkWishlistStatus();
  }, [id, token]);

  const toggleWishlist = async () => {
    if (!token) {
      toast.error("You need to be logged in to use the wishlist.");
      return;
    }

    if (isWishlisted) {
      try {
        const response = await fetch("http://localhost:8080/api/v2/user/watchlist", {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
          body: JSON.stringify([parseInt(id)]),
        });

        const responseData = await response.json();
        if (response.ok && responseData.success) {
          toast.success("Removed from wishlist.");
          setIsWishlisted(false);
        } else {
          toast.error(responseData.message || "Failed to remove from wishlist.");
        }
      } catch (error) {
        console.error("Error removing from wishlist:", error);
        toast.error("An error occurred while removing from wishlist.");
      }
    } else {
      try {
        const response = await fetch(`http://localhost:8080/api/v2/user/watchlist?productId=${id}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
        });

        const responseData = await response.json();
        if (response.ok && responseData.success) {
          toast.success("Added to wishlist.");
          setIsWishlisted(true);
        } else {
          toast.error(responseData.message || "Failed to add to wishlist.");
        }
      } catch (error) {
        console.error("Error adding to wishlist:", error);
        toast.error("An error occurred while adding to wishlist.");
      }
    }
  };


  return (
    <Card className="w-64 h-auto cursor-pointer bg-transparent shadow-none transition-shadow border-none">
      <CardContent className="px-0">
        <AspectRatio ratio={1} className="bg-muted">
          <Image
            src={img}
            alt={`Image of ${title}`}
            fill
            className="rounded-md object-fill"
          />

          <Button
            className="absolute mb-48 ml-40 mt-2 px-4 py-2 rounded shadow"
            variant="default"
            onClick={() => router.push(`/productDetails/${id}`)}
          >
            Bid Now
          </Button>
        </AspectRatio>
      </CardContent>
      <CardFooter className="px-0 flex-col items-start">
        <CardTitle className="mb-2 text-lg font-sans font-medium hover:underline">
          {title}
        </CardTitle>
        <CardTitle className="text-2xl">{`৳ ${price}`}</CardTitle>

        <Toggle
          className="absolute mb-48 ml-44 mt-2 px-4 py-2 rounded shadow"
          pressed={isWishlisted}
          onPressedChange={toggleWishlist}
        >
          {isWishlisted ? <Star className="w-5 h-5  text-black" fill="black"/> : <Star />}
        </Toggle>
      </CardFooter>
    </Card>
  );
}
