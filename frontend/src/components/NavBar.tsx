'use client';
import { Button } from './ui/button';
import { Bell, Search, ShoppingCart, User } from 'lucide-react';
import React, { useEffect, useRef, useState } from 'react';
import { SidebarProvider } from '@/components/ui/sidebar';
import { AppSidebar } from '@/components/Sidebar';
import Link from 'next/link';
import { Select, SelectContent, SelectGroup, SelectTrigger } from '@/components/ui/select';
import { cn } from '@/lib/utils';
import { NotificationGetter } from '@/components/Notification';
import { useSession } from 'next-auth/react';
import { Popover, PopoverTrigger, PopoverContent } from '@/components/ui/popover';
import { useRouter } from 'next/navigation';


type Props = {
  placeholder?: string;
};

type Notification = {
  id: number;
  message: string;
  lastFetchTime?: string;
  createdAt: string;
  type: string;
  redirectUrl: string;
};

export function NavBar({ placeholder }: Props) {
  const [isSidebarOpen, setSidebarOpen] = useState(false);
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const { data: session, status } = useSession();
  const [query, setQuery] = useState('');
  const linkRef = useRef<HTMLAnchorElement | null>(null);
  const router = useRouter(); // Access the router


  const handleLoginRedirect = () => {
    router.push('/'); // Redirect to home page
  };


  useEffect(() => {
    console.log('Session Data:', session);
    if (!session) return;
    const userId = session?.user?.id;
    const userToken = session?.user?.token;
    const lastFetchTime = new Date(new Date().setDate(new Date().getDate() - 2)).toISOString().split('.')[0];

    async function fetchNotifications(lastFetchTime: string) {
      console.log('Fetching notifications for user:', userId);
      const result = await NotificationGetter(userId, userToken, lastFetchTime);


      if (result.success) {
        if (Array.isArray(result.data)) {
          const newNotifications = result.data
            .filter((item) => item.message.trim().length > 0)
            .filter((item) => !notifications.some((notification) => notification.id === item.id));
          setNotifications((prevNotifications) => {
            const updatedNotifications = [...prevNotifications, ...newNotifications];
            const seenIds = new Set();
            const filteredNotifications = updatedNotifications.filter((notification) => {
              if (seenIds.has(notification.id)) {
                return false;
              }
              seenIds.add(notification.id);
              return true;
            });
            return filteredNotifications;
          });
        }
        fetchNotifications(result.lastFetchTime);
      } else {
        console.error(result.message);
      }
    }

    fetchNotifications(lastFetchTime);
  }, [session]);
  const toggleSidebar = () => {
    setSidebarOpen(!isSidebarOpen);
  };


  return (
    <nav className="flex justify-between p-4 text-2xl bg-brand-dark h-[120px] relative z-40">
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
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            className={cn(
              'flex h-12 w-full rounded-md border border-input bg-background py-2 px-4 text-xl ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus:outline-none focus-visible:outline-none focus-visible:ring-0 focus-visible:ring-offset-0 disabled:cursor-not-allowed disabled:opacity-50',
              'pr-6',
            )}
          />
          <div className="absolute left-[92%] top-1/2 transform -translate-y-1/2">
            <Link
              href={{
                pathname: '/search',
                query: query ? { q: query } : {},
              }}
            >
              <Button variant="custom2" size="customSize" className="w-[50%] h-[95%] rounded-xl">
                <Search className="text-muted-foreground" size={18} />
              </Button>
            </Link>
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
                {notifications.length > 0 ? (
                  notifications.slice().reverse().map((item, index) => (
                    <Link key={item.id} href={item.redirectUrl || '#'}>
                      <div
                        className="overflow-hidden max-w-92 m-2 px-4 py-2 rounded-lg bg-brand-mid hover:bg-brand-dark hover:text-white cursor-pointer">
                        <p className="text-xs text-black">
                          {new Date(item.createdAt).toLocaleString()}
                        </p>
                        <p className="mt-1">{item.message}</p>
                      </div>
                    </Link>
                  ))
                ) : (
                  <p className="text-center p-2">No new notifications</p>
                )}
              </div>
            </SelectGroup>
          </SelectContent>
        </Select>



        {session ? (
          <SidebarProvider>
            <Button variant="custom" size="icon2" className="rounded-full" onClick={toggleSidebar}>
              <User />
            </Button>
            {isSidebarOpen && <AppSidebar />}
          </SidebarProvider>
        ) : (
          <Popover>
            <PopoverTrigger asChild>
              <Button variant="custom" size="icon2" className="rounded-full">
                <User />
              </Button>
            </PopoverTrigger>
            <PopoverContent className="p-2 text-center mt-0" style={{ marginTop: '-40px' }}>
              <p className="mb-2">Log in first</p>
              <Link href="/auth/login" className="text-black underline hover:text-brand-dark">
                Go to Login
              </Link>
            </PopoverContent>
          </Popover>
        )}

      </div>
    </nav>
  );
}