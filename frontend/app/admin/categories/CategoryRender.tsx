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
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import Link from 'next/link';
import { toast } from '@/hooks/use-toast'; // Import ShadCN toast

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
      toast({
        title: 'Category name cannot be empty',
        description: 'Please enter a valid category name.',
        variant: 'destructive', // Optionally, use a variant to style it
      });
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
        toast({
          title: 'Failed to add category',
          description: 'There was an issue adding the category.',
          variant: 'destructive',
        });
        throw new Error('Failed to add category');
      }

      setNewCategory('');
      toast({
        title: 'Category added successfully',
        description: 'Your new category has been added.',
        variant: 'default', // Show success toast
      });
      window.location.reload();
    } catch (error) {
      console.error('Error adding category:', error);
    }
  };

  return (
    <div className="min-h-screen flex flex-col items-center space-y-6 mx-auto px-4 w-full overflow-hidden">
      {/* Title Left-Aligned */}
      <h1 className="text-5xl font-bold my-12 text-left w-full pl-4">Category Management</h1>

      {/* Centered Table */}
      <div className="flex justify-center w-full">
        <div className="w-full max-w-4xl"> {/* Ensure table doesn't stretch too wide */}
          <Table>
            <TableHeader>
              <TableRow>
                <TableHead className="text-left">Category Name</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {category === 'All' ? (
                // Render all categories when 'All' is selected
                categories.map((cat, index) => (
                  <TableRow key={index}>
                    <TableCell>
                      <Link
                        href={`/admin/categories?category=${encodeURIComponent(cat.name)}`}
                      >
                        {cat.name}
                      </Link>
                    </TableCell>
                  </TableRow>
                ))
              ) : (
                // Render subcategories when a specific category is selected
                categories[0]?.subCategories.map((subCat, index) => (
                  <TableRow key={index}>
                    <TableCell>
                      <Link href={`/admin/categories?category=${encodeURIComponent(subCat)}`}>
                        {subCat}
                      </Link>
                    </TableCell>
                  </TableRow>
                ))
              )}
            </TableBody>
          </Table>
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
              <DialogDescription>
                Add new category
              </DialogDescription>
            </DialogHeader>
            <div className="grid gap-4 py-4">
              <div className="grid grid-cols-4 items-center gap-4">
                <Input id="newCategory"
                       value={newCategory}
                       onChange={(e) => {
                         setNewCategory(e.target.value);
                       }} className="col-span-3" />
              </div>
              <Label>
                <span>Category Name</span>
              </Label>
            </div>
            <DialogFooter>
              <DialogClose asChild>
                <Button type="submit" onClick={handleSubmit}>Save changes</Button>
              </DialogClose>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </div>
    </div>
  );
}
