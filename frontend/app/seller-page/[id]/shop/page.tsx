'use client';

import React, { useEffect, useState } from 'react';
import { CardDemo } from '@/components/SellerCard';
import { useParams } from 'next/navigation';

interface ProductData {
  id: number;
  price: number;
  title: string;
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
  bids: never;
}

const ShopPage = () => {
  const params = useParams();
  const userId = params?.id as string;

  const [products, setProducts] = useState<ProductData[]>([]);
  const [categories, setCategories] = useState<string[]>([]);
  const [isSidebarOpen, setIsSidebarOpen] = useState<boolean>(false);
  const [selectedCategory, setSelectedCategory] = useState<string>('All');
  const [loadingProducts, setLoadingProducts] = useState<boolean>(true);
  const [loadingCategories, setLoadingCategories] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [sortOrder, setSortOrder] = useState<string>('asc');
  const [searchTerm, setSearchTerm] = useState<string>('');



  useEffect(() => {
    if (!userId) return;

    const fetchProducts = async () => {
       const apiUrl =
        selectedCategory === 'All'
          ? `http://localhost:8080/api/v2/product/filter?userId=${userId}`
          : `http://localhost:8080/api/v2/product/filter?userId=${encodeURIComponent(
            userId
          )}&categoryName=${encodeURIComponent(selectedCategory)}`;

      setLoadingProducts(true);

      try {
        const response = await fetch(apiUrl, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) throw new Error('Failed to fetch products');

        const data = await response.json();
        setProducts(data?.data?.content || []);
      } catch (error) {
        setError('Error fetching products');
      } finally {
        setLoadingProducts(false);
      }
    };

    fetchProducts();
  }, [selectedCategory, userId]);

  useEffect(() => {
    if (!userId) return;

    const fetchCategories = async () => {
      setLoadingCategories(true);

      try {
        const apiUrl = `http://localhost:8080/api/v2/category/all/${userId}`;
        const response = await fetch(apiUrl, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
          },
        });

        if (!response.ok) throw new Error('Failed to fetch categories');

        const data = await response.json();
        setCategories(data?.data || []);
      } catch (error) {
        setError('Error fetching categories');
      } finally {
        setLoadingCategories(false);
      }
    };

    fetchCategories();
  }, [userId]);

  const handleCategorySelect = (categoryName: string) => {
    setSelectedCategory(categoryName);
    setIsSidebarOpen(true);
  };

  const handleSortChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setSortOrder(e.target.value);
  };


  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchTerm(e.target.value);
  };

  const filteredAndSortedProducts = [...products]
    .filter((product) =>
      product.title.toLowerCase().includes(searchTerm.toLowerCase())
    )
    .sort((a, b) => (sortOrder === 'asc' ? a.price - b.price : b.price - a.price));




  return (
    <div className="flex min-h-screen">
      {isSidebarOpen && (
        <div className="flex flex-col top-120 left-15 mt-12 w-60 text-black p-4 rounded-lg">
          <h2 className="text-xl font-bold mb-4">Categories</h2>
          <ul>
            <li>
              <button
                onClick={() => handleCategorySelect('All')}
                className={`cursor-pointer ${
                  selectedCategory === 'All' ? 'font-bold underline' : 'text-black hover:underline'
                }`}
              >
                All
              </button>
            </li>
            {categories.map((category) => (
              <li key={category}>
                <button
                  onClick={() => handleCategorySelect(category)}
                  className={`cursor-pointer ${
                    selectedCategory === category ? 'font-bold underline' : 'text-black hover:underline'
                  }`}
                >
                  {category}
                </button>
              </li>
            ))}
          </ul>
        </div>
      )}

      <div className="absolute top-100 mb-4 flex justify-between w-full">
        {/* Categories Button */}
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
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 12h16M4 18h16" />
          </svg>
          <span>Categories</span>
        </button>


        <div className="flex flex-1 items-center space-x-2 ml-20">
          <select
            onChange={handleSortChange}
            className="px-7 py-2 bg-gray-300 text-gray-700 font-medium rounded-lg hover:bg-gray-400 transition focus:outline-none focus:border-gray-500 focus:ring-0"
          >
            <option value="asc" className="text-black bg-gray-300">Price: Low to High</option>
            <option value="desc" className="text-black bg-gray-300">Price: High to Low</option>
          </select>


          <input
            type="text"
            placeholder="Search products"
            value={searchTerm}
            onChange={handleSearchChange}
            className="px-4 py-2 bg-gray-300 text-gray-700 font-medium rounded-lg hover:bg-gray-400 transition focus:border-gray-500 focus:ring-0"
          />
        </div>
      </div>


      <main className="container mx-auto px-4 py-8 flex-1 mt-16">
        {loadingCategories && <div>Loading categories...</div>}
        {loadingProducts && <div>Loading products...</div>}
        {error && <div className="text-red-500">{error}</div>}

        <div>
          <div
            className={`grid gap-6 mb-6 ${
              isSidebarOpen ? 'grid-cols-3 sm:grid-cols-3 md:grid-cols-3 lg:grid-cols-3' : 'grid-cols-4 sm:grid-cols-4 md:grid-cols-4 lg:grid-cols-4'
            }`}
          >
            {filteredAndSortedProducts.map((product) => (
              <CardDemo
                key={product.id}
                id={product.id}
                title={product.title}
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
        </div>
      </main>
    </div>
  );
};

export default ShopPage;
