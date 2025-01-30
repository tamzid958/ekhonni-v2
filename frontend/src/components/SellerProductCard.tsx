import React from 'react';

type Product = {
  id: string;
  name: string;
  status: string;
  image: string;
};

const SellerProductCard: React.FC<{ product: Product }> = ({ product }) => {
  return (
    <div
      className={`border p-4 rounded ${
        product.status === "sold" ? "bg-white" : "bg-gray-400"
      }`}
    >
      <img
        src={product.image}
        alt={product.name}
        className="w-full h-40 object-cover rounded mb-4"
      />
      <h3 className="font-bold">{product.name}</h3>
      <p className="text-sm">
        {product.status === "sold" ? "Sold Out" : "Available"}
      </p>
    </div>
  );
};

export default SellerProductCard;
