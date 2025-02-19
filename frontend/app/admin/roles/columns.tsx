"use client"
import React, { useEffect, useState } from 'react';
import { ColumnDef } from "@tanstack/react-table"
import {
  Ban,
  CheckCircle,
  Hourglass,
  MoreHorizontal,
  Plus,
  ShieldCheck,
  ShieldIcon,
  Trash2,
  UserIcon,
} from 'lucide-react';
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

import { X } from 'lucide-react';
import { Select } from '@/components/ui/select';
import { FaSearch } from 'react-icons/fa';

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
    accessorKey: 'roles',
    header: 'Assigned To',
    cell: ({ row }) => {
      const rolesData = row.original.roles || []; // Get the roles or default to an empty array
      const [selectedRoles, setSelectedRoles] = useState<string[]>(rolesData); // State for selected roles
      const [isDropdownOpen, setIsDropdownOpen] = useState<boolean>(false); // Toggle dropdown
      const [searchQuery, setSearchQuery] = useState<string>(''); // Search query for filtering roles

      const availableRoles = ['ADMIN', 'USER', 'SUPER_ADMIN', 'Viewer']; // Available roles to assign

      // Handle the addition of selected roles
      const handleRolesChange = (role: string) => {
        setSelectedRoles((prev) => [...prev, role]);
      };

      // Remove a selected role
      const handleRemoveRole = (role: string) => {
        setSelectedRoles((prev) => prev.filter((r) => r !== role));
      };

      // Toggle dropdown visibility
      const toggleDropdown = () => setIsDropdownOpen((prev) => !prev);

      // Handle search query update
      const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchQuery(event.target.value);
      };

      // Filter available roles based on the search query
      const filteredRoles = availableRoles.filter((role) =>
        role.toLowerCase().includes(searchQuery.toLowerCase())
      );

      return (
        <div className='flex relative'>
          {/* Display selected roles as badges */}
          <div className="flex flex-wrap gap-2 ">
            {selectedRoles.length > 0 ? (
              selectedRoles.map((role, index) => (
                <Badge key={index} className="flex items-center text-xs bg-gray-200 text-gray-800">
                  {role}
                  <Button
                    variant="link"
                    className="ml-1 p-0"
                    onClick={() => handleRemoveRole(role)} // Remove role on click
                  >
                    <X className="h-4 w-4 text-red-500" />
                  </Button>
                </Badge>
              ))
            ) : (
              <span>No roles assigned</span>
            )}
          </div>

          {/* Plus icon to trigger the dropdown */}
          <div className="flex min-h-6 min-w-8 max-h-10 max-w-10 rounded-xl mx-4 bg-blue-500 justify-center shadow-xl items-center" onClick={toggleDropdown}>
            <Plus className="h-5 w-5 "  />
          </div>
          {/* Dropdown with search functionality */}
          {isDropdownOpen && (
            <div className="absolute float-end bg-white border rounded mt-2 w-64 shadow-md z-10" style={{ top: '100%', left: 0 }} >
              {/* Search input */}
              <div className="flex items-center p-2 border-b">
                <FaSearch className="h-4 w-4 text-gray-500" />
                <input
                  type="text"
                  className="ml-2 p-1 w-full border-none focus:outline-none"
                  placeholder="Search roles"
                  value={searchQuery}
                  onChange={handleSearchChange}
                />
              </div>

              {/* Display filtered roles in the dropdown */}
              <div className="max-h-40 ">
                {filteredRoles.length > 0 ? (
                  filteredRoles.map((role, index) => (
                    <div
                      key={index}
                      className={`flex items-center justify-between p-2 cursor-pointer ${selectedRoles.includes(role) ? 'bg-gray-200 text-gray-500' : 'hover:bg-gray-100'}`}
                      onClick={() => {
                        if (!selectedRoles.includes(role)) {
                          handleRolesChange(role); // Only trigger handleRolesChange if the role is not selected
                        }
                      }}
                      style={{ pointerEvents: selectedRoles.includes(role) ? 'none' : 'auto' }} // Disable click events for selected roles
                    >
                      <span>{role}</span>
                      {selectedRoles.includes(role) && (
                        <CheckCircle className="h-4 w-4 text-green-500" />
                      )}
                    </div>
                  ))
                ) : (
                  <div className="p-2 text-gray-500">No roles found</div>
                )}
              </div>
            </div>
          )}
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
