'use client';

import Link from "next/link";
import { useState, useEffect } from "react";
import { usePathname, useRouter } from "next/navigation";
import React from "react";
import { useSession } from 'next-auth/react';

const SellerPageLayout = ({ children }: { children: React.ReactNode }) => {
  const [activeMenu, setActiveMenu] = useState<string>("");
  const [profile, setProfile] = useState<any>(null);  // State to hold profile data
  const pathname = usePathname();
  const router = useRouter();
  const { data: session } = useSession(); // Get logged-in user session

  // Extract the userId from the URL (e.g., /sellerPage/{userId}/shop)
  const userId = pathname.split('/')[2];  // Get userId from the URL (position 2 in /sellerPage/{userId}/shop)

  // Fetch user profile info when the component mounts
  useEffect(() => {
    if (userId) {
      const fetchProfile = async () => {
        try {
          const response = await fetch(`http://localhost:8080/api/v2/user/550e8400-e29b-41d4-a716-446655440005`, {
            method: 'GET',
            headers: {
              'Content-Type': 'application/json',
            },
          });

          if (!response.ok) {
            throw new Error('Failed to fetch profile');
          }

          const data = await response.json();
          setProfile(data);  // Set profile data in state
        } catch (error) {
          console.error('Error fetching profile:', error);
        }
      };

      fetchProfile();
    }
  }, [userId]);

  useEffect(() => {
    if (pathname === "/sellerPage") {
      router.replace("/sellerPage/shop");
    }
  }, [pathname, router]);

  useEffect(() => {
    if (pathname.includes("/shop")) {
      setActiveMenu("/shop");
    } else if (pathname.includes("/feedback")) {
      setActiveMenu("/feedback");
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
              src={profile?.profileImage || "/placeholder.jpg"}  // Display profile image or a placeholder
              alt="User Profile"
              className="w-16 h-16 rounded-full object-cover"
            />
            <div>
              <h1 className="text-2xl font-bold text-gray-800">{profile?.name || 'Loading...'}</h1>
              <p className="text-sm text-gray-500">{profile?.email || 'Loading email...'}</p>
              <p className="text-sm text-gray-500">{profile?.address || 'Loading address...'}</p>
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
          <Link href={`/sellerPage/${userId}/shop`}>
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

          <Link href={`/sellerPage/${userId}/feedback`}>
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
