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
