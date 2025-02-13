import useSWR from "swr";
import fetcher from "@/data/services/fetcher";

type User = {
  id: string;
  name: string;
  email: string;
  roleName: string;
  verified: boolean;
  isBlocked: boolean;
  profileImage: string;
  createdAt: string;
  updatedAt: string;
  deletedAt?: string | null;
  address: string;
};

export const processUsers = (users: any[]): User[] => {
  return users.map(user => {
    let status = "ACTIVE";
    if (!user.verified) {
      status = "UNVERIFIED";
    } else if (user.deletedAt) {
      status = "DELETED";
    } else if (user.isBlocked) {
      status = "BLOCKED";
    }
    return {
      ...user,
      status,
    };
  });
};

export function useUsers(userId: string, userToken: string) {
  const { data, error: allUserError, isLoading } = useSWR(
    userId ? [`/api/v2/admin/user`, userToken] : null,
    async ([url, token]) => {
      const fetchedData = await fetcher(url, token);
      return fetchedData?.content ?? [];
    }
  );

  const allUsers = processUsers(data ?? []);

  const getUserById = (id: string): User | undefined => {
    return allUsers.find((user: User) => user.id === id);
  };

  return {
    allUsers,
    getUserById,
    isLoading,
    allUserError,
    processUsers,
  };
}
