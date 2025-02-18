'use client';
import { Button } from './ui/button';
import { Bell, Search, ShoppingCart, User } from 'lucide-react';
import React, { useEffect, useRef, useState } from 'react';
import Link from 'next/link';
import { Select, SelectContent, SelectGroup, SelectTrigger } from '@/components/ui/select';
import { NotificationGetter } from '@/components/Notification';
import { useSession } from 'next-auth/react';
import { Popover, PopoverContent, PopoverTrigger } from '@/components/ui/popover';
import { useRouter } from 'next/navigation';
import { AppSidebar } from '@/components/userSheet';
import { Sheet, SheetTrigger } from '@/components/ui/sheet';
import { useFilterProducts } from '@/hooks/useFilterProducts';

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

  const { products, error, isLoading } = useFilterProducts(query, 'newlyListed', [], [], [0, 1000000]);

  const handleLoginRedirect = () => {
    router.push('/'); // Redirect to home page
  };
  const filteredProducts = products || [];
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setQuery(e.target.value);
  };
  const handleKeyDown = (e: React.MouseEvent<HTMLButtonElement> | React.KeyboardEvent<HTMLInputElement>) => {
    if ('key' in e && e.key === 'Enter' && query.trim() !== '') {
      router.push(`/search?q=${encodeURIComponent(query)}`);
      setQuery('');
    } else if ('type' in e && e.type === 'click' && query.trim() !== '') {
      router.push(`/search?q=${encodeURIComponent(query)}`);
      setQuery('');
    }
  };

  useEffect(() => {
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
    setSidebarOpen((prev) => !prev);
  };


  return (
    <nav className="flex gap-4 p-4 text-2xl bg-brand-dark h-24 relative z-40 overflow-visible">
      <div className="font-bold ml-4 md:ml-8 lg:ml-20 md:mt-4 lg:mt-2">
        <Link href="/">
          <img
            src="frame.png"
            alt="logo"
            className="h-8 lg:h-14 xl:h-16 min-h-8 min-w-8 w-auto object-contain"
          />
        </Link>
      </div>

      <div className="relative flex justify-center items-center mt-2 w-full max-w-xl mx-auto">
        <input
          className="w-full m-1 pl-4 pr-12 py-2 rounded-lg border-none focus:ring-0 focus:outline-none"
          type="text"
          placeholder={placeholder}
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onKeyDown={handleKeyDown}
        />

        {query && filteredProducts.length > 0 && (
          <div
            className="absolute left-0 top-[calc(85%)] w-full bg-brand-bright border border-gray-200 rounded-md shadow-lg z-50">
            <div className="max-h-60 overflow-y-auto divide-y divide-gray-200">
              {filteredProducts.map((item) => (
                <Link key={item.id} href={`/productDetails?id=${item.id}`}>
                      <span className="block px-4 py-2 hover:bg-brand-mid transition-colors cursor-pointer rounded-lg">
                        <p className="text-gray-800 text-base">{item.title}</p>
                      </span>
                </Link>
              ))}
            </div>
          </div>
        )}

        <div className="absolute right-2 top-1/2 transform -translate-y-1/2">
          <button
            className="m-1 rounded-full text-white focus:outline-none"
            onClick={handleKeyDown}
          >
            <Search className="text-brand-dark" size={20} />
          </button>
        </div>
      </div>

      <div className="flex gap-4 mr-4 mt-4 sm:mr-14 md:mr-16 lg:mr-32">
        {session && (<Link href="/cart">
          <Button variant="custom" size="icon">
            <ShoppingCart className="h-6 w-6" />
          </Button>
        </Link>)}
        {session && (<Select>
          <SelectTrigger
            className="text-primary bg-brand-mid hover:bg-brand-light h-10 w-10 px-3 focus:ring-0 focus:outline-none active:ring-0 active:outline-none focus-visible:ring-0 focus-visible:outline-none ring-0 [&_svg.h-4]:hidden">
            <Bell className="w-6 h-6" />
          </SelectTrigger>
          <SelectContent className="bg-brand-bright w-96 md:right-10 lg:right-12">
            <SelectGroup>
              <p className="text-xm font-bold p-2 justify-center flex">NOTIFICATION</p>
              <div
                className="max-h-96 overflow-y-auto space-y-1 scrollbar-thin scrollbar-thumb-brand-dark scrollbar-track-brand-mid">
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
        </Select>)}


        {session ? (
          <Sheet>
            <SheetTrigger asChild>
              <Button variant="custom" size="icon" onClick={toggleSidebar}>
                <User className="h-6 w-6" />
              </Button>
            </SheetTrigger>

            {/* The Sidebar content */}
            <AppSidebar />
          </Sheet>
        ) : (
          <Popover>
            <PopoverTrigger asChild>
              <Button variant="custom" size="icon">
                <User className="h-6 w-6" />
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