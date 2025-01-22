'use client';

import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { Dialog, DialogContent, DialogFooter, DialogHeader, DialogTrigger } from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';

interface CategoryNode {
  name: string;
  subcategories: string[];
  chainCategories: string[];
}

export default function CategoryPage() {
  const [categories, setCategories] = useState<CategoryNode[]>([]);
  const [newCategoryName, setNewCategoryName] = useState<string>('');
  const [parentCategoryId, setParentCategoryId] = useState<string | null>(null);

  // Fetch categories
  useEffect(() => {
    fetch('http://localhost:8080/api/v2/category/all')
      .then((res) => res.json())
      .then((data) => {
        console.log(data.data);
        setCategories(data.data);
      })
      .catch((err) => console.error('Error fetching categories:', err));
  }, []);

  // Add new category
  function handleAddCategory() {
    
    // Post to API
    fetch('http://localhost:8080/api/v2/category', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        name: newCategoryName,
        parentCategory: parentCategoryId,
      }),
    })
      .then((res) => res.json())
      .then((data) => console.log('Category added:', data))
      .catch((err) => console.error('Error adding category:', err));

    setNewCategoryName('');
    setParentCategoryId(null);
  }

  const renderCategories = (nodes: CategoryNode[]) =>
    nodes.map((category) => (
      <React.Fragment key={category.name}>
        <TableRow>
          <TableCell className="font-medium">{category.name}</TableCell>

          {/*</TableCell>*/}
          {/*<TableCell>*/}
          {/*  {category.subcategories.length > 0 ? (*/}
          {/*    <ul className="list-disc pl-4">*/}
          {/*      {category.subcategories.map((sub) => (*/}
          {/*        <li key={sub}> {sub} </li>*/}
          {/*      ))}*/}
          {/*    </ul>*/}
          {/*  ) : (*/}
          {/*    'No subcategories'*/}
          {/*  )}*/}
          {/*</TableCell>*/}
        </TableRow>
        {/*{category.subcategories.length > 0 &&*/}
        {/*  renderCategories(category.subcategories)}*/}
      </React.Fragment>
    ));

  return (
    <div className="container mx-auto p-6 space-y-6">
      <h1 className="text-3xl font-bold">Category Management</h1>

      {/* Data Table */}
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Category</TableHead>
            <TableHead>Subcategories</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>{renderCategories(categories)}</TableBody>
      </Table>
      <Dialog>
        <DialogTrigger asChild>
          <Button>Add New Category</Button>
        </DialogTrigger>
        <DialogContent>
          <DialogHeader>
            <h2 className="text-lg font-bold">Add New Category</h2>
          </DialogHeader>
          <div className="space-y-4">
            <div>
              <Label htmlFor="newCategoryName">Category Name</Label>
              <Input
                id="newCategoryName"
                placeholder="Enter category name"
                value={newCategoryName}
                onChange={(e) => setNewCategoryName(e.target.value)}
              />
            </div>
            <div>
              <Label htmlFor="parentCategory">Parent Category</Label>
              <select
                id="parentCategory"
                className="w-full p-2 border rounded"
                value={parentCategoryId || ''}
                onChange={(e) =>
                  setParentCategoryId(
                    e.target.value === '' ? null : e.target.value,
                  )
                }
              >
                <option value="">None (Main Category)</option>
                {categories.map((cat) => (
                  <option key={cat.name} value={cat.name}>
                    {cat.name}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <DialogFooter>
            <Button onClick={handleAddCategory}>Save</Button>
          </DialogFooter>
        </DialogContent>
      </Dialog>
    </div>
  );
}


// 'use client';
//
// import React, { useState } from 'react';
// import { Button } from '@/components/ui/button';
// import { Input } from '@/components/ui/input';
// import { DropdownMenu, DropdownMenuContent, DropdownMenuTrigger } from '@/components/ui/dropdown-menu';
//
// interface CategoryNode {
//   id: number;
//   name: string;
//   subcategories: CategoryNode[];
// }
//
// export default function CategoryManagement() {
//   const [categories, setCategories] = useState<CategoryNode[]>([]);
//   const [newCategory, setNewCategory] = useState<string>('');
//
//   // Add a new main category
//   const handleAddCategory = () => {
//     if (!newCategory.trim()) return;
//
//     const newNode: CategoryNode = {
//       id: Date.now(),
//       name: newCategory.trim(),
//       subcategories: [],
//     };
//
//     setCategories((prev) => [...prev, newNode]);
//     setNewCategory('');
//   };
//
//   // Add subcategory to a specific category
//   const handleAddSubcategory = (parentId: number, subcategoryName: string) => {
//     const updateCategories = (nodes: CategoryNode[]): CategoryNode[] =>
//       nodes.map((node) =>
//         node.id === parentId
//           ? {
//             ...node,
//             subcategories: [
//               ...node.subcategories,
//               { id: Date.now(), name: subcategoryName, subcategories: [] },
//             ],
//           }
//           : { ...node, subcategories: updateCategories(node.subcategories) },
//       );
//
//     setCategories((prev) => updateCategories(prev));
//   };
//
//   // Recursive function to render categories
//   const renderCategories = (nodes: CategoryNode[]) =>
//     nodes.map((node) => (
//       <div key={node.id} className="ml-4 border-l-2 pl-4 space-y-2">
//         <div className="flex items-center justify-between">
//           <span className="font-bold text-gray-700">{node.name}</span>
//           <DropdownInput onAddSubcategory={(name) => handleAddSubcategory(node.id, name)} />
//         </div>
//         {node.subcategories.length > 0 && (
//           <div className="ml-4">{renderCategories(node.subcategories)}</div>
//         )}
//       </div>
//     ));
//
//   // Submit categories
//   const handleSubmit = () => {
//     console.log('Submitting data:', JSON.stringify(categories, null, 2));
//     // Send `categories` JSON to the database
//   };
//
//   return (
//     <div className="space-y-6 p-6 container mx-auto">
//       <h1 className="text-3xl font-bold text-gray-800">Category Management</h1>
//
//       {/* Add Main Category */}
//       <div className="flex items-center space-x-2">
//         <Input
//           value={newCategory}
//           onChange={(e) => setNewCategory(e.target.value)}
//           placeholder="Enter main category"
//           className="w-1/3"
//         />
//         <Button onClick={handleAddCategory}>Add Category</Button>
//       </div>
//
//       {/* Render Categories */}
//       <div className="mt-6">
//         {categories.length === 0 ? (
//           <p className="text-gray-500">No categories added yet.</p>
//         ) : (
//           renderCategories(categories)
//         )}
//       </div>
//
//       {/* Submit Button */}
//       <div className="mt-6">
//         <Button onClick={handleSubmit}>Submit to Database</Button>
//       </div>
//     </div>
//   );
// }
//
// function DropdownInput({
//                          onAddSubcategory,
//                        }: {
//   onAddSubcategory: (name: string) => void;
// }) {
//   const [subcategoryName, setSubcategoryName] = useState('');
//
//   const handleAdd = () => {
//     if (!subcategoryName.trim()) return;
//     onAddSubcategory(subcategoryName.trim());
//     setSubcategoryName('');
//   };
//
//   return (
//     <DropdownMenu>
//       <DropdownMenuTrigger asChild>
//         <Button variant="outline" size="sm">
//           Add Subcategory
//         </Button>
//       </DropdownMenuTrigger>
//       <DropdownMenuContent>
//         <div className="p-4 space-y-2">
//           <Input
//             value={subcategoryName}
//             onChange={(e) => setSubcategoryName(e.target.value)}
//             placeholder="Enter subcategory name"
//           />
//           <Button size="sm" onClick={handleAdd}>
//             Add
//           </Button>
//         </div>
//       </DropdownMenuContent>
//     </DropdownMenu>
//   );
// }
