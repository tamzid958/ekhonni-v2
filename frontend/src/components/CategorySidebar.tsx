import React from 'react';
import Link from 'next/link';

interface SidebarProps {
  selectedCategory: string;
}

interface Category {
  name: string;
  subCategories: string[];
  chainCategories: string[];
}

export default async function Sidebar({ selectedCategory }: SidebarProps) {

  const url = `http://192.168.68.255:8080/api/v2/category/all`;

  let categories: Category[] = [];

  try {
    const response = await fetch(url, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('failed to fetch categories');
    }

    const json = await response.json();
    categories = json.data;
  } catch (error) {
    console.error('Error fetching categories', error);
  }

  console.log(categories);

  return (
    <aside className="w-56 flex-shrink-0 min-h-screen">
      <h2 className="text-lg font-semibold mb-4">Shop by Category</h2>
      <ul className="space-y-1">
        {categories.map((category) => (
          <li key={category.name}>
            <Link
              href={
                category.name === 'All'
                  ? '/categoryProducts'
                  : `/categoryProducts?category=${encodeURIComponent(category.name)}`
              }
              className={`block py-1 rounded text-lg ${
                selectedCategory === category.name
                  ? 'font-bold'
                  : 'hover:underline'
              }`}
            >
              {category.name}
            </Link>
          </li>
        ))}
      </ul>
    </aside>
  );
}
