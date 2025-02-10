"use client"
import React, { useEffect, useState } from 'react';
import { ColumnDef } from "@tanstack/react-table"
import { Ban, Hourglass, MoreHorizontal, ShieldCheck, ShieldIcon, Trash2, UserIcon } from 'lucide-react';
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
import { Toaster } from 'sonner';
import { handleBlockUser, handleUnblockUser } from '../components/useHandleBlock';
import { handleChangeUserRole } from '../components/useHandleRole';
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
export type blockedUser = {
  id: string
  name: string
  email: string
  address: string
  blockedBy: string
  blockedReason:string
  unblockAt: string
  roleName: "ADMIN" | "USER" | "SUPER_ADMIN"
  image: string
  status: "ACTIVE" | "UNVERIFIED" | "DELETED" | "BLOCKED"
  CreatedAt: string
  UpdatedAt: string
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
        User.roleName === "SUPER_ADMIN"
          ? "bg-red-500"
          : User.roleName === "ADMIN"
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
      const User = row.original;
      const router = useRouter();
      const { data: session } = useSession();
      const userToken = session?.user?.token;
      const [isBlocked, setIsBlocked] = useState(false);
      const [isMenuOpen, setIsMenuOpen] = useState(false);

      const [isRoleDialogOpen, setIsRoleDialogOpen] = useState(false);
      const [isBlockDialogOpen, setIsBlockDialogOpen] = useState(false);

      const [selectedRole, setSelectedRole] = useState<number>(
        reverseRoleMapping[User.roleName] || 0
      );
      const [blockReason, setBlockReason] = useState('');
      const [blockDuration, setBlockDuration] = useState<string>('TEMPORARY_7_DAYS');
      const blockDurations = ["TEMPORARY_7_DAYS", "TEMPORARY_30_DAYS"];

      useEffect(() => {
        setIsBlocked(User.status === "BLOCKED");
      }, [User.status]);

      return (
        <div>
          {/* Change Role Dialog */}
          <Dialog open={isRoleDialogOpen} onOpenChange={setIsRoleDialogOpen}>
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
                    value={selectedRole}
                    onChange={(e) => setSelectedRole(Number(e.target.value))}
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
                <Button onClick={() => setIsRoleDialogOpen(false)}>Cancel</Button>
                <Button onClick={() => {
                  setIsRoleDialogOpen(false);
                  handleChangeUserRole(User.id, selectedRole, userToken)
                }} type="submit">
                  Save changes
                </Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>

          {/* Block User Dialog */}
          <Dialog open={isBlockDialogOpen} onOpenChange={setIsBlockDialogOpen}>
            <DialogContent className="sm:max-w-[425px]">
              <DialogHeader>
                <DialogTitle>Block User</DialogTitle>
                <DialogDescription>
                  Block {User.name}. Please provide the reason and time period.
                </DialogDescription>
              </DialogHeader>
              <div className="grid gap-4 py-4">
                {/* Reason Input */}
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="blockReason" className="text-right">Reason</Label>
                  <input
                    type="text"
                    id="blockReason"
                    className="col-span-3 border rounded p-2"
                    value={blockReason}
                    onChange={(e) => setBlockReason(e.target.value)}
                  />
                </div>

                {/* Duration Dropdown */}
                <div className="grid grid-cols-4 items-center gap-4">
                  <Label htmlFor="blockDuration" className="text-right">Duration</Label>
                  <select
                    id="blockDuration"
                    className="col-span-3 border rounded p-2"
                    value={blockDuration}
                    onChange={(e) => setBlockDuration(e.target.value)}
                  >
                    {blockDurations.map((duration) => (
                      <option key={duration} value={duration}>
                        {duration}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <DialogFooter>
                <Button onClick={() => setIsBlockDialogOpen(false)}>Cancel</Button>
                <Button onClick={() => {
                  setIsBlockDialogOpen(false);
                  handleBlockUser(User.id, blockReason, blockDuration, userToken);
                }} type="submit">
                  Block User
                </Button>
              </DialogFooter>
            </DialogContent>
          </Dialog>

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
              <DropdownMenuItem
                onClick={() => navigator.clipboard.writeText(User.id)}
              >
                Copy User ID
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem
                onClick={() => router.push(`/admin/user/${User.id}`)}
              >
                View User Public Details
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem onClick={() => {
                setIsRoleDialogOpen(true);
                setIsMenuOpen(false);
              }}>
                Change Role
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              {!isBlocked && (
                <DropdownMenuItem onClick={() => {
                  setIsBlockDialogOpen(true);
                  setIsMenuOpen(false);
                }}>
                  Block User
                </DropdownMenuItem>
              )}
              {isBlocked && (
                <DropdownMenuItem onClick={() => handleUnblockUser(User.id, userToken)}>
                  Unblock User
                </DropdownMenuItem>
              )}
              <DropdownMenuSeparator />
            </DropdownMenuContent>
          </DropdownMenu>

          <Toaster />
        </div>
      )
    },
  }

]


export const blockedUserColumns: ColumnDef<>[] = [
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
    enableSorting: true,
    enableHiding: true,
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
    accessorKey: "address",
    header: "Address",
  },
  {
    accessorKey: "blockedAt",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Blocked At
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
    cell: ({ row }) => {
      const date = new Date(row.getValue("blockedAt"))
      const formatted = date.toDateString()
      return <div className="text-center font-medium">{formatted}</div>
    }
  },
  {
    accessorKey: "blockedBy",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
          Blocked By
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
    cell: ({ row }) => {
      const User = row.original;
      const formatted = User.blockedBy;
      return <div className="text-center font-medium">{formatted}</div>
    }
  },

  {
    accessorKey: "blockedReason",
    header: "Block Reason",
  },
  {
    accessorKey: "unblockAt",
    header: ({ column }) => {
      return (
        <Button
          variant="ghost"
          onClick={() => column.toggleSorting(column.getIsSorted() === "asc")}
        >
         Unblock At
          <ArrowUpDown className="ml-2 h-4 w-4" />
        </Button>
      )
    },
    cell: ({ row }) => {
      const date = new Date(row.getValue("unblockAt"))
      const formatted = date.toDateString()
      return <div className="text-center font-medium">{formatted}</div>
    }
  },
  {
    id: "actions",
    cell: ({ row }) => {
      const User = row.original;
      const { data: session } = useSession();
      const userToken = session?.user?.token;
      const [isMenuOpen, setIsMenuOpen] = useState(false);
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
              <DropdownMenuItem
                onClick={() => navigator.clipboard.writeText(User.id)}
              >
                Copy User ID
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem
                onClick={() => alert(`Viewing user ${User.name}`)}
              >
                View User Public Details
              </DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem onClick={() => handleUnblockUser(User.id, userToken)}>
                  Unblock User
              </DropdownMenuItem>
              <DropdownMenuSeparator />
            </DropdownMenuContent>
          </DropdownMenu>
          <Toaster />
        </div>
      )
    },
  }

]


