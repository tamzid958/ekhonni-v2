'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import { usePathname } from 'next/navigation';
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
  { title: "Edit Profile", url: "/editProfile", icon: Settings },
  { title: "About", url: "/userAbout", icon: Info },
  { title: "Inbox", url: "/inbox", icon: Inbox },
  { title: "Feedback", url: "/feedback", icon: MessageCircle },
  { title: "Watchlist", url: "/watchlist", icon: List },
  { title: "Shop", url: "/shop", icon: ShoppingBag },
];

export function AppSidebar() {
  const router = useRouter();
  const pathname = usePathname();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [activeButton, setActiveButton] = useState(""); // Track active button

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

  const handleButtonClick = (url) => {
    setActiveButton(url);
    router.push(url);
  };

  return (
    <>
      {isSidebarOpen && (
        <Sidebar side="right" variant="floating">
          <SidebarContent>
            <button
              onClick={handleClose}
              className="justify-end p-4 text-black-500 font-bold hover:text-red-700"
            >
              <X className="w-6 h-6" />
            </button>

            <SidebarGroup className="mt-0">
              <SidebarGroupLabel className="pt-2">Menu</SidebarGroupLabel>
              <SidebarGroupContent className="font-bold">
                <SidebarMenu>
                  {items.map((item) => (
                    <SidebarMenuButton
                      key={item.title}
                      onClick={() => handleButtonClick(item.url)}
                    >
                      <SidebarMenuItem
                        className={`flex items-center space-x-2 pt-1 ${
                          pathname === item.url || activeButton === item.url
                            ? 'bg-blue-500 text-white rounded w-full'
                            : 'text-black-700 hover:bg-black-200'
                        }`}
                      >
                        <item.icon className="w-6 h-6" />
                        <span>{item.title}</span>
                      </SidebarMenuItem>
                    </SidebarMenuButton>
                  ))}

                  <AlertDialog>
                    <AlertDialogTrigger asChild>
                      <SidebarMenuButton onClick={() => setActiveButton("logout")}>
                        <SidebarMenuItem
                          className={`flex items-center space-x-2 pt-1 ${
                            activeButton === "logout"
                              ? 'bg-blue-500 text-white rounded w-full'
                              : 'text-black-700 hover:bg-black-200'
                          }`}
                        >
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
                        <AlertDialogCancel onClick={handleCancelButton}>
                          Cancel
                        </AlertDialogCancel>
                        <AlertDialogAction onClick={handleLogout}>
                          Log Out
                        </AlertDialogAction>
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
