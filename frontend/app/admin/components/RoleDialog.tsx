'use client';
//import {useState}

import { Dialog, DialogTrigger, DialogContent, DialogTitle, DialogDescription, DialogFooter } from '@/components/ui/dialog';
import {Input}  from "@/components/ui/input";
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import * as z from 'zod';
import React, { useEffect } from 'react';
import { Textarea } from '@/components/ui/textarea';
import { Button } from '@/components/ui/button';
import { axiosInstance } from '@/data/services/fetcher';

interface Role{
  id?: number
  name: string
  description?: string
}
interface RoleDialogProps {
  token: string
  isOpen: boolean;
  onClose: () => void;
  onSave: (data: Role) => void;
  existingRole?: Role | null;
  existingRoles?: Role[];
}
const roleSchema = z.object((
  {
    name : z.string().min(3, "Role name must be at least 3 characters").max(50, "Role name must not exceed 50 characters"),
    description: z.string().max(200, "Description cannot exceed 200 characters").optional(),
  }
));
export const RoleDialog: React.FC<RoleDialogProps> = ({token, isOpen, onClose, onSave, existingRole = null, existingRoles = [], }) => {
  const {
    register,
    handleSubmit,
    setValue,
    reset,
    formState: {errors},
  } = useForm<Role> ({
    resolver: zodResolver(roleSchema),
    defaultValues: {name: "", description: ""},
  });
  useEffect(() => {
    if(existingRole){
      setValue('name', existingRole.name);
      setValue('description', existingRole.description|| "");
    }
    else{
      reset();
    }
  }, [existingRole, setValue, reset]);
  const validateUniqueName = (name: string) =>{
    if(existingRoles?.some((role) => role.name.toLowerCase() === name.toLowerCase() ))
    {
      return "Role name already exists!";

    }
    return true;
  }
  const onSubmit = async (data: Role) => {
    const nameValidation = validateUniqueName(data.name);
    if(nameValidation != true)
    {
      alert(nameValidation)
      return;
    }
    try{
      let response;
      if(existingRole)
      {
        console.log("adding existing role")

        response = await axiosInstance.patch(`api/v2/role/${existingRole.id}`,
          data,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          });
      }
      else{
        response = await axiosInstance.post('api/v2/role/',
          data,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
        );
      }
      onSave(response.data);
      onClose();
    }catch (error)
    {
      console.log('Error saving role:', error)
      alert('Failed to save role. Please try again');

    }
  }
  return(
    <Dialog open = {isOpen} onOpenChange = {onClose}>
      <DialogContent>
        <DialogTitle>
          Add New Role
        </DialogTitle>
        <form onSubmit={handleSubmit(onSubmit)} className="space-y-4">
          <div>
            <label className="block text-sm font-medium">
              Role Name
            </label>
            <Input
              {...register('name')} placeholder = "Enter Role Name" />
            { errors.name && <p className="text-red-500 text-sm" >{errors.name.message}</p> }
          </div>
          <div>
            <label className="block text-sm font-medium">Description</label>
            <Textarea {...register('description')} placeholder="Enter description (optional)" />
            {errors.description && <p className="text-red-500 text-sm">{errors.description.message}</p>}
          </div>
          <DialogFooter>
            <Button type="button" onClick={onClose} variant="outline" >
              Cancel
            </Button>
            <Button type="submit" onClick={onClose} variant="outline" >
                     Add / Update Role
            </Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  )
}
