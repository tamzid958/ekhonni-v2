// components/NavBar.tsx
'use client';
import { Button } from './ui/button';
import { Bell, Search, ShoppingCart, User } from 'lucide-react';
import React, { useState } from 'react';
import { SidebarProvider } from '@/components/ui/sidebar';
import { AppSidebar } from '@/components/Sidebar';
import Link from 'next/link';
import { Select, SelectContent, SelectGroup, SelectTrigger } from '@/components/ui/select';
import { cn } from '@/lib/utils';


type Props = {
  placeholder?: string;
};

const notifications = [
  {
    text: 'System Update Available asssssssss sssssssssssssssssssssssssssssssss ssssssssssssssssssssss ssssssssssssssssssssss ssssssssssss ssssssssssss ssssss',
    link: 'http://localhost:3000/update',
  },
  {
    text: 'gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji ',
    link: 'http://localhost:3000/form',
  },
  { text: 'Meeting Reminder: 2 PM', link: 'http://localhost:3000/reminder' },
  { text: 'Exclusive Offer: 20% Off', link: 'http://localhost:3000/offer' },
  { text: 'Security Alert: Unusual Login Attempt', link: 'http://localhost:3000/alert' },
  {
    text: 'System Update Available asssssssss sssssssssssssssssssssssssssssssss ssssssssssssssssssssss ssssssssssssssssssssss ssssssssssss ssssssssssss ssssss',
    link: 'http://localhost:3000/update',
  },
  {
    text: 'gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji ',
    link: 'http://localhost:3000/form',
  },
  { text: 'Meeting Reminder: 2 PM', link: 'http://localhost:3000/reminder' },
  { text: 'Exclusive Offer: 20% Off', link: 'http://localhost:3000/offer' },
  { text: 'Security Alert: Unusual Login Attempt', link: 'http://localhost:3000/alert' },
  {
    text: 'System Update Available asssssssss sssssssssssssssssssssssssssssssss ssssssssssssssssssssss ssssssssssssssssssssss ssssssssssss ssssssssssss ssssss',
    link: 'http://localhost:3000/update',
  },
  {
    text: 'gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji gujibugji gujibuji ',
    link: 'http://localhost:3000/form',
  },
  { text: 'Meeting Reminder: 2 PM', link: 'http://localhost:3000/reminder' },
  { text: 'Exclusive Offer: 20% Off', link: 'http://localhost:3000/offer' },
  { text: 'Security Alert: Unusual Login Attempt', link: 'http://localhost:3000/alert' },
];


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
        </Link>
      </div>

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
              <Link href="/search">
                <Search className="text-muted-foreground" size={18} />
              </Link>
              {/*<span className="sr-only">Search Button</span>*/}
            </Button>
          </div>
        </div>
      </div>
      <div className="flex gap-4 mr-28 mt-4">
        <Link href="/cart">
          <Button variant="custom" size="icon2" className="rounded-full">
            <ShoppingCart />
          </Button>
        </Link>
        <Select>
          <SelectTrigger
            className="text-primary bg-brand-mid hover:bg-brand-light h-12 w-12 px-3 rounded-full focus:ring-0 focus:outline-none active:ring-0 active:outline-none focus-visible:ring-0 focus-visible:outline-none ring-0 [&_svg.h-4]:hidden">
            <Bell className="w-5 h-5" />
          </SelectTrigger>
          <SelectContent className="bg-brand-bright right-12 w-96">
            <SelectGroup>
              <p className="text-xm font-bold p-2 justify-center flex">NOTIFICATION</p>
              <div
                className="max-h-80 overflow-y-auto space-y-1 scrollbar-thin scrollbar-thumb-brand-dark scrollbar-track-brand-mid">
                {notifications.map((item, index) => (
                  <Link key={index} href={item.link}>
                    <div
                      className="overflow-hidden max-w-92 m-2 px-4 py-2 rounded-lg transition-all duration-300 bg-brand-mid hover:bg-brand-dark hover:text-white cursor-pointer">
                      {item.text}
                    </div>
                  </Link>
                ))}
              </div>
            </SelectGroup>
          </SelectContent>
        </Select>

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
