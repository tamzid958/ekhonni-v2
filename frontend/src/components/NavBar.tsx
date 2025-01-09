// components/NavBar.tsx
'use client';
import { Button } from './ui/button';
import { Bell, Search, ShoppingCart, User } from 'lucide-react';
import React, { useState } from 'react';
import { SidebarProvider } from '@/components/ui/sidebar';
import { AppSidebar } from '@/components/Sidebar';
import Link from 'next/link';
import { cn } from '@/lib/utils';


type Props = {
  placeholder?: string;
};

export function NavBar({ placeholder }: Props) {
  const [isSidebarOpen, setSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setSidebarOpen(!isSidebarOpen);
  };

  return (
    <nav className="flex justify-between p-4 text-2xl bg-brand-dark h-[120px]">
      <div className="font-bold ml-16 mt-2">
        <Link href="/">
          <img src="frame.png" alt="logo" className="h-[75px]" />
        </Link></div>
      <div className="w-[680px] flex justify-center items-center">
        <div className="w-full relative">
          <input
            type="text"
            placeholder={placeholder}
            className={cn(
              'flex h-12 w-full rounded-md border border-input bg-background py-2 px-4 text-xl ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus:outline-none focus-visible:outline-none focus-visible:ring-0 focus-visible:ring-offset-0 disabled:cursor-not-allowed disabled:opacity-50',
              'pr-6',
            )}
          />
          <div className="absolute left-[92%] top-1/2 transform -translate-y-1/2">
            <Button variant="custom2" size="customSize" className="w-[50%] h-[95%] rounded-xl">
              <Search className="text-muted-foreground" size={18} />
              <span className="sr-only">Search Button</span>
            </Button>
          </div>
        </div>
      </div>
      <div className="flex gap-4 mr-28 mt-4">
        <Link href="/watchlist">
          <Button variant="custom" size="icon2" className="rounded-full">
            <ShoppingCart />
          </Button>
        </Link>
        
        <Button variant="custom" size="icon2" className="rounded-full"><Bell /></Button>

        <SidebarProvider>
          <Button variant="custom" size="icon2" className="rounded-full" onClick={toggleSidebar}>
            <User />
          </Button>
          {isSidebarOpen && <AppSidebar />}
        </SidebarProvider>

      </div>

      {/* Trigger Sidebar */}
    </nav>
  );
}
