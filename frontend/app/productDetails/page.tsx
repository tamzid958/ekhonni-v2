import React from "react";
import ProductDetailsClient from "./ProductDetailsClient";

async function fetchProductDetails(productId: string) {
  const productResponse = await fetch(`http://localhost:8080/api/v2/product/${productId}`);
  const productData = await productResponse.json();

  const biddingCountResponse = await fetch(`http://localhost:8080/api/v2/bid/product/${productId}/count`);
  const biddingCountData = await biddingCountResponse.json();

  const biddingDetailsResponse = await fetch(`http://localhost:8080/api/v2/bid/buyer/product/${productId}`);
  const biddingDetailsData = await biddingDetailsResponse.json();


  return {
    productDetails: productData.data,
    biddingCount: biddingCountData.data,
    biddingDetails: biddingDetailsData.data.content || [],
  };
}

export default async function ProductDetailsPage({ searchParams }: { searchParams: { id: string } }) {
  const { id: productId } = searchParams;

  if (!productId) {
    return <div>Product ID is missing!</div>;
  }

  const { productDetails, biddingCount, biddingDetails } = await fetchProductDetails(productId);
  console.log(productDetails.seller);


  return (
    <ProductDetailsClient
      productDetails={productDetails}
      biddingCount={biddingCount}
      biddingDetails={biddingDetails}
    />
  );
}
