'use client';

import Link from "next/link";
import { useState, useEffect } from "react";
import { usePathname, useRouter } from "next/navigation";
import React from "react";

const SellerPageLayout = ({ children }: { children: React.ReactNode }) => {
  const [activeMenu, setActiveMenu] = useState<string>("");
  const pathname = usePathname();
  const router = useRouter();


  useEffect(() => {
    if (pathname === "/sellerPage") {
      router.replace("/sellerPage/shop");
    }
  }, [pathname, router]);


  useEffect(() => {
    if (pathname.includes("/shop")) {
      setActiveMenu("shop");
    } else if (pathname.includes("/about")) {
      setActiveMenu("about");
    } else if (pathname.includes("/feedback")) {
      setActiveMenu("feedback");
    }
  }, [pathname]);

  const handleMenuClick = (menu: string) => {
    setActiveMenu(menu);
  };

  return (
    <div className="min-h-screen bg-gray-100">
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
            <button className="bg-blue-500 text-white px-4 py-2 rounded-md hover:bg-blue-600">
              Share
            </button>
            <button className="bg-green-500 text-white px-4 py-2 rounded-md hover:bg-green-600">
              Chat
            </button>
          </div>
        </div>
      </header>

      <nav className="bg-gray-200 py-3">
        <div className="container mx-auto px-4 flex space-x-6">
          <Link href="/sellerPage/shop">
            <span
              onClick={() => handleMenuClick("shop")}
              className={`text-gray-700 font-medium hover:text-black ${
                activeMenu === "shop"
                  ? "text-black font-bold border-b-2 border-black"
                  : ""
              }`}
            >
              Shop
            </span>
          </Link>
          <Link href="/sellerPage/about">
            <span
              onClick={() => handleMenuClick("about")}
              className={`text-gray-700 font-medium hover:text-black ${
                activeMenu === "about"
                  ? "text-black font-bold border-b-2 border-black"
                  : ""
              }`}
            >
              About
            </span>
          </Link>
          <Link href="/sellerPage/feedback">
            <span
              onClick={() => handleMenuClick("feedback")}
              className={`text-gray-700 font-medium hover:text-black ${
                activeMenu === "feedback"
                  ? "text-black font-bold border-b-2 border-black"
                  : ""
              }`}
            >
              Feedback
            </span>
          </Link>
        </div>
      </nav>


      <main className="container mx-auto px-4 py-6">{children}</main>
    </div>
  );
};

export default SellerPageLayout;
