'use client';

import React from 'react';
import { getCoreRowModel, getSortedRowModel, useReactTable } from '@tanstack/react-table';
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import { useSession } from 'next-auth/react';
import { ProductData } from './page';
import { getColumns } from './columns';

export default function DataTable({ data }: { data: ProductData[] }) {
  const { data: session } = useSession();
  const token = session?.user?.token; // Get the session token

  const columns = getColumns(token); // Pass token to getColumns

  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getSortedRowModel: getSortedRowModel(), // Enables sorting
  });

  return (
    <div className="rounded-lg border bg-white shadow-lg overflow-x-auto">
      <Table className="w-full">
        {/* Table Header */}
        <TableHeader className="bg-gray-200">
          {table.getHeaderGroups().map((headerGroup) => (
            <TableRow key={headerGroup.id}>
              {headerGroup.headers.map((header) => (
                <TableHead key={header.id}
                           className="px-6 py-3 text-left text-lg text-gray-800 font-semibold tracking-wide">
                  {header.isPlaceholder
                    ? null
                    : typeof header.column.columnDef.header === 'function'
                      ? header.column.columnDef.header(header.getContext())
                      : header.column.columnDef.header}
                </TableHead>
              ))}
            </TableRow>
          ))}
        </TableHeader>

        {/* Table Body */}
        <TableBody>
          {table.getRowModel().rows.length > 0 ? (
            table.getRowModel().rows.map((row, index) => (
              <TableRow
                key={row.id}
                className={`transition-all hover:bg-gray-100 ${index % 2 === 0 ? 'bg-gray-50' : 'bg-white'}`}
              >
                {row.getVisibleCells().map((cell) => (
                  <TableCell key={cell.id} className="px-6 py-4 text-sm text-gray-700 break-words">
                    {typeof cell.column.columnDef.cell === 'function'
                      ? cell.column.columnDef.cell(cell.getContext())
                      : (cell.renderValue() as React.ReactNode)}
                  </TableCell>
                ))}
              </TableRow>
            ))
          ) : (
            <TableRow>
              <TableCell colSpan={columns.length} className="text-center py-4 text-gray-500">
                No Products available
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
    </div>
  );
}