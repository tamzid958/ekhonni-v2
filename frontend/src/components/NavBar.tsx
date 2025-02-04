'use client';
import { Button } from './ui/button';
import { Bell, Search, ShoppingCart, User } from 'lucide-react';
import React, { useEffect, useState } from 'react';
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
};

export function NavBar({ placeholder }: Props) {
  const [isSidebarOpen, setSidebarOpen] = useState(false);
  const [notifications, setNotifications] = useState<Notification[]>([]);
  const { data: session, status } = useSession();
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
      console.log('Notification API result:', result);

      console.info('the last fetch time is->');
      console.log(lastFetchTime);

      if (result.success) {
        if (Array.isArray(result.data)) {
          // Filter out notifications with empty messages and duplicates
          const newNotifications = result.data
            .filter((item) => item.message.trim().length > 0) // Filter empty messages
            .filter((item) => !notifications.some((notification) => notification.id === item.id)); // Filter duplicates

          // Add new notifications to the state
          setNotifications((prevNotifications) => {
            const updatedNotifications = [...prevNotifications, ...newNotifications];

            // Remove neighboring duplicates
            const filteredNotifications = updatedNotifications.filter(
              (notification, index, array) => {
                if (index < array.length - 1 && notification.id === array[index + 1].id) {
                  return false;
                }
                return true;
              },
            );

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
            className={cn(
              'flex h-12 w-full rounded-md border border-input bg-background py-2 px-4 text-xl ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus:outline-none focus-visible:outline-none focus-visible:ring-0 focus-visible:ring-offset-0 disabled:cursor-not-allowed disabled:opacity-50',
              'pr-6',
            )}
          />
          <div className="absolute left-[92%] top-1/2 transform -translate-y-1/2">
            <Button variant="custom2" size="customSize" className="w-[50%] h-[95%] rounded-xl">
              <Link href="/search">
                <Search className="text-muted-foreground" size={18} />
              </Link>
              {/*<span className="sr-only">Search Button</span>*/}
            </Button>
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
                  notifications.map((item, index) => (
                    <Link key={index} href="/">
                      <div
                        className="overflow-hidden max-w-92 m-2 px-4 py-2 rounded-lg bg-brand-mid hover:bg-brand-dark hover:text-white cursor-pointer">
                        {item.message}
                        {item.id}
                        {item.lastFetchTime}
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



        <SidebarProvider>
          {session ? (
            <>
              <Button variant="custom" size="icon2" className="rounded-full" onClick={toggleSidebar}>
                <User />
              </Button>
              {isSidebarOpen && <AppSidebar />}
            </>
          ) : (
            <Popover>
              <PopoverTrigger asChild>
                <Button variant="custom" size="icon2" className="rounded-full">
                  <User />
                </Button>
              </PopoverTrigger>
              <PopoverContent
                className="p-2 text-center mt-0"
                style={{ marginTop: '-40px' }}
              >
                <p className="mb-2">Log in first</p>
                <Link href="/auth/login" className="text-black underline hover:text-brand-dark">
                  Go to Login
                </Link>
              </PopoverContent>
            </Popover>
          )}
        </SidebarProvider>
      </div>
    </nav>
  );
}