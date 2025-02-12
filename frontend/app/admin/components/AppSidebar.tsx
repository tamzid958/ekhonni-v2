import { Boxes, ChevronRight, CreditCard, Home, Package, Settings, ShieldCheck, Users } from 'lucide-react';

import React from 'react';
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet';

// Menu items.
const items = [
  {
    title: 'Home',
    url: '/admin',
    icon: Home,
  },
  {
    title: 'Categories',
    url: '/admin/categories',
    icon: Boxes,
  },
  {
    title: 'Products',
    url: '/admin/products',
    icon: Package,
  },
  {
    title: 'Users',
    url: '/admin/user',
    icon: Users,
  },
  {
    title: 'Roles',
    url: '/admin/roles',
    icon: ShieldCheck,
  },
  {
    title: 'Transactions',
    url: '/admin/transactions',
    icon: CreditCard,
  },
  {
    title: 'Settings',
    url: '#',
    icon: Settings,
  },
];

export function AppSidebar() {
  return (
    <Sheet>
      {/* Floating Expand Button (Tall Vertical Strip) */}
      <SheetTrigger asChild>
        <div
          className="fixed left-0 top-1/2 -translate-y-1/2 z-50 w-10 h-20 bg-white dark:bg-gray-900 rounded-r-full shadow-lg flex items-center justify-center cursor-pointer"
        >
          <ChevronRight className="w-6 h-6 text-gray-700 dark:text-gray-300" />
        </div>
      </SheetTrigger>

      {/* Sidebar Content inside Sheet */}
      <SheetContent side="left" className="w-64">
        <div className="p-4">
          <h2 className="text-lg font-semibold mb-4">Admin Panel</h2>
          <nav className="space-y-2">
            {items.map((item) => (
              <a
                key={item.title}
                href={item.url}
                className="flex items-center space-x-3 p-2 rounded-md hover:bg-gray-100 dark:hover:bg-gray-800"
              >
                <item.icon className="w-5 h-5" />
                <span>{item.title}</span>
              </a>
            ))}
          </nav>
        </div>
      </SheetContent>
    </Sheet>
  );
}