import React from "react";
import { ProductDetailsProps } from "../ProductDetailsClient";
import ProductDetailsClient from "../ProductDetailsClient";
import Loading from "@/components/Loading";


async function fetchProductDetails(productId: string): Promise<ProductDetailsProps> {
  const productResponse = await fetch(
    `http://localhost:8080/api/v2/product/${productId}`
  );
  const productData = await productResponse.json();

  const biddingCountResponse = await fetch(
    `http://localhost:8080/api/v2/bid/product/${productId}/count`
  );
  const biddingCountData = await biddingCountResponse.json();

  const biddingDetailsResponse = await fetch(
    `http://localhost:8080/api/v2/bid/buyer/product/${productId}`
  );
  const biddingDetailsData = await biddingDetailsResponse.json();

  const sellerId = productData.data.seller.id;

  const sellerRatingResponse = await fetch(
    `http://localhost:8080/api/v2/review/seller/${sellerId}/average`
  );
  const sellerRatingData = await sellerRatingResponse.json();

  const sellerLocationResponse = await fetch(
    `http://localhost:8080/api/v2/user/${sellerId}`
  );
  const sellerLocationData = await sellerLocationResponse.json();

  return {
    productDetails: productData.data,
    biddingCount: biddingCountData.data,
    biddingDetails: biddingDetailsData.data.content || [],
    sellerRating: sellerRatingData.data || 0,
    sellerLocation: sellerLocationData.address,
  };
}

export default async function ProductDetailsPage({
                                                   params,
                                                 }: {
  params: { id: string };
}) {
  const { id: productId } = params;

  const productData = await fetchProductDetails(productId);

  if (!productData) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loading />
      </div>
    );
  }

  const {
    productDetails,
    biddingCount,
    biddingDetails,
    sellerRating,
    sellerLocation,
  } = productData;

  return (
    <ProductDetailsClient
      productDetails={productDetails}
      biddingCount={biddingCount}
      biddingDetails={biddingDetails}
      sellerRating={sellerRating}
      sellerLocation={sellerLocation}
    />
  );
}
