import React from 'react';
import Link from 'next/link';

interface SidebarProps {
  selectedCategory: string;
}

const Sidebar: React.FC<SidebarProps> = ({ selectedCategory }) => {
  const categories = [
    'All',
    'Travel & Nature',
    'Antiques',
    'Electronics',
    'Home Decor',
    'Fashion',
    'Books',
    'Sports & Outdoors',
    'Beauty & Health',
    'Toys & Games',
  ];

  return (
    <aside className="w-56 flex-shrink-0 min-h-screen">
      <h2 className="text-lg font-semibold mb-4">Shop by Category</h2>
      <ul className="space-y-1">
        {categories.map((category) => (
          <li key={category}>
            <Link
              href={
                category === 'All'
                  ? '/categoryProducts'
                  : `/categoryProducts?category=${encodeURIComponent(category)}`
              }
              className={`block py-1 rounded text-lg ${
                selectedCategory === category
                  ? 'font-bold'
                  : 'hover:underline'
              }`}
            >
              {category}
            </Link>
          </li>
        ))}
      </ul>
    </aside>
  );
};

export default Sidebar;
