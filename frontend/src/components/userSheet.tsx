import { DialogTitle } from "@/components/ui/dialog";
import {
  ArrowBigDown, ChevronRight, Inbox, Info, List,
  MessageCircle, Settings, ShoppingBag, Wallet
} from "lucide-react";
import React from "react";
import { SheetContent } from "@/components/ui/sheet";
import { signOut, useSession } from "next-auth/react";
import useSWR from "swr";
import fetcher from "@/data/services/fetcher";
import Loading from '@/components/Loading';

interface UserDetail {
  profileImage: string | null;
  email: string;
  name: string;
  id: string;
  address: string;
}

const items = [
  { title: "Edit Profile", url: "/user-page/edit-profile", icon: Settings },
  { title: "About", url: "/user-page/user-about", icon: Info },
  { title: "Inbox", url: "/user-page/inbox", icon: Inbox },
  { title: "Feedback", url: "/user-page/feedback", icon: MessageCircle },
  { title: "Watchlist", url: "/user-page/watchlist", icon: List },
  { title: "Sell Product", url: "/form", icon: ArrowBigDown },
  { title: "My-cart", url: "/user-page/my-cart", icon: ShoppingBag },
  { title: "Account", url: "/user-page/account", icon: Wallet },
];

export function AppSidebar() {
  const { data: session } = useSession();
  const userID = session?.user?.id;
  const token = session?.user?.token;

  const url = userID ? `http://localhost:8080/api/v2/user/${userID}` : null;
  const { data, error, isLoading } = useSWR(url, (url) => fetcher(url, token));
  if (!userID) {
    return <div className="text-center text-red-500">User Not Found</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">Error: {error}</div>;
  }

  if (isLoading || !data) {
    return <div className="text-center text-gray-500"><Loading/></div>;
  }

  const userDetail: UserDetail | null = data?.data || null;

  return (
    <SheetContent side="right" className="w-64">
      <DialogTitle>Menu</DialogTitle>


      <div className="flex flex-col items-center p-2 border-b border-gray-300">
        <img
          src={userDetail.profileImage || "/default-avatar.png"}
          alt="User Avatar"
          className="w-12 h-12 rounded-full border mb-2"
        />
        <div className="text-center">
          <p className="text-sm font-medium">{userDetail.name || "Unknown User"}</p>
          <p className="text-sm text-gray-500">{userDetail.email || "No Email"}</p>
        </div>
      </div>



      {/* Sidebar Menu */}
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
