

import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';
import { Role } from '../roles/columns';
import { useUsers } from './useUser';

export let roleMapping: Record<number, string> = {}; // Maps roleId → roleName
export let reverseRoleMapping: Record<string, number> = {}; // Maps roleName → roleId
export let allRolesList: { id: number; name: string; description: string | null }[] = []; // Stores roles
export let processedRoles: Role[] | null[] = [];
export let userGroups: Record<number, any[]> = {}; // Tracks users by role

export const setAllRoles = (roles: { id: number; name: string; description: string | null }[]) => {
  allRolesList = roles;

  roleMapping = roles.reduce((acc, role) => {
    acc[role.id] = role.name;
    return acc;
  }, {} as Record<number, string>);

  reverseRoleMapping = roles.reduce((acc, role) => {
    acc[role.name] = role.id;
    return acc;
  }, {} as Record<string, number>);
};

export const processRoles = (roles: any[]): Role[] => {
  return roles.map((role) => {
    let status = "ACTIVE";
    if (role.deletedAt) status = "ARCHIVED";

    const roleUsers = userGroups[role.id] || [];
    const totalUsers = roleUsers.length;
    return {
      ...role,
      status,
      NoOfUsers: totalUsers,
    };
  });
};

export const useRoles = (userId: string, userToken: string) => {
  const { data: roleData, error, isLoading } = useSWR(
    userId ? `/api/v2/role/` : null,
    (url) => fetcher(url, userToken)
  );

  const { allUsers } = useUsers(userId, userToken);

  processedRoles = roleData?.content ? processRoles(roleData.content) : [];

  if (roleData?.content) {
    setAllRoles(
      roleData.content.map((role: any) => ({
        id: role.id,
        name: role.name,
        description: role.description,
      }))
    );
  }
  userGroups = allRolesList.reduce((acc, role) => {
    const roleUsers = allUsers?.filter((user: any) => user.roleName === role.name) || [];
    acc[role.id] = roleUsers;

    return acc;
  }, {} as Record<number, any[]>);

  const totalAdmins = allUsers.filter((user: any) => user.roleName === "ADMIN").length;

  return {
    processedRoles,
    totalAdmins,
    allRolesList,
    roleMapping,
    reverseRoleMapping,
    isLoading,
    error,
  };
};
