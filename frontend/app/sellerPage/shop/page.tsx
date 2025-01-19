'use client';

import React, { useEffect, useState } from 'react';
import { CardDemo } from '@/components/SellerCard';

interface ProductData {
  id: string;
  price: number;
  name: string;
  description: string;
  status: string;
  condition: string;
  createdAt: string;
  updatedAt: string;
  seller: {
    id: string;
    name: string;
  };
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
  bids: any;
}

interface Category {
  name: string;
}

const ShopPage = () => {
  const [products, setProducts] = useState<ProductData[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [isSidebarOpen, setIsSidebarOpen] = useState<boolean>(false);
  const [selectedCategory, setSelectedCategory] = useState<string>("All");

  // Fetch products
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const apiUrl = 'http://192.168.68.164:8080/api/v2/product/filter';
        const response = await fetch(apiUrl);
        if (!response.ok) {
          throw new Error('Failed to fetch products');
        }
        const data = await response.json();
        const productsData = data?.data?.content || [];
        setProducts(productsData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchProducts();
  }, []);

  // Fetch categories
  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const userId = "d05a34b5-640e-4b2a-9f6b-4f168768d7cd";
        const apiUrl = `http://192.168.68.164:8080/api/v2/category/all/${userId}`;

        const response = await fetch(apiUrl);
        if (!response.ok) {
          throw new Error('Failed to fetch categories');
        }
        const data = await response.json();
        const categoriesData = data?.data?.map((categoryName: string, index: number) => ({
          id: index + 1,  // Assigning a placeholder ID, you can adjust this logic if necessary
          name: categoryName
        })) || [];

        setCategories(categoriesData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchCategories();
  }, []);


  // Handle category selection
  const handleCategorySelect = (categoryName: string) => {
    setSelectedCategory(categoryName);
    setIsSidebarOpen(false); // Close the sidebar after selecting a category
  };

  return (
    <div className="flex min-h-screen">
      {/* Sidebar */}
      {isSidebarOpen && (
        <div className="w-64 bg-gray-800 text-white p-4 mr-6 min-h-screen">
          <h2 className="text-xl font-bold mb-4">Categories</h2>
          <ul>
            <li>
              <button
                className="text-blue-500 hover:underline"
                onClick={() => handleCategorySelect('All')}
              >
                All
              </button>
            </li>
            {categories.map((category) => (
              <li key={category.name}>
                <button
                  className="text-blue-500 hover:underline"
                  onClick={() => handleCategorySelect(category.name)}
                >
                  {category.name}
                </button>
              </li>
            ))}
          </ul>

        </div>
      )}

      {/* Main Content */}
      <main className="container mx-auto px-4 py-6 flex-1">
        <div className="flex justify-between items-center mb-4">
          <button
            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
            className="flex items-center space-x-2 bg-gray-300 text-gray-700 font-medium px-4 py-2 rounded-lg hover:bg-gray-400 transition"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              className="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M4 6h16M4 12h16M4 18h16"
              />
            </svg>
            <span>Categories</span>
          </button>
        </div>

        {/* Product Grid */}
        <div>
          {selectedCategory !== 'All' ? (
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 mb-6">
              {products
                .filter((product) => product.category.name === selectedCategory)
                .map((product) => (
                  <CardDemo
                    key={product.id}
                    id={product.id}
                    title={product.name}
                    description={product.description}
                    img={product.images[0]?.imagePath || '/placeholder.jpg'}
                    price={product.price}
                    status={product.status}
                    condition={product.condition}
                    createdAt={product.createdAt}
                    updatedAt={product.updatedAt}
                    seller={product.seller}
                    category={product.category}
                    bids={product.bids}
                  />
                ))}
            </div>
          ) : (
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 mb-6">
              {products.map((product) => (
                <CardDemo
                  key={product.id}
                  id={product.id}
                  title={product.name}
                  description={product.description}
                  img={product.images[0]?.imagePath || '/placeholder.jpg'}
                  price={product.price}
                  status={product.status}
                  condition={product.condition}
                  createdAt={product.createdAt}
                  updatedAt={product.updatedAt}
                  seller={product.seller}
                  category={product.category}
                  bids={product.bids}
                />
              ))}
            </div>
          )}
        </div>
      </main>
    </div>
  );
};

export default ShopPage;
