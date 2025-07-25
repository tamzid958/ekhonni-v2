import React, { useEffect, useState } from 'react';
import { usePathname, useRouter } from 'next/navigation';
import { ArrowBigDown, Inbox, Info, List, LogOut, MessageCircle, Settings, ShoppingBag, X } from 'lucide-react';
import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from '@/components/ui/sidebar';

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
} from '@/components/ui/alert-dialog';
import { logout } from '../../app/auth/logout/page';

const items = [
  { title: 'Edit Profile', url: '/user-page/edit-profile', icon: Settings },
  { title: 'About', url: '/user-page/user-about', icon: Info },
  { title: 'Inbox', url: '/user-page/inbox', icon: Inbox },
  { title: 'Feedback', url: '/user-page/feedback', icon: MessageCircle },
  { title: 'Watchlist', url: '/user-page/watchlist', icon: List },
  { title: 'Shop', url: '/user-page/shop', icon: ShoppingBag },
  { title: 'Sell Product', url: '/form', icon: ArrowBigDown },
];

export function AppSidebar() {
  const router = useRouter();
  const pathname = usePathname();
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [activeButton, setActiveButton] = useState(pathname);

  useEffect(() => {
    setActiveButton(pathname); // Sync activeButton with pathname when the component loads
  }, [pathname]);

  const handleClose = () => {
    setIsSidebarOpen(false);
  };

  const handleCancelButton = () => {
    setIsSidebarOpen(true);
  };

  const handleLogout = () => {
    console.log('Logging out...');
    setIsSidebarOpen(false);
    setTimeout(() => {
      logout();
    }, 200);
  };

  const handleButtonClick = (url) => {
    setActiveButton(url);
    router.push(url);
  };

  return (
    <>
      {isSidebarOpen && (
        <Sidebar side="right" variant="sidebar" className="fixed right-0 top-0 z-50">
          <SidebarContent>
            <div className="flex justify-end">
              <button
                onClick={handleClose}
                className="p-4 text-black-500 font-bold hover:text-red-700"
              >
                <X className="w-6 h-6" />
              </button>
            </div>

            <SidebarGroup className="mt-0" style={{ marginTop: '-35px' }}>
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
                          activeButton === item.url
                            ? 'bg-brand-dark text-white rounded w-full'
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
                      <SidebarMenuButton onClick={() => setActiveButton('logout')}>
                        <SidebarMenuItem
                          className={`flex items-center space-x-2 pt-1 ${
                            activeButton === 'logout'
                              ? 'bg-brand-dark text-white rounded w-full'
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
