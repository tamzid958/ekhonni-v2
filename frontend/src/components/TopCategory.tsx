'use client';

import * as React from 'react';
import { cn } from '@/lib/utils';
import {
  NavigationMenu,
  NavigationMenuContent,
  NavigationMenuItem,
  NavigationMenuLink,
  NavigationMenuList,
  NavigationMenuTrigger,
} from '@/components/ui/navigation-menu';
import Link from 'next/link';

export function TopCAtegory() {
  return (
    <div className="bg-brand-mid h-12 flex justify-center">
      <NavigationMenu>
        <NavigationMenuList className="space-x-2">
          <NavigationMenuItem>

            <NavigationMenuTrigger
              className="flex items-center text-xs px-1 font-semibold text-gray-700 border-transparent hover:underline">ALL
              CATEGORIES</NavigationMenuTrigger>
            <NavigationMenuContent>
              <ul className="text-b grid m-2 md:w-[200px] lg:w-[300px]">
                <ListItem href="/docs" title="Electronics">
                  Wide range of tech products and accessories.
                </ListItem>
                <ListItem href="/docs/installation" title="Fashion">
                  Latest trends in clothing and accessories.
                </ListItem>
                <ListItem href="/docs" title="Books and Movies">
                  Must-read books and top-rated movies.
                </ListItem>
              </ul>
            </NavigationMenuContent>
          </NavigationMenuItem>

          <NavigationMenuItem className="relative">
            <NavigationMenuTrigger
              className="flex items-center text-xs px-1 font-semibold text-gray-700 border-transparent hover:underline">ALL
              CATEGORIES</NavigationMenuTrigger>
            <NavigationMenuContent>
              <ul className="text-b grid m-2 md:w-[200px] lg:w-[300px]">
                <ListItem href="/docs" title="Electronics">
                  Wide range of tech products and accessories.
                </ListItem>
                <ListItem href="/docs/installation" title="Fashion">
                  Latest trends in clothing and accessories.
                </ListItem>
                <ListItem href="/docs" title="Books and Movies">
                  Must-read books and top-rated movies.
                </ListItem>
              </ul>
            </NavigationMenuContent>
          </NavigationMenuItem>

          <Link href="/docs" legacyBehavior passHref>
            <div
              className="flex items-center text-xs px-1 font-semibold text-gray-700 border-transparent hover:underline">
              GARDEN
            </div>
          </Link>
          <Link href="/docs" legacyBehavior passHref>
            <div
              className="flex items-center text-xs px-1 font-semibold text-gray-700 border-transparent hover:underline">
              GARDEN
            </div>
          </Link>
          <Link href="/docs" legacyBehavior passHref>
            <div
              className="flex items-center text-xs px-1 font-semibold text-gray-700 border-transparent hover:underline">
              GARDEN
            </div>
          </Link>


        </NavigationMenuList>
      </NavigationMenu>
    </div>
  );
}

const ListItem = React.forwardRef<
  React.ElementRef<'a'>,
  React.ComponentPropsWithoutRef<'a'>
>(({ className, title, children, ...props }, ref) => {
  return (
    <li>
      <NavigationMenuLink asChild>
        <a
          ref={ref}
          className={cn(
            'block select-none space-y-1 rounded-md p-2 leading-none no-underline outline-none transition-colors hover:bg-brand-mid hover:text-accent-foreground focus:bg-accent focus:text-accent-foreground',
            className,
          )}
          {...props}
        >
          <div className="text-sm font-medium leading-none">{title}</div>
          <p className="line-clamp-2 text-sm leading-snug text-muted-foreground">
            {children}
          </p>
        </a>
      </NavigationMenuLink>
    </li>
  );
});
ListItem.displayName = 'ListItem';