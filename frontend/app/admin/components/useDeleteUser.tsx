"use client";

import React, { useState } from 'react';
import { Button } from "@/components/ui/button";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from '@/components/ui/alert-dialog';
import { axiosInstance } from '@/data/services/fetcher';
import { mutate } from 'swr';

interface DeleteConfirmationDialogProps {
  userId: string;
  userToken: string;
  onDeleteSuccess: (userId: string) => void;
}

const DeleteConfirmationDialog: React.FC<DeleteConfirmationDialogProps> = ({ userId, userToken, onDeleteSuccess }) => {
  const [open, setOpen] = useState(false);

  const handleDelete = async () => {
    try {
      const res = await axiosInstance.delete(`/api/v2/user/${userId}`, {
        headers: {
          Authorization: `Bearer ${userToken}`,
        },

      });
      if (res.status === 200) {
        console.log("User deleted successfully");
        setOpen(false);
        mutate(`/api/v2/admin/users`);
        onDeleteSuccess(userId);
      }
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  return (
    <>
      <div  onClick={() => setOpen(true)}>
        Delete User
      </div>
      <AlertDialog open={open} onOpenChange={setOpen}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Are you sure you want to delete this user?</AlertDialogTitle>
            <AlertDialogDescription>
              This action cannot be undone.
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => setOpen(false)}>Cancel</AlertDialogCancel>
            <AlertDialogAction onClick={handleDelete}>Delete</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};

export default DeleteConfirmationDialog;
