import useSWR from 'swr';
import fetcher from '@/data/services/fetcher';


export let roleMapping: Record<number, string> = {}; // Maps roleId → roleName
export let reverseRoleMapping: Record<string, number> = {}; // Maps roleName → roleId
export let allRolesList: { id: number; name: string; description: string | null }[] = []; // Stores roles

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

export const useRoles = (userId: string, userToken: string) => {
  const { data: roleData, error, isLoading } = useSWR(
    userId ? `/api/v2/role/` : null,
    (url) => fetcher(url, userToken)
  );

  if (roleData?.content) {
    setAllRoles(
      roleData.content.map((role: any) => ({
        id: role.id,
        name: role.name,
        description: role.description,
      }))
    );
  }
  return { allRolesList, roleMapping, reverseRoleMapping, isLoading, error };
};


