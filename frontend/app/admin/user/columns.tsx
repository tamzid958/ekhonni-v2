"use client"
import React, { useState } from 'react';
import { ColumnDef } from "@tanstack/react-table"
import { LogOut, MoreHorizontal, ShieldIcon, UserIcon } from 'lucide-react';
import { Button } from "@/components/ui/button"
import { ArrowUpDown } from "lucide-react"
import { Checkbox } from "@/components/ui/checkbox"

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu"
import { Badge } from '@/components/ui/badge';
import { useSession } from 'next-auth/react';

import DeleteConfirmationDialog from '../components/useDeleteUser';
import { mutate } from 'swr';
import { users } from '@/data/users';




export type User = {
  id: string
  name: string
  email: string
  role: "Admin" | "User"
  image: string
  status: "active" | "banned" | "disabled" | "failed"
  CreatedAt: string
  UpdatedAt: string
  address: string

}

export const columns: ColumnDef<User>[] = [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={
          table.getIsAllPageRowsSelected() ||
          (table.getIsSomePageRowsSelected() && "indeterminate")
        }
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "name",
    header: "Name",
  },
  {
    accessorKey: "email",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Email
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
  },
  {
    accessorKey: "role",
    header: "Role",
    cell: ({ row }) => {
      const role = "Admin"; //row.getValue("role")
      return (
        <div className="flex items-start">
          {role === "Admin" ? (
            <ShieldIcon className="h-4 w-4 mr-1" />
          ) : (
            <UserIcon className="h-4 w-4 mr-1" />
          )}
          <Badge className="bg-green-500">{role}</Badge>
        </div>
      )
    },
  },
  {
    accessorKey: "address",
    header: "Address",
  },
  {
    accessorKey: "status",
    header: "Status",
    cell : ({ row }) => {
      const status = "active"; // row.getValue("status")
      let color = "gray"
      if (status === "active") {
        color = "green"
      } else if (status === "banned") {
        color = "red"
      }
      return (
        <Badge className={`bg-${color}-500`}>

          {status}
        </Badge>)
    }
  },
  {
    accessorKey: "createdAt",
    header: "Created At",
    cell: ({ row }) => {
      const date = new Date(row.getValue("createdAt"))
      const formatted = date.toDateString()
      return <div className="text-right font-medium">{formatted}</div>
    }
  },
  {
    accessorKey: "updatedAt",
    header: "Updated At",
    cell: ({ row }) => {
      const date = new Date(row.getValue("updatedAt"))
      const formatted = date.toDateString()
      return <div className="text-right font-medium">{formatted}</div>
    }
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const User = row.original
      const { data: session } = useSession();
      const userToken = session?.user?.token;
      const handleDeleteSuccess = (deletedUserId) => {
        mutate('/api/users', users.filter(user => User.id !== deletedUserId), false);

      };

      return (
        <DropdownMenu>
          <DropdownMenuTrigger asChild>
            <Button variant="ghost" className="h-8 w-8 p-0">
              <span className="sr-only">Open menu</span>
              <MoreHorizontal className="h-4 w-4" />
            </Button>
          </DropdownMenuTrigger>
          <DropdownMenuContent align="end">
            <DropdownMenuLabel>Actions</DropdownMenuLabel>
            <DropdownMenuItem
              onClick={() => navigator.clipboard.writeText(User.id)}
            >
             Copy User ID
            </DropdownMenuItem>
            <DropdownMenuSeparator />
            <DropdownMenuItem
              onClick={() => alert(`Viewing user ${User.name}`)}
            >View User Public Details </DropdownMenuItem>

            <DropdownMenuSeparator />

            <DropdownMenuItem
              onClick={() => alert(`Editing user ${User.name}`)}
            >Change User Role</DropdownMenuItem>

            <DropdownMenuSeparator />

            <DropdownMenuItem>
              <DeleteConfirmationDialog
                userId={User.id}
                userToken={userToken}
                onDeleteSuccess={handleDeleteSuccess}
              />
            </DropdownMenuItem>

            <DropdownMenuSeparator />
          </DropdownMenuContent>
        </DropdownMenu>
      )
    },
  },

]
