'use client';
import React, { useState } from 'react';
import { useRouter } from 'next/navigation';
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
  CommandSeparator,
} from '../components/command';
import { useFilterProducts } from '@/hooks/useFilterProducts';
import Link from 'next/link';

export function CommandDemo() {
  const [isHovered, setIsHovered] = useState(false);
  const [query, setQuery] = useState('');
  const { products, error, isLoading } = useFilterProducts('', 'newlyListed', [], [], [0, 1000000]);
  const router = useRouter();
  console.log('Query state:', query);

  // Handle Enter key press to navigate
  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && query.trim() !== '') {
      router.push(`/search?q=${encodeURIComponent(query)}`);
    }
  };

  if (error || isLoading) {
    return (
      <Command className="rounded-lg border">
        <CommandInput placeholder="What are you looking for?..." />
      </Command>
    );
  }

  return (
    <Command
      className="rounded-lg border w-1/2"
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <CommandInput
        placeholder="What are you looking for?..."
        value={query}
        onValueChange={(newValue) => {
          console.log('Typed:', newValue); // Debugging
          setQuery(newValue);
        }}
        onKeyDown={handleKeyDown}
      />
      {isHovered && (
        <CommandList>
          {products.length === 0 ? (
            <CommandEmpty>No results found.</CommandEmpty>
          ) : (
            <CommandGroup heading="Suggestions">
              {products.map((item) => (
                <CommandItem key={item.id}>
                  <Link href={`/productDetails?id=${item.id}`} className="cursor-pointer">
                    <span>{item.title}</span>
                  </Link>
                </CommandItem>
              ))}
            </CommandGroup>
          )}
          <CommandSeparator />
        </CommandList>
      )}
    </Command>
  );
}
