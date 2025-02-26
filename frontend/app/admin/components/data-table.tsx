"use client"
import * as React from "react"
import {
  ColumnDef,
  SortingState,
  flexRender,
  ColumnFiltersState,
  getCoreRowModel,
  useReactTable,
  VisibilityState,
  getPaginationRowModel,
  getSortedRowModel,
  getFilteredRowModel,
} from "@tanstack/react-table"


import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input"



import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"


interface DataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[]
  data: TData[]
  userType?: string
}



export function DataTable<TData, TValue>({
                                           columns,
                                           data,
                                           userType,
                                         }: DataTableProps<TData, TValue>) {
  const [sorting, setSorting] = React.useState<SortingState>([])
  const [columnFilters, setColumnFilters] = React.useState<ColumnFiltersState>(
    []
  )

  const [columnVisibility, setColumnVisibility] =  React.useState<VisibilityState>({})
  const [rowSelection, setRowSelection] = React.useState({})


  const table = useReactTable({
    data,
    columns,
    getCoreRowModel: getCoreRowModel(),
    getPaginationRowModel: getPaginationRowModel(),
    onSortingChange: setSorting,
    getSortedRowModel: getSortedRowModel(),
    onColumnFiltersChange: setColumnFilters,
    getFilteredRowModel: getFilteredRowModel(),
    onColumnVisibilityChange: setColumnVisibility,
    onRowSelectionChange: setRowSelection,
    state: {
      sorting,
      columnFilters,
      columnVisibility,
      rowSelection,
    },
  })

  return (
    <div className="flex-col align-middle pl-4">
      <div className="flex items-center py-4 mr-2">
        {
          (() => {
            switch (userType) {
              case "role":
                return (
                  <div className="flex">
                    <Input
                      placeholder="Filter Ids..."
                      value={(table.getColumn("id")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("id")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                    <Input
                      placeholder="Filter name..."
                      value={(table.getColumn("name")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("name")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                    <Input
                      placeholder="Filter status..."
                      value={(table.getColumn("status")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("status")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                  </div>
                );
              case "privilege":
                return (
                  <div className="flex">

                    <Input
                      placeholder="Filter name..."
                      value={(table.getColumn("name")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("name")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                    <Input
                      placeholder="Filter Method"
                      value={(table.getColumn("httpMethod")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("httpMethod")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                    <Input
                      placeholder="Filter api..."
                      value={(table.getColumn("endpoint")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("endpoint")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                  </div>
                );
              case "user":
                return (
                  <div className="flex">
                    <Input
                      placeholder="Filter name..."
                      value={(table.getColumn("name")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("name")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                    <Input
                      placeholder="Filter emails..."
                      value={(table.getColumn("email")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("email")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                    <Input
                      placeholder="Filter status..."
                      value={(table.getColumn("status")?.getFilterValue() as string) ?? ""}
                      onChange={(event) =>
                        table.getColumn("status")?.setFilterValue(event.target.value)
                      }
                      className="max-w-sm w-28 mr-2"
                    />
                  </div>
                );
              default:
                return (<div></div>)
            }
          })()
        }
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="outline" className="ml-auto">
              Columns
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            {table
              .getAllColumns()
              .filter(
                (column) => column.getCanHide()
              )
              .map((column) => {
                return (
                  <DropdownMenuCheckboxItem
                    key={column.id}
                    className="capitalize"
                    checked={column.getIsVisible()}
                    onCheckedChange={(value) =>
                      column.toggleVisibility(!!value)
                    }
                  >
                    {column.id}
                  </DropdownMenuCheckboxItem>
                )
              })}
          </DropdownMenuContent>
        </DropdownMenu>
      </div>
      <div className="rounded-md w-full border ">
      <Table>
        <TableHeader>
          {table.getHeaderGroups().map((headerGroup) => (
              <TableRow key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                    return (
                      <TableHead key={header.id}>
                        {header.isPlaceholder
                            ? null
                            : flexRender(
                              header.column.columnDef.header,
                              header.getContext()
                            )}
                        </TableHead>
                    )
                  })}
                </TableRow>
            ))}
      </TableHeader>
      <TableBody>
      {table.getRowModel().rows?.length ? (
            table.getRowModel().rows.map((row) => (
              <TableRow
                key={row.id}
          data-state={row.getIsSelected() && "selected"}
            >
            {row.getVisibleCells().map((cell) => (
                <TableCell key={cell.id}>
                  {flexRender(cell.column.columnDef.cell, cell.getContext())}
            </TableCell>
  ))}
  </TableRow>
  ))
  ) : (
      <TableRow>
        <TableCell colSpan={columns.length} className="h-24 text-center">
      No results.
    </TableCell>
    </TableRow>
  )}
    </TableBody>
    </Table>
    </div>

      <div className="flex flex-1 ">
        <div className="flex-1 text-sm py-6 text-muted-foreground">
          {table.getFilteredSelectedRowModel().rows.length} of{" "}
          {table.getFilteredRowModel().rows.length} row(s) selected.
        </div>

        <div className="flex items-center justify-end space-x-2 py-4">
          <Button
            variant="outline"
            size="sm"
            onClick={() => table.previousPage()}
            disabled={!table.getCanPreviousPage()}
          >
            Previous
          </Button>
          <Button
            variant="outline"
            size="sm"
            onClick={() => table.nextPage()}
            disabled={!table.getCanNextPage()}
          >
            Next
          </Button>
        </div>
      </div>
    </div>
  )
}
