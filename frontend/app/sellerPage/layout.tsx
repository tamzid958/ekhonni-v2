'use client';

import Link from "next/link";
import { usePathname } from "next/navigation";
import React from "react";

const SellerPageLayout = ({ children }: { children: React.ReactNode }) => {
  const pathname = usePathname();

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Seller Info Section */}
      <header className="bg-white shadow-md">
        <div className="container mx-auto px-4 py-6 flex justify-between items-center">
          <div className="flex items-center space-x-4">
            {/* Seller Logo */}
            <img
              src="/path-to-seller-logo.jpg" // Replace with actual logo path
              alt="Seller Logo"
              className="w-16 h-16 rounded-full object-cover"
            />

            {/* Seller Name */}
            <div>
              <h1 className="text-2xl font-bold text-gray-800">Seller Name</h1>
              <p className="text-sm text-gray-500">Verified Seller</p>
            </div>
          </div>

          {/* Action Buttons */}
          <div className="flex space-x-4">
            {/* Share Button */}
            <button className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">
              Share
            </button>

            {/* Chat Button */}
            <button className="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600">
              Chat
            </button>
          </div>
        </div>
      </header>

      {/* Navigation Menu */}
      <nav className="bg-gray-200 py-3">
        <div className="container mx-auto px-4 flex space-x-6">
          <Link href="/sellerPage/shop">
      <span
        className={`text-gray-700 font-medium hover:text-blue-600 ${
          pathname.includes("/shop") ? "text-blue-600" : ""
        }`}
      >
        Shop
      </span>
          </Link>
          <Link href="/sellerPage/about">
      <span
        className={`text-gray-700 font-medium hover:text-blue-600 ${
          pathname.includes("/about") ? "text-blue-600" : ""
        }`}
      >
        About
      </span>
          </Link>
          <Link href="/sellerPage/feedback">
      <span
        className={`text-gray-700 font-medium hover:text-blue-600 ${
          pathname.includes("/feedback") ? "text-blue-600" : ""
        }`}
      >
        Feedback
      </span>
          </Link>
        </div>
      </nav>


      {/* Content Section */}
      <main className="container mx-auto px-4 py-6">{children}</main>
    </div>
  );
};

export default SellerPageLayout;
