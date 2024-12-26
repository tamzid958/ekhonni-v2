'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import {
  X,
  LogOut,
  ShoppingBag,
  Inbox,
  Settings,
  Info,
  List,
  MessageCircle,
} from "lucide-react";
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";

import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";

const items = [
  { title: "Edit Profile", url: "buyerProfile", icon: Settings },
  { title: "About", url: "#", icon: Info },
  { title: "Inbox", url: "#", icon: Inbox },
  { title: "Feedback", url: "#", icon: MessageCircle },
  { title: "Watchlist", url: "#", icon: List },
  { title: "Shop", url: "#", icon: ShoppingBag },
];

export function AppSidebar() {
  const router = useRouter();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const handleClose = () => {
    setIsSidebarOpen(false);
    router.push('/');
  };

  const handleCancelButton = () => {
    setIsSidebarOpen(true);
  };

  const handleLogout = () => {
    console.log("Logging out...");
    setIsSidebarOpen(false);
    router.push('/');
  };

  return (
    <>
      {isSidebarOpen && (
        <Sidebar side="right" variant="floating">
          <SidebarContent>
            {/* Close Button */}
            <button
              onClick={handleClose}
              className=" justify-end p-4 text-black-500 font-bold hover:text-red-700"
            >
              <X className="w-6 h-6" />
            </button>

            {/* Sidebar Menu */}
            <SidebarGroup className="mt-0">
              <SidebarGroupLabel className="pt-2">Menu</SidebarGroupLabel>
              <SidebarGroupContent className="font-bold">
                <SidebarMenu>
                  {items.map((item) => (
                    <SidebarMenuButton key={item.title} onClick={() => router.push(item.url)}>
                      <SidebarMenuItem
                        href={item.url}
                        className="flex items-center space-x-2 pt-1"
                      >
                        <item.icon className="w-6 h-6" />
                        <span>{item.title}</span>
                      </SidebarMenuItem>
                    </SidebarMenuButton>
                  ))}

                  {/* Log Out Option with AlertDialog */}
                  <AlertDialog>
                    <AlertDialogTrigger asChild>
                      <SidebarMenuButton>
                        <SidebarMenuItem className="flex items-center space-x-2 pt-1">
                          <LogOut className="w-6 h-6" />
                          <span>Log Out</span>
                        </SidebarMenuItem>
                      </SidebarMenuButton>
                    </AlertDialogTrigger>
                    <AlertDialogContent>
                      <AlertDialogHeader>
                        <AlertDialogTitle>Are you sure you want to log out?</AlertDialogTitle>
                        <AlertDialogDescription>
                          Logging out will end your session and require you to log back in.
                        </AlertDialogDescription>
                      </AlertDialogHeader>
                      <AlertDialogFooter>
                        <AlertDialogCancel onClick={handleCancelButton}>Cancel</AlertDialogCancel>
                        <AlertDialogAction onClick={handleLogout}>Log Out</AlertDialogAction>
                      </AlertDialogFooter>
                    </AlertDialogContent>
                  </AlertDialog>
                </SidebarMenu>
              </SidebarGroupContent>
            </SidebarGroup>
          </SidebarContent>
        </Sidebar>
      )}
    </>
  );
}
