import React from 'react';

interface SidebarProps {
  selectedCategory: string;
  setSelectedCategory: (category: string) => void;
}

const Sidebar: React.FC<SidebarProps> = ({
                                           selectedCategory,
                                           setSelectedCategory,
                                         }) => {
  const categories = ['All', 'Best Selling', 'Limited Time Deals', 'Top Rated'];

  return (
    <aside className="w-64 bg-gray-100 p-4 min-h-screen">
      <h2 className="text-lg font-semibold mb-4">Shop by Category</h2>
      <ul className="space-y-2">
        {categories.map((category) => (
          <li
            key={category}
            className={`cursor-pointer p-2 rounded ${
              selectedCategory === category
                ? 'bg-blue-500 text-white'
                : 'hover:bg-blue-100'
            }`}
            onClick={() => setSelectedCategory(category)}
          >
            {category}
          </li>
        ))}
      </ul>
    </aside>
  );
};

export default Sidebar;
