
export const calculatePercentageChange = (currentCount: number, totalCount: number) => {
  if (totalCount === 0) return 0;
  return ((currentCount / totalCount) * 100).toFixed(2);
};
