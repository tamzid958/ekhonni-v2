import React from 'react';
import Link from 'next/link';

interface SidebarProps {
  selectedCategory: string;
  categories: {
    category: {
      name: string;
    };
    subCategories: {
      name: string;
    }[];
    chainCategories: string[];
  };
}

export default function Sidebar({ selectedCategory, categories }: SidebarProps) {
  const categoryName = categories.category.name.toLowerCase().includes('root') ? 'All' : categories.category.name;

  return (
    <aside className="w-56 flex-shrink-0 min-h-screen">
      <h2 className="text-lg font-semibold mb-4">Shop by Category</h2>
      <ul className="space-y-1">
        {/* Main Category */}
        <li>
          <Link
            href={`/categoryProducts?category=${encodeURIComponent(categoryName)}`}
            className="block py-1 rounded text-lg font-semibold hover:underline"
          >
            {categoryName}
          </Link>

          {/* Subcategories */}
          {categories.subCategories.length > 0 && (
            <ul className="pl-4 space-y-1">
              {categories.subCategories.map(({ name }) => (
                <li key={name}>
                  <Link
                    href={`/categoryProducts?category=${encodeURIComponent(name)}`}
                    className="block py-1 rounded text-lg hover:underline"
                  >
                    {name}
                  </Link>
                </li>
              ))}
            </ul>
          )}
        </li>
      </ul>
    </aside>
  );
}
