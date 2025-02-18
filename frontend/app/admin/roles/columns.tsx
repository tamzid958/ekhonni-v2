"use client"
import React, { useEffect, useState } from 'react';
import { ColumnDef } from "@tanstack/react-table"
import { Ban, Hourglass, MoreHorizontal, ShieldCheck, ShieldIcon, Trash2, UserIcon } from 'lucide-react';
import { Button } from "@/components/ui/button"
import { ArrowUpDown } from "lucide-react"
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
import { Toaster } from 'sonner';
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import { Label } from '@/components/ui/label';
import { allRolesList, reverseRoleMapping } from '../hooks/useRoles';
import { useRouter } from "next/navigation";
import { FaUser, FaUsers } from 'react-icons/fa6';
import { RoleDialog } from '../components/RoleDialog';
import {RoleSelector} from '../components/RoleSelector'

export type Role = {
  id: number
  name: string
  description:string
  status: "ACTIVE" | "ARCHIVED"
  CreatedAt: string
  UpdatedAt: string
  NoOfUsers: number
}

export type Privilege = {
  id: number
  name: string
  description:string
  type: string
  roles: string[]
  httpMethod: string
  endpoint: string

}

export const columns: ColumnDef<Role>[] = [

  {
    accessorKey: "id",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          ID
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
  },
  {
    accessorKey: "name",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
         Name
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
  },
  {
    accessorKey: "description",
    header: "Description",
  },
  {
    accessorKey: "status",
    header: "Status",
    cell: ({ row }) => {
      const Role = row.original;
      const icon =
        Role.status === "ACTIVE" ? (
          <ShieldCheck className="h-4 w-4 mr-1 text-green-500" />
        ) : Role.status === "ARCHIVED" ? (
          <Trash2 className="h-4 w-4 mr-1 text-black" />
        ) : (
          <Hourglass className="h-4 w-4 mr-1 text-blue-500" />
        );

      const statusColor =
        Role.status === "ACTIVE"
          ? "bg-green-500"
            : Role.status === "ARCHIVED"
              ? "bg-black"
              : "bg-blue-500";
      return (
        <div className="flex align-middle">
          {icon}
          <Badge className={statusColor}>{Role.status}</Badge>
        </div>
      );
    },
  },
  {
    accessorKey: 'NoOfUsers',
    header: 'No. Of Users',
    cell: ({ getValue }) => {
      const noOfUsers = getValue();

      return (
        <div className="flex justify-center items-center text-center">
          {noOfUsers > 1 ? (
            <FaUsers className="h-5 w-5 text-blue-500 mr-2" />
          ) : (
            <FaUser className="h-5 w-5 text-blue-500 mr-2" />
          )}
          <span>{noOfUsers}</span>
        </div>
      );
    },
  },
  {
    accessorKey: "createdAt",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Created At
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
    cell: ({ row }) => {
      const date = new Date(row.getValue("createdAt"))
      const formatted = date.toDateString()
      return <div className="text-center font-medium">{formatted}</div>
    }
  },
  {
    accessorKey: "updatedAt",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Updated At
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
    cell: ({ row }) => {
      const date = new Date(row.getValue("updatedAt"))
      const formatted = date.toDateString()
      return <div className="text-center font-medium">{formatted}</div>
    }
  },

  {
    id: "actions",
    cell: ({ row }) => {
      const Role = row.original;
      const router = useRouter();
      const { data: session } = useSession();
      const userToken = session?.user?.token;
      const [isMenuOpen, setIsMenuOpen] = useState(false);
      const [isRoleDialogOpen, setIsRoleDialogOpen] = useState(false);

      const handleEditRole = (role: {name: string, description: string}) =>{
        setIsRoleDialogOpen(true)
        setIsMenuOpen(false);

      }
      return (
        <div>
          {/* Dropdown Menu */}
          <DropdownMenu open={isMenuOpen} onOpenChange={setIsMenuOpen}>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" className="h-8 w-8 p-0">
                <span className="sr-only">Open menu</span>
                <MoreHorizontal className="h-4 w-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuLabel>Actions</DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuItem
                onClick={() => router.push(`/admin/roles/${Role.id}`)}
              >
                Add/View Privileges
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem onClick={() => {
                setIsRoleDialogOpen(true);
                setIsMenuOpen(false);
              }}>
                Change Role
              </DropdownMenuItem>
              <DropdownMenuSeparator />
             <DropdownMenuItem onClick={() => handleEditRole(Role)}>
               Edit Role
             </DropdownMenuItem>
              <DropdownMenuSeparator />
            </DropdownMenuContent>
          </DropdownMenu>
          <RoleDialog
            token={userToken}
            isOpen={isRoleDialogOpen}
            onClose={() => setIsRoleDialogOpen(false)} onSave={(updatedRole) =>{
                      console.log("Role Updated", updatedRole);
                      }}
            existingRole = {Role}
           />
          <Toaster />
        </div>
      )
    },
  }

]

