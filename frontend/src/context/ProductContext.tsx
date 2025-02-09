'use client';

import React, { createContext, useContext, useEffect, useState } from 'react';

interface ProductData {
  id: number;
  title: string;
  description: string;
  price: number;
  images: { imagePath: string }[];
  seller: { id: string; name: string };
  status: string;
}

interface ProductContextType {
  product: ProductData | null;
  setProduct: (product: ProductData | null) => void;
}

// Create Context
const ProductContext = createContext<ProductContextType | undefined>(undefined);

// Provide Context
export function ProductProvider({ children }: { children: React.ReactNode }) {
  const [product, setProduct] = useState<ProductData | null>(null);

  // Restore product data from sessionStorage on refresh
  useEffect(() => {
    const storedProduct = sessionStorage.getItem('selectedProduct');
    if (storedProduct) {
      setProduct(JSON.parse(storedProduct));
    }
  }, []);

  // Store product data in sessionStorage whenever it changes
  useEffect(() => {
    if (product) {
      sessionStorage.setItem('selectedProduct', JSON.stringify(product));
    } else {
      sessionStorage.removeItem('selectedProduct');
    }
  }, [product]);

  return (
    <ProductContext.Provider value={{ product, setProduct }}>
      {children}
    </ProductContext.Provider>
  );
}

// Custom hook for using ProductContext
export function useProduct() {
  const context = useContext(ProductContext);
  if (!context) {
    throw new Error('useProduct must be used within a ProductProvider');
  }
  return context;
}
