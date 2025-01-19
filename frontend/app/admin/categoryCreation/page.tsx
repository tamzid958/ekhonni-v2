'use client';

import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { DropdownMenu, DropdownMenuContent, DropdownMenuTrigger } from '@/components/ui/dropdown-menu';

interface CategoryNode {
  id: number;
  name: string;
  subcategories: CategoryNode[];
}

export default function CategoryManagement() {
  const [categories, setCategories] = useState<CategoryNode[]>([]);
  const [newCategory, setNewCategory] = useState<string>('');

  // Add a new main category
  const handleAddCategory = () => {
    if (!newCategory.trim()) return;

    const newNode: CategoryNode = {
      id: Date.now(),
      name: newCategory.trim(),
      subcategories: [],
    };

    setCategories((prev) => [...prev, newNode]);
    setNewCategory('');
  };

  // Add subcategory to a specific category
  const handleAddSubcategory = (parentId: number, subcategoryName: string) => {
    const updateCategories = (nodes: CategoryNode[]): CategoryNode[] =>
      nodes.map((node) =>
        node.id === parentId
          ? {
            ...node,
            subcategories: [
              ...node.subcategories,
              { id: Date.now(), name: subcategoryName, subcategories: [] },
            ],
          }
          : { ...node, subcategories: updateCategories(node.subcategories) },
      );

    setCategories((prev) => updateCategories(prev));
  };

  // Recursive function to render categories
  const renderCategories = (nodes: CategoryNode[]) =>
    nodes.map((node) => (
      <div key={node.id} className="ml-4 border-l-2 pl-4 space-y-2">
        <div className="flex items-center justify-between">
          <span className="font-bold text-gray-700">{node.name}</span>
          <DropdownInput onAddSubcategory={(name) => handleAddSubcategory(node.id, name)} />
        </div>
        {node.subcategories.length > 0 && (
          <div className="ml-4">{renderCategories(node.subcategories)}</div>
        )}
      </div>
    ));

  // Submit categories
  const handleSubmit = () => {
    console.log('Submitting data:', JSON.stringify(categories, null, 2));
    // Send `categories` JSON to the database
  };

  return (
    <div className="space-y-6 p-6 container mx-auto">
      <h1 className="text-3xl font-bold text-gray-800">Category Management</h1>

      {/* Add Main Category */}
      <div className="flex items-center space-x-2">
        <Input
          value={newCategory}
          onChange={(e) => setNewCategory(e.target.value)}
          placeholder="Enter main category"
          className="w-1/3"
        />
        <Button onClick={handleAddCategory}>Add Category</Button>
      </div>

      {/* Render Categories */}
      <div className="mt-6">
        {categories.length === 0 ? (
          <p className="text-gray-500">No categories added yet.</p>
        ) : (
          renderCategories(categories)
        )}
      </div>

      {/* Submit Button */}
      <div className="mt-6">
        <Button onClick={handleSubmit}>Submit to Database</Button>
      </div>
    </div>
  );
}

function DropdownInput({
                         onAddSubcategory,
                       }: {
  onAddSubcategory: (name: string) => void;
}) {
  const [subcategoryName, setSubcategoryName] = useState('');

  const handleAdd = () => {
    if (!subcategoryName.trim()) return;
    onAddSubcategory(subcategoryName.trim());
    setSubcategoryName('');
  };

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="outline" size="sm">
          Add Subcategory
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent>
        <div className="p-4 space-y-2">
          <Input
            value={subcategoryName}
            onChange={(e) => setSubcategoryName(e.target.value)}
            placeholder="Enter subcategory name"
          />
          <Button size="sm" onClick={handleAdd}>
            Add
          </Button>
        </div>
      </DropdownMenuContent>
    </DropdownMenu>
  );
}
