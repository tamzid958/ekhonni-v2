import { DialogTitle } from "@/components/ui/dialog"; // Import DialogTitle
import { ArrowBigDown, ChevronRight, Inbox, Info, List, MessageCircle, Settings, ShoppingBag } from "lucide-react";
import React from "react";
import { SheetContent } from "@/components/ui/sheet";
import { signOut } from "next-auth/react";

const items = [
  { title: "Edit Profile", url: "/user-page/edit-profile", icon: Settings },
  { title: "About", url: "/user-page/user-about", icon: Info },
  { title: "Inbox", url: "/user-page/inbox", icon: Inbox },
  { title: "Feedback", url: "/user-page/feedback", icon: MessageCircle },
  { title: "Watchlist", url: "/user-page/watchlist", icon: List },
  { title: "Sell Product", url: "/form", icon: ArrowBigDown },
  { title: "My-cart", url: "/user-page/my-cart", icon: ShoppingBag },
];

export function AppSidebar() {
  return (
    <SheetContent side="right" className="w-64">
      <DialogTitle>Menu</DialogTitle>
      <div className="p-4">
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
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              signOut({ callbackUrl: "/" });
            }}
            className="flex items-center space-x-3 p-2 mt-4 rounded-md hover:bg-gray-100 dark:hover:bg-gray-800"
          >
            <ChevronRight className="w-5 h-5" />
            <span>Log Out</span>
          </a>
        </nav>
      </div>
    </SheetContent>
  );
}
