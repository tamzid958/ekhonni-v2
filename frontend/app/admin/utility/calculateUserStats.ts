export interface UserStats {
  totalUsers: number;
  totalUsersChange: string;
  totalUsersIncreased: boolean;
  newUsersThisWeek: number;
  newUsersThisMonth: number;
  newUsersThisYear: number;
  newUsersWeekChange: string;
  newUsersMonthChange: string;
  newUsersYearChange: string;
}

const getUsersByTimePeriod = (users: any[], period: "week" | "month" | "year") => {
  const now = new Date();
  return users.filter((user) => {
    const createdAt = new Date(user.createdAt);
    if (period === "week") {
      const oneWeekAgo = new Date();
      oneWeekAgo.setDate(now.getDate() - 7);
      return createdAt >= oneWeekAgo;
    }
    if (period === "month") {
      return createdAt.getMonth() === now.getMonth() && createdAt.getFullYear() === now.getFullYear();
    }
    if (period === "year") {
      return createdAt.getFullYear() === now.getFullYear();
    }
    return false;
  }).length;
};

export const calculateUserStats = (
  currentUsers: any[],
  previousUsers: any[],
): UserStats => {
  const totalUsers = currentUsers.length;

  const previousTotalUsers = previousUsers.length;

  const getChange = (current: number, previous: number): string => {
    if (previous === 0) return "0"; // If no previous data, return 0% change
    return (((current - previous) / previous) * 100).toFixed(2);
  };

  // Calculate user increases by time period
  const newUsersThisWeek = getUsersByTimePeriod(currentUsers, "week");
  const previousUsersThisWeek = getUsersByTimePeriod(previousUsers, "week");
  const newUsersWeekChange = getChange(newUsersThisWeek, previousUsersThisWeek);

  const newUsersThisMonth = getUsersByTimePeriod(currentUsers, "month");
  const previousUsersThisMonth = getUsersByTimePeriod(previousUsers, "month");
  const newUsersMonthChange = getChange(newUsersThisMonth, previousUsersThisMonth);

  const newUsersThisYear = getUsersByTimePeriod(currentUsers, "year");
  const previousUsersThisYear = getUsersByTimePeriod(previousUsers, "year");
  const newUsersYearChange = getChange(newUsersThisYear, previousUsersThisYear);

  return {
    totalUsers,
    totalUsersChange: getChange(totalUsers, previousTotalUsers),
    totalUsersIncreased: totalUsers > previousTotalUsers,
    newUsersThisWeek,
    newUsersThisMonth,
    newUsersThisYear,
    newUsersWeekChange,
    newUsersMonthChange,
    newUsersYearChange,
  };
};
