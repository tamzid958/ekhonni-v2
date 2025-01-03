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
        'Toys & Games'
    ];

    return (
        <aside className="w-56 flex-shrink-0 bg-gray-100 p-4 min-h-screen">
            <h2 className="text-lg font-semibold mb-4">Shop by Category</h2>
            <ul className="space-y-2">
                {categories.map((category) => (
                    <li key={category}>
                        <Link
                            href={
                                category === 'All'
                                    ? '/categoryProducts'
                                    : `/categoryProducts?category=${encodeURIComponent(category)}`
                            }
                            className={`block p-2 rounded ${
                                selectedCategory === category
                                    ? 'bg-blue-500 text-white'
                                    : 'hover:bg-blue-100'
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
