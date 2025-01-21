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
  const url =
    selectedCategory === 'All'
      ? `http://localhost:8080/api/v2/category/all`
      : `http://localhost:8080/api/v2/category/${encodeURIComponent(selectedCategory)}/subcategories`;

  let categories: Category[] = [];

  try {
    const response = await fetch(url, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('Failed to fetch category data');
    }

    const json = await response.json();

    // if (selectedCategory === 'All') {
    //   // Use the array response as is
    //   categories = json.data;
    // } else {
    //   // Wrap the single object response into an array
    //   categories = [json.data];
    // }
    categories = (selectedCategory === 'All' ? json.data : [json.data]);
  } catch (error) {
    console.error('Error fetching category data:', error);
  }

  console.log(categories);

  return (
    <aside className="w-56 flex-shrink-0 min-h-screen">
      <h2 className="text-lg font-semibold mb-4">Shop by Category</h2>
      <ul className="space-y-1">
        {/* Conditional Rendering */}
        {selectedCategory === 'All' &&
          categories.map((category) => (
            <li key={category.name}>
              <Link
                href={
                  category.name === 'All'
                    ? '/categoryProducts'
                    : `/categoryProducts?category=${encodeURIComponent(category.name)}`
                }
                className={`block py-1 rounded text-lg hover:underline`}
              >
                {category.name}
              </Link>
            </li>
          ))}

        {selectedCategory !== 'All' &&
          categories.map((category) => (
            <li key={category.name}>
              {/* Render category name */}
              <Link
                href={`/categoryProducts?category=${encodeURIComponent(category.name)}`}
                className={`block py-1 rounded text-lg hover:underline`}
              >
                {category.name}
              </Link>
              {/* Render subcategories */}
              {category.subCategories.length > 0 && (
                <ul className="pl-4 space-y-1">
                  {category.subCategories.map((subCategory) => (
                    <li key={subCategory}>
                      <Link
                        href={`/categoryProducts?category=${encodeURIComponent(subCategory)}`}
                        className="block py-1 rounded text-lg hover:underline"
                      >
                        {subCategory}
                      </Link>
                    </li>
                  ))}
                </ul>
              )}
            </li>
          ))}
      </ul>
    </aside>
  );
}
