'use client';

import React, { useState } from 'react';
import { Button } from '@/components/ui/button';
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import Link from 'next/link';
import { toast, Toaster } from 'sonner';
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbSeparator,
} from '@/components/ui/breadcrumb'; // Import ShadCN toast

interface CategoryNode {
  name: string;
  subCategories: string[];
  chainCategories: string[];
}

interface Props {
  category: string;
  categories: CategoryNode[];
}

export default function CategoryRender({ category, categories }: Props) {
  const [newCategory, setNewCategory] = useState('');
  const handleSubmit = async () => {
    if (newCategory.trim() === '') {
      toast('Category name cannot be empty', {
          description: 'Please enter a valid category name.',
        },// Show success toast
      );
      return;
    }

    const url = 'http://localhost:8080/api/v2/category';

    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: newCategory,
          parentCategory: category === 'All' ? null : category,
        }),
      });

      if (!response.ok) {
        toast('Failed to add category', {
            description: 'There was an issue adding the category.',
          },
        );
        throw new Error('Failed to add category');
      }

      setNewCategory('');
      toast('Category added successfully', {
          description: 'Your new category has been added.',
        },// Show success toast
      );
      setTimeout(() => {
        window.location.reload(); // Reload after 3 seconds
      }, 3000);
    } catch (error) {
      console.error('Error adding category:', error);
    }
  };

  function handleEdit(category: string) {
  }

  const handleDelete = async (name: string) => {
    const url = `http://localhost:8080/api/v2/category/${encodeURIComponent(name)}`;

    try {
      const response = await fetch(url, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });
      const responseData = await response.json();
      console.log(responseData);
      if (!response.ok) {
        toast('Failed to delete category', {
            description: 'Check if it has subcategories inside.',
          },
        );
        throw new Error('Failed to delete category');
      }
      console.log(response);
      toast('Category deleted successfully', {
          description: 'Your selected category has been deleted.',
        },// Show success toast
      );

      setTimeout(() => {
        window.location.reload(); // Reload after 3 seconds
      }, 3000);
    } catch (error) {
      console.error('Error deleting category:', error);
    }

  };

  return (
    <div className="min-h-screen flex flex-col items-center space-y-6 mx-auto px-4 w-full overflow-hidden">

      {/* Title Left-Aligned */}
      <h1 className="text-5xl font-bold m-8 text-left w-full pl-4">Category Management</h1>
      <Toaster position="top-right" />

      {/* Breadcrumbs */}
      <div className="w-full max-w-4xl">
        <Breadcrumb>
          <BreadcrumbList>
            <BreadcrumbItem className="text-xl">
              <BreadcrumbLink asChild>
                <Link href="/admin/categories">..</Link>
              </BreadcrumbLink>
            </BreadcrumbItem>
            {categories[0]?.chainCategories.slice().reverse().map((cat, index) => (
              <React.Fragment key={index}>
                <BreadcrumbSeparator />
                <BreadcrumbItem className="text-xl">
                  <BreadcrumbLink asChild>
                    <Link href={`/admin/categories?category=${encodeURIComponent(cat)}`}>{cat}</Link>
                  </BreadcrumbLink>
                </BreadcrumbItem>
              </React.Fragment>
            ))}
          </BreadcrumbList>
        </Breadcrumb>
      </div>

      {/* Centered Table */}
      <div className="flex justify-center w-full">
        <div className="w-full max-w-4xl">
          <div className="space-y-4"> {/* Parent container for spacing */}
            {category === 'All' ? (
              // Render all categories when 'All' is selected
              categories.map((cat, index) => (
                <div
                  key={index}
                  className={`flex items-center justify-between p-4 rounded-lg shadow ${
                    index % 2 === 0 ? 'bg-gray-50' : 'bg-white'
                  } hover:bg-yellow-50`}
                >
                  {/* Category Name with Link */}
                  <Link
                    href={`/admin/categories?category=${encodeURIComponent(cat.name)}`}
                    className="text-xl font-medium text-gray-700 hover:underline"
                  >
                    {cat.name}
                  </Link>
                  {/* Action Buttons */}
                  <div className="flex space-x-4">
                    <button
                      onClick={() => handleEdit(cat.name)}
                      className="px-4 py-2 text-sm font-semibold text-blue-600 bg-blue-100 rounded-lg hover:bg-blue-200"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(cat.name)}
                      className="px-4 py-2 text-sm font-semibold text-red-600 bg-red-100 rounded-lg hover:bg-red-200"
                    >
                      Delete
                    </button>
                  </div>
                </div>
              ))
            ) : (
              // Render subcategories when a specific category is selected
              categories[0]?.subCategories.map((subCat, index) => (
                <div
                  key={index}
                  className={`flex items-center justify-between p-4 rounded-lg shadow ${
                    index % 2 === 0 ? 'bg-gray-50' : 'bg-white'
                  } hover:bg-yellow-50`}
                >
                  {/* Subcategory Name with Link */}
                  <Link
                    href={`/admin/categories?category=${encodeURIComponent(subCat)}`}
                    className="text-xl font-medium text-gray-700 hover:underline"
                  >
                    {subCat}
                  </Link>
                  {/* Action Buttons */}
                  <div className="flex space-x-4">
                    <button
                      onClick={() => handleEdit(subCat)}
                      className="px-4 py-2 text-sm font-semibold text-blue-600 bg-blue-100 rounded-lg hover:bg-blue-200"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(subCat)}
                      className="px-4 py-2 text-sm font-semibold text-red-600 bg-red-100 rounded-lg hover:bg-red-200"
                    >
                      Delete
                    </button>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>

      {/* Dialog Button */}
      <div className="flex flex-col-reverse sm:flex-row sm:justify-end sm:space-x-2">
        <Dialog>
          <DialogTrigger asChild>
            <Button variant="outline">Add New Category</Button>
          </DialogTrigger>
          <DialogContent className="sm:max-w-[425px]">
            <DialogHeader>
              <DialogTitle>Add New Category</DialogTitle>
              <DialogDescription>Add new category</DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Input
                  id="newCategory"
                  value={newCategory}
                  onChange={(e) => {
                    setNewCategory(e.target.value);
                  }}
                  className="col-span-3"
                />
              </div>
              <Label>
                <span>Category Name</span>
              </Label>
            </div>
            <DialogFooter>
              <DialogClose asChild>
                <Button type="submit" onClick={handleSubmit}>
                  Save changes
                </Button>
              </DialogClose>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
    </div>
  );
}
