"use client"
import React, { useEffect, useState } from 'react';
import { ColumnDef } from "@tanstack/react-table"
import { Ban, Hourglass, LogOut, MoreHorizontal, ShieldCheck, ShieldIcon, Trash2, UserIcon } from 'lucide-react';
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
import { axiosInstance } from '@/data/services/fetcher';
import { Toast } from '@/components/ui/toast';
import { toast, Toaster } from 'sonner';
import { handleBlcokUser, handleUnblcokUser } from '../components/useHandleBlock';
import { handleChangeUserRole, openDialog } from '../components/useHandleRole';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from '@/components/ui/dialog';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';
import { allRolesList, reverseRoleMapping } from '@/components/roles';


export type User = {
  id: string
  name: string
  email: string
  roleName: "ADMIN" | "USER" | "SUPER_ADMIN"
  image: string
  status: "ACTIVE" | "UNVERIFIED" | "DELETED" | "BLOCKED"
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
      const User = row.original;
      const icon =
        User.roleName === "ADMIN" || User.roleName === "SUPER_ADMIN" ? (
          <ShieldIcon className="h-4 w-4 mr-1" />
        ) : (
          <UserIcon className="h-4 w-4 mr-1" />
        );


      const roleColor =
        User.roleName === "ADMIN"
          ? "bg-red-500"
          : User.roleName === "SUPER_ADMIN"
            ? "bg-purple-500"
            : "bg-blue-500";

      return (
        <div className="flex items-start">
          {icon}
          <Badge className={`${roleColor}`}>{User.roleName}</Badge>
        </div>
      );
    },
  },
  {
    accessorKey: "address",
    header: "Address",
  },
  {
    accessorKey: "status",
    header: "Status",
    cell: ({ row }) => {
      const User = row.original;
      const icon =
        User.status === "ACTIVE" ? (
          <ShieldCheck className="h-4 w-4 mr-1 text-green-500" />
        ) : User.status === "BLOCKED" ? (
          <Ban className="h-4 w-4 mr-1 text-red-500" />
        ) : User.status === "DELETED" ? (
          <Trash2 className="h-4 w-4 mr-1 text-black" />
        ) : (
          <Hourglass className="h-4 w-4 mr-1 text-blue-500" />
        );
      const statusColor =
        User.status === "ACTIVE"
          ? "bg-green-500"
          : User.status === "BLOCKED"
            ? "bg-red-500"
            : User.status === "DELETED"
              ? "bg-black"
              : "bg-blue-500";

      return (
        <div className="flex items-start">
          {icon}
          <Badge className={statusColor}>{User.status}</Badge>
        </div>
      );
    },
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
      const [isblocked , setIsBlocked] = useState(false);
      const [isDialogOpen, setIsDialogOpen] = useState(false);
      const [isMenuOpen, setIsMenuOpen] = useState(false);
      const [selectedRole, setSelectedRole] = useState<number>(
        reverseRoleMapping[User.roleName] || 0
      );

      useEffect(() => {
        setIsBlocked(User.status === "BLOCKED");
      }, [User.status]);
      return (
        <div>
          <Dialog open={isDialogOpen} onOpenChange={(open) => {
            setIsDialogOpen(open);
            if(!open)
            {
              setTimeout(() => {setIsMenuOpen(false)}, 100);
            }
          }}>
            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>Change Role</DialogTitle>
                <DialogDescription>
                  Select a new role for {User.name}. Click save when you're done.
                </DialogDescription>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="role" className="text-right">Role</Label>
                  <select
                    id="role"
                    className="col-span-3 border rounded p-2"
                    value={selectedRole}  // Only use 'value'
                    onChange={(e) => setSelectedRole(Number(e.target.value))} // Convert to number
                  >
                    {allRolesList.map((role) => (
                      <option key={role.id} value={role.id}>
                        {role.name}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <DialogFooter>
                <Button onClick={() => setIsDialogOpen(false)}>Cancel</Button>
                <Button onClick={() => handleChangeUserRole(User.id, selectedRole, userToken)} type="submit">
                  Save changes
                </Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>


        <DropdownMenu open={isMenuOpen} onOpenChange={setIsMenuOpen}>
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

            <DropdownMenuItem onClick = {() =>
            {
              setIsDialogOpen(true)
              setIsMenuOpen(false);
            }}>
              Change Role
            </DropdownMenuItem>

            <DropdownMenuSeparator />

            {!isblocked && (<DropdownMenuItem
              onClick = {() => handleBlcokUser(User.id, userToken)}
            >
              Block User

            </DropdownMenuItem>)}

            {isblocked && (<DropdownMenuItem
            onClick = {() => handleUnblcokUser(User.id, userToken)}
            >
            Unblock User

          </DropdownMenuItem>)}

            <DropdownMenuSeparator />
          </DropdownMenuContent>

        </DropdownMenu>
          <Toaster/>
        </div>
      )
    },
  },

]
