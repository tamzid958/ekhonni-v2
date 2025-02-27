import { createColumnHelper } from '@tanstack/react-table';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import React, { useState } from 'react';

export interface CategoryData {
  category: {
    name: string;
  };
  subCategories: {
    name: string;
  }[];
  chainCategories: string[];
}

// Define the type for subcategories
export interface SubCategory {
  name: string;
}

// Column helper
const columnHelper = createColumnHelper<SubCategory>();

export function getColumns(mainCategory: string) {
  return [
    columnHelper.accessor('name', {
      header: () => <span>{mainCategory} → Subcategories</span>,
      cell: ({ row }) => {
        const [editedName, setEditedName] = useState(row.original.name);

        return (
          <Input
            value={editedName}
            onChange={(e) => setEditedName(e.target.value)}
            className="border px-2 py-1 rounded-md"
          />
        );
      },
    }),
    columnHelper.display({
      id: 'actions',
      header: () => <span>Actions</span>,
      cell: ({ row }) => (
        <div className="flex space-x-2">
          <Button size="sm" variant="outline" onClick={() => console.log('Save', row.original.name)}>Save</Button>
          <Button size="sm" variant="destructive"
                  onClick={() => console.log('Delete', row.original.name)}>Delete</Button>
        </div>
      ),
    }),
  ];
}
