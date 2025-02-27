'use client';

import React, { useMemo } from 'react';
import { useParams } from "next/navigation"; // Import useParams()
import { useSession } from "next-auth/react";
import useSWR from "swr";
import ProductDetailsClient from "../ProductDetailsClient";
import Loading from "@/components/Loading";
import fetcher from "@/data/services/fetcher";

export default function ProductDetailsPage() {
  const params = useParams();
  const productId = params?.id as string;



  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token || "";

  const { data: productData, error: productError, isLoading: productLoading } = useSWR(
    productId ? `http://localhost:8080/api/v2/product/${productId}` : null,
    (url) => fetcher(url, userToken)
  );



  const { data: biddingCountData } = useSWR(
    productId ? `http://localhost:8080/api/v2/bid/product/${productId}/count` : null,
    fetcher
  );
  const { data: biddingDetailsData } = useSWR(
    productId ? `http://localhost:8080/api/v2/bid/buyer/product/${productId}` : null,
    fetcher
  );

  const sellerId = productData?.data?.seller?.id;
  const { data: sellerRatingData } = useSWR(
    sellerId ? `http://localhost:8080/api/v2/review/seller/${sellerId}/average` : null,
    fetcher
  );
  const { data: sellerLocationData } = useSWR(
    sellerId ? `http://localhost:8080/api/v2/user/${sellerId}` : null,
    fetcher
  );

  const isAuthorized = useMemo(() => {
    if (!productData?.data) return false;
    if (productData.data.status !== "SOLD") return true;
    return userId && (userId === productData.data.seller.id || userId === productData.data.buyer?.id);
  }, [productData, userId]);

  if (productLoading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <Loading />
      </div>
    );
  }

  if (!isAuthorized) {
    return (
      <div className="flex justify-center items-center h-screen bg-gray-100">
        <p className="text-3xl text-gray-700 opacity-70 font-semibold">
          Product is not available now
        </p>
      </div>
    );
  }



  return (
    <ProductDetailsClient
      productDetails={productData?.data}
      biddingCount={biddingCountData?.data || 0}
      biddingDetails={biddingDetailsData?.data?.content || []}
      sellerRating={sellerRatingData?.data || 0}
      sellerLocation={sellerLocationData?.address || ""}
      isAuthorized={isAuthorized}
    />
  );
}
