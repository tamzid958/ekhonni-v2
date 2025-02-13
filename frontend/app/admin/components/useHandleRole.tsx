import React from 'react';
import { axiosInstance } from '@/data/services/fetcher';
import { toast } from 'sonner';
import {roleMapping} from '../hooks/useRoles';

export const handleChangeUserRole = async (userId: string, newRoleId:number, userToken: string | undefined) => {

  if (!userToken) {
    toast.error("Unauthorized! Please log in again.");
    return;
  }

  try {
    const response = await axiosInstance.post(
      `/api/v2/admin/user/${userId}/assign/role/${newRoleId}`,

      {
        headers: {
          Authorization: `Bearer ${userToken}`,
        },
      }
    );
    if(response.status == 200)
    {
      const role = roleMapping[newRoleId];
      toast.success(`User role updated to ${role} successfully!`);
    }

    return response.data;

  } catch (error) {
    console.error("Error updating user role:", error);
    toast.error("Failed to update user role.");
  }
};
