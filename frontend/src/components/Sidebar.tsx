'use client'; // Necessary for client-side routing in Next.js

import React from 'react';
import { useRouter } from 'next/navigation'; // For navigating in Next.js
import { Calendar, Home, Inbox, Search, Settings, X } from "lucide-react";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar"; // Import Sidebar components

const items = [
  { title: "Home", url: "#", icon: Home },
  { title: "Inbox", url: "#", icon: Inbox },
  { title: "Search", url: "#", icon: Search },
  { title: "Calendar", url: "#", icon: Calendar },
  { title: "Settings", url: "#", icon: Settings },
];

export function AppSidebar() {
  const router = useRouter(); // Router instance for navigation

  const handleClose = () => {
    router.back();
  };

  return (
    <Sidebar side="right" variant="floating">
      <SidebarContent>
        <button
          onClick={handleClose}
          className=" justify-end p-4 text-black-500 font-bold hover:text-red-700">
          <X className="w-6 h-6" />
        </button>
        <SidebarGroup className="mt-0">
          <SidebarGroupLabel className="pt-2">Menu</SidebarGroupLabel>
          <SidebarGroupContent className="font-bold">
            <SidebarMenu>
              {items.map((item) => (
                <SidebarMenuButton key={item.title}>
                  <SidebarMenuItem
                    href={item.url}
                    className="flex items-center space-x-2 pt-1"
                  >
                    <item.icon className="w-6 h-6" />
                    <span>{item.title}</span>
                  </SidebarMenuItem>
                </SidebarMenuButton>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}