function RoleSelector(props: {
  initialRoles: string[],
  onChange: (updatedRoles: string[]) => void,
  availableRoles: string[]
}) {
  return null;
}

export const privilegeColumns: ColumnDef<Privilege>[] = [

  {
    accessorKey: "name",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Name
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
  },
  {
    accessorKey: "description",
    header: "Description",
  },
  {
    accessorKey: "httpMethod",
    header: "HTTP Method",
    cell: ({ row }) => {
      const httpMethod = row.original.httpMethod;
      const getBadgeColor = (method: string) => {
        switch (method) {
          case 'GET':
            return 'bg-blue-500';
          case 'POST':
            return 'bg-green-500';
          case 'PATCH':
            return 'bg-yellow-500';
          case 'DELETE':
            return 'bg-red-500';
          default:
            return 'bg-gray-500';
        }
      };
      return (
        <Badge className={`text-white px-3 py-1  text-sm  ${getBadgeColor(httpMethod)}`}>
          {httpMethod}
        </Badge>
      );
    },
  },
  {
    accessorKey: "endpoint",
    header: "API End Point",
  },
  {
    accessorKey: "roles",
    header: "Assigned To",
    cell: ({ row }) => {

      const RolesData = row.original.roles;

      const [roles, setRoles] = useState<string[]>(RolesData || []);
      const availableRoles = ['ADMIN', 'USER', 'SUPER_ADMIN', 'Viewer'];

      const handleRolesChange = (updatedRoles: string[]) => {
        setRoles(updatedRoles);
        // You can send the updated roles to your backend or manage state as needed
        // For example: updateUserRoles(row.original.id, updatedRoles);
        console.log('Updated Roles:', updatedRoles);

      };
      return (
        <div>
          <RoleSelector
            initialRoles={roles}
            onChange={handleRolesChange}
            availableRoles={availableRoles}
          />
        </div>
      );
    },
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const Role = row.original;
      const router = useRouter();
      const { data: session } = useSession();
      const userToken = session?.user?.token;
      const [isMenuOpen, setIsMenuOpen] = useState(false);
      const [isRoleDialogOpen, setIsRoleDialogOpen] = useState(false);

      const handleEditRole = (role: {name: string, description: string}) =>{
        setIsRoleDialogOpen(true)
        setIsMenuOpen(false);

      }
      return (
        <div>
          {/* Dropdown Menu */}
          <DropdownMenu open={isMenuOpen} onOpenChange={setIsMenuOpen}>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" className="h-8 w-8 p-0">
                <span className="sr-only">Open menu</span>
                <MoreHorizontal className="h-4 w-4" />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuLabel>Actions</DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuItem
                onClick={() => router.push(`/admin/roles/${Role.id}`)}
              >
                Add/View Privileges
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem onClick={() => {
                setIsRoleDialogOpen(true);
                setIsMenuOpen(false);
              }}>
                Change Role
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem onClick={() => handleEditRole(Role)}>
                Edit Role
              </DropdownMenuItem>
              <DropdownMenuSeparator />
            </DropdownMenuContent>
          </DropdownMenu>
          <RoleDialog
            token={userToken}
            isOpen={isRoleDialogOpen}
            onClose={() => setIsRoleDialogOpen(false)} onSave={(updatedRole) =>{
            console.log("Role Updated", updatedRole);
          }}
            existingRole = {Role}
          />
          <Toaster />
        </div>
      )
    },
  }
]
