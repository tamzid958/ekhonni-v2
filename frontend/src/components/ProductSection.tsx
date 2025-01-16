import Link from 'next/link';
import { ScrollArea, ScrollBar } from '@/components/ui/scroll-area';
import { CardDemo } from '@/components/Card';
import React from 'react';

interface Data {
  id: string;
  title: string;
  description: string;
  img: string;
  price: number;
  category: string;
  label: string;
}

interface ProductSectionProps {
  title: string;
  products: Data[];
  selectedCategory: string;
}

export function ProductSection({ title, products, selectedCategory }: ProductSectionProps) {
  return (
    <div className="w-full mb-6">
      <div className="flex justify-between items-center">
        <h2 className="text-3xl font-semibold py-4">{title}</h2>
        <span>
                  <Link href={{
                    pathname: '/labeledCategory',
                    query: { category: selectedCategory, label: title },
                  }}
                        className="text-xl"
                  > See All </Link> </span>
      </div>
      <ScrollArea className="w-full overflow-x-auto">
        {products.length === 0 ? (
          <p className="text-center text-gray-500">No products found in this label.</p>
        ) : (
          <div className="flex w-[1000px] space-x-4 py-4">
            {products.map((item) => (
              <CardDemo key={item.id} {...item} />
            ))}
          </div>
        )}
        <ScrollBar orientation="horizontal" />
      </ScrollArea>
    </div>
  );
}