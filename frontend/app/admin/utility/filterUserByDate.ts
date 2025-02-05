
export const filterUsersByDate = (users: any[], period:"all" | "week" | "month" | "year"): any[] => {
  const now = new Date();
  let startDate: Date;

  switch (period) {
    case "week":
      startDate = new Date(now.setDate(now.getDate() - 7)); // Last 7 days
      break;
    case "month":
      startDate = new Date(now.setMonth(now.getMonth() - 1)); // Last 1 month
      break;
    case "year":
      startDate = new Date(now.setFullYear(now.getFullYear() - 1)); // Last 1 year
      break;
    default:
      startDate = new Date(0); // "All Time"
      break;
  }
  return users.filter((user) => new Date(user.createdAt) >= startDate);
};
