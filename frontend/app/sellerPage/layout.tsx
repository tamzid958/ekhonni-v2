'use client';

import React, { useState, useEffect } from "react";
import { useRouter, usePathname } from "next/navigation";
import SellerCategoryList from '@/components/SellerCategoryList';
import SellerProductCard from '@/components/SellerProductCard';
import Link from 'next/link';

type Product = {
  id: string;
  name: string;
  status: string;
  image: string;
};

const SellerPageLayout = ({ children }: { children: React.ReactNode }) => {
  const [activeMenu, setActiveMenu] = useState<string>("");
  const [isSidebarOpen, setIsSidebarOpen] = useState<boolean>(false);
  const [selectedCategory, setSelectedCategory] = useState<string>("All");
  const [categories, setCategories] = useState<string[]>(["Antiques", "Travel & Nature"]);
  const [products, setProducts] = useState<Product[]>([]);
  const pathname = usePathname();
  const userId = "d05a34b5-640e-4b2a-9f6b-4f168768d7cd";
  const categoryUrl = `http://192.168.68.164:8080/api/v2/category/all/${userId}`;

  // Fetch categories from the API
  const fetchCategories = async () => {
    try {
      const res = await fetch(categoryUrl);
      const data = await res.json();
      if (data?.data?.length) {
        setCategories(data.data);
      }
    } catch (error) {
      console.error("Failed to fetch categories:", error);
    }
  };

  // Fetch products based on the selected category
  const fetchProducts = async (categoryId: string) => {
    try {
      const res = await fetch(`/api/sellerProduct?categoryId=${categoryId}`);
      const data = await res.json();
      setProducts(data.data || []);
    } catch (error) {
      console.error("Failed to fetch products:", error);
      setProducts([]);
    }
  };

  // Handle category selection
  const handleCategorySelect = (categoryId: string) => {
    setSelectedCategory(categoryId);
    fetchProducts(categoryId);
    setActiveMenu("shop");
  };

  // Set active menu on pathname change
  useEffect(() => {
    if (pathname.includes("/shop")) {
      setActiveMenu("shop");
    } else if (pathname.includes("/about")) {
      setActiveMenu("about");
    } else if (pathname.includes("/feedback")) {
      setActiveMenu("feedback");
    }
  }, [pathname]);

  // Fetch categories and default products on mount
  useEffect(() => {
    fetchCategories();
    fetchProducts("Antiques"); // Fetch products for the first default category
  }, []);

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow-md">
        <div className="container mx-auto px-4 py-6 flex justify-between items-center">
          <div className="flex items-center space-x-4">
            <img
              src="/tracker.webp"
              alt="Seller Logo"
              className="w-16 h-16 rounded-full object-cover"
            />
            <div>
              <h1 className="text-2xl font-bold text-gray-800">Electronics Media</h1>
              <p className="text-sm text-gray-500">Verified Seller</p>
            </div>
          </div>
          <div className="flex space-x-4">
            <button className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">Share</button>
            <button className="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600">Chat</button>
          </div>
        </div>
      </header>

      {/* Navbar */}
      <nav className="bg-gray-200 py-6">
        <div className="container mx-auto flex justify-between items-center px-4">
          <div className="flex items-center space-x-4">
            {/* Categories Button */}
            <button
              onClick={() => setIsSidebarOpen(!isSidebarOpen)}
              className={`flex items-center space-x-2 bg-gray-300 text-gray-700 font-medium px-4 py-2 rounded-lg hover:bg-gray-400 transition`}
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

            <div className="flex space-x-6">
              {/* Shop Link */}
              <Link href="/sellerPage/shop">
                <span
                  onClick={() => setActiveMenu("shop")}
                  className={`text-gray-700 font-medium hover:text-black ${
                    activeMenu === "shop" ? "text-black font-bold border-b-2 border-black" : ""
                  }`}
                >
                  Shop
                </span>
              </Link>

              {/* About Link */}
              <Link href="/sellerPage/about">
                <span
                  onClick={() => setActiveMenu("about")}
                  className={`text-gray-700 font-medium hover:text-black ${
                    activeMenu === "about" ? "text-black font-bold border-b-2 border-black" : ""
                  }`}
                >
                  About
                </span>
              </Link>

              {/* Feedback Link */}
              <Link href="/sellerPage/feedback">
                <span
                  onClick={() => setActiveMenu("feedback")}
                  className={`text-gray-700 font-medium hover:text-black ${
                    activeMenu === "feedback" ? "text-black font-bold border-b-2 border-black" : ""
                  }`}
                >
                  Feedback
                </span>
              </Link>
            </div>
          </div>
        </div>
      </nav>

      {/* Main Content with Sidebar */}
      <main className="container mx-auto px-4 py-6 flex">
        {/* Sidebar */}
        {isSidebarOpen && activeMenu === "shop" && (
          <div className="w-64 bg-gray-800 text-white p-4 mr-6 min-h-screen">
            <h2 className="text-xl font-bold mb-4">Categories</h2>
            <SellerCategoryList
              categories={categories}
              onCategorySelect={handleCategorySelect}
            />

          </div>
        )}
        <div className="flex-1">
          {isSidebarOpen && selectedCategory !== "All" ? (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-6">
              {products.map((product) => (
                <SellerProductCard key={product.id} product={product} />
              ))}
            </div>
          ) : (
            children
          )}
        </div>
      </main>
    </div>
  );
};

export default SellerPageLayout;
