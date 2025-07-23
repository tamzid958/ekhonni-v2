'use client';
import useSWR from 'swr';
import {
  NavigationMenu,
  NavigationMenuItem,
  NavigationMenuList,
  NavigationMenuTrigger,
} from '@/components/ui/navigation-menu';
import React from 'react';
import { ChevronsRight } from 'lucide-react';
import Link from 'next/link';

const fetcher = async (url) => {
  const res = await fetch(url);
  if (!res.ok) throw new Error('Failed to fetch');
  return res.json();
};

export function TopCAtegory() {
  const { data, error } = useSWR(
    'http://localhost:8080/api/v2/category/all',
    fetcher,
    { suspense: false },
  );

  if (error) return <div className="text-red-500">Failed to load categories</div>;
  if (!data) return <div className="bg-brand-mid relative h-10 flex justify-center items-center">
    <span className="text-gray-600 text-sm">Loading categories...</span>
  </div>;

  return (
    <div className="bg-brand-mid relative h-10 flex justify-center">
      <NavigationMenu className="w-full max-w-5xl">
        <NavigationMenuList className="flex justify-center space-x-1">
          {data.data.slice(0, 10).map((category) => (
            <NavigationMenuItem key={category.name} className="relative group">
              <NavigationMenuTrigger
                className="peer flex items-center text-xs px-2 font-semibold text-gray-700 border-transparent hover:underline">
                <Link
                  href={`/categoryProducts?category=${encodeURIComponent(category.name)}`}
                  passHref
                  className="">
                  {category.name}
                </Link>
              </NavigationMenuTrigger>

              <div
                className="absolute left-0 top-full bg-brand-bright shadow-lg border rounded-md opacity-0 invisible peer-hover:opacity-100 peer-hover:visible hover:opacity-100 hover:visible transition-opacity duration-200 pointer-events-none peer-hover:pointer-events-auto hover:pointer-events-auto">
                <ul className="text-xs font-semibold grid m-2 md:w-[200px] lg:w-[300px]">
                  {category.subCategories.map((sub, index) => (
                    <li
                      key={index}
                      className="flex items-center p-2 rounded-md hover:bg-brand-mid group">
                      <Link
                        href={`/categoryProducts?category=${encodeURIComponent(sub)}`}
                        passHref
                        className="flex items-center w-full">
                        <ChevronsRight
                          className="mr-2 opacity-0 hover:opacity-100 transition-opacity duration-200"
                          size={16}
                        />
                        <span>{sub}</span>
                      </Link>
                    </li>
                  ))}
                </ul>
              </div>
            </NavigationMenuItem>
          ))}
        </NavigationMenuList>
      </NavigationMenu>
    </div>
  );
}