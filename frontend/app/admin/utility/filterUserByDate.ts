export function getUserStats(users, period = "week") {
  const now = new Date();
  let startOfCurrentPeriod, startOfPreviousPeriod, endOfPreviousPeriod;

  if (period === "week") {
    startOfCurrentPeriod = new Date(now.setDate(now.getDate() - now.getDay())); // Start of this week
    startOfPreviousPeriod = new Date(startOfCurrentPeriod);
    startOfPreviousPeriod.setDate(startOfPreviousPeriod.getDate() - 7); // Start of last week
    endOfPreviousPeriod = new Date(startOfCurrentPeriod);
  } else if (period === "month") {
    startOfCurrentPeriod = new Date(now.getFullYear(), now.getMonth(), 1); // Start of this month
    startOfPreviousPeriod = new Date(now.getFullYear(), now.getMonth() - 1, 1); // Start of last month
    endOfPreviousPeriod = new Date(now.getFullYear(), now.getMonth(), 0); // End of last month
  } else if (period === "year") {
    startOfCurrentPeriod = new Date(now.getFullYear(), 0, 1); // Start of this year
    startOfPreviousPeriod = new Date(now.getFullYear() - 1, 0, 1); // Start of last year
    endOfPreviousPeriod = new Date(now.getFullYear(), 0, 0); // End of last year
  } else if (period === "all") {

    return {
      userCount: users.length,
      growthPercentage: users.length > 0 ? 100 : 0
    };
  } else {
    throw new Error("Invalid period. Choose 'week', 'month', 'year', or 'all'.");
  }


  const usersCurrentPeriod = users.filter(user => new Date(user.createdAt) >= startOfCurrentPeriod);


  const usersPreviousPeriod = users.filter(user =>
    new Date(user.createdAt) >= startOfPreviousPeriod && new Date(user.createdAt) < endOfPreviousPeriod
  );

  const currentCount = usersCurrentPeriod.length;
  const previousCount = usersPreviousPeriod.length;

  console.log(`Stats for ${period}`);
  console.log("Current Period User Count:", currentCount);
  console.log("Previous Period User Count:", previousCount);


  let growthPercentage = 0;
  if (previousCount === 0) {
    growthPercentage = currentCount > 0 ? 100 : 0;
  } else {
    growthPercentage = ((currentCount - previousCount) / previousCount) * 100;
  }

  return {
    userCount: currentCount,
    growthPercentage: growthPercentage.toFixed(2)
  };
}
