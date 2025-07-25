'use client';

import Link from 'next/link';
import React, { useEffect, useState } from 'react';
import { usePathname, useRouter } from 'next/navigation';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';

interface SellerProfile {
  profileImage: string | null;
  name: string;
  email: string;
  address: string;
}

const SellerPageLayout = ({ children }: { children: React.ReactNode }) => {
  const [sellerProfile, setSellerProfile] = useState<SellerProfile | null>(null);
  const [activeMenu, setActiveMenu] = useState<string>('');
  const pathname = usePathname();
  const router = useRouter();

  const sellerId = pathname.split('/')[2];

  useEffect(() => {
    if (sellerId) {
      fetch(`http://localhost:8080/api/v2/user/${sellerId}`)
        .then((res) => res.json())
        .then((data) => setSellerProfile(data.data))
        .catch((err) => console.error('Error fetching seller data:', err));
    }
  }, [sellerId]);

  useEffect(() => {
    if (pathname === `/sellerPage/${sellerId}`) {
      router.replace(`/sellerPage/${sellerId}/shop`);
    }
  }, [pathname, router, sellerId]);

  useEffect(() => {
    if (pathname.includes('/shop')) {
      setActiveMenu('/shop');
    } else if (pathname.includes('/feedback')) {
      setActiveMenu('/feedback');
    }
  }, [pathname]);

  const shareUrl = `${window.location.origin}/sellerPage/${sellerId}`;


  return (
    <div className="min-h-screen min-w-screen bg-gray-100">
      <header className="bg-white shadow-md">
        <div className="container mx-auto px-4 py-6 flex justify-between items-center">
          <div className="flex items-center space-x-4">
            {sellerProfile?.profileImage ? (
              <img
                src={sellerProfile.profileImage}
                alt={`${sellerProfile.name}'s profile`}
                className="w-16 h-16 rounded-full object-cover"
              />
            ) : (
              <div className="w-16 h-16 rounded-full bg-gray-300 flex items-center justify-center">
      <span className="text-white text-lg font-semibold">
        {sellerProfile?.name?.charAt(0).toUpperCase()}
      </span>
              </div>
            )}
            <div>
              <h1 className="text-2xl font-bold text-gray-800">
                {sellerProfile?.name || 'Loading...'}
              </h1>
              <p className="text-sm text-gray-500">{sellerProfile?.email || 'Loading...'}</p>
              <p className="text-sm text-gray-500">{sellerProfile?.address || 'Loading...'}</p>
            </div>
          </div>

          <div className="flex space-x-4">
            <Dialog>
              <DialogTrigger asChild>
                <button className="bg-brand-dark text-white px-4 py-2 rounded-md hover:bg-brand-mid">
                  Share
                </button>
              </DialogTrigger>
              <DialogContent>
                <DialogHeader>
                  <DialogTitle>Share Link</DialogTitle>
                  <DialogDescription>
                    Anyone with this link can view the profile. Copy and share it.
                  </DialogDescription>
                </DialogHeader>
                <div className="mt-4 flex items-center space-x-2">
                  <input
                    type="text"
                    value={shareUrl}
                    readOnly
                    className="border border-gray-300 rounded-md px-2 py-1 w-full"
                  />
                  <button
                    onClick={() => navigator.clipboard.writeText(shareUrl)}
                    className="bg-gray-200 hover:bg-gray-300 px-3 py-1 rounded-md"
                  >
                    Copy
                  </button>
                </div>
              </DialogContent>
            </Dialog>

            <button className="bg-green-300 text-white px-4 py-2 rounded-md hover:bg-green-400">
              Chat
            </button>
          </div>
        </div>
      </header>

      <nav className="bg-gray-200 py-3">
        <div className="container mx-auto px-4 flex space-x-6">
          <Link href={`/seller-page/${sellerId}/shop`}>
            <span
              className={`text-gray-700 font-medium hover:text-black ${
                activeMenu === '/shop' ? 'text-black font-bold border-b-2 border-black' : ''
              }`}
            >
              Shop
            </span>
          </Link>

          <Link href={`/seller-page/${sellerId}/feedback`}>
            <span
              className={`text-gray-700 font-medium hover:text-black ${
                activeMenu === '/feedback' ? 'text-black font-bold border-b-2 border-black' : ''
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
