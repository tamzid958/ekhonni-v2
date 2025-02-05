import useSWR from 'swr';

const fetcher = (url: string) => fetch(`http://localhost:8080${url}`).then(res => res.json());

export function useFilterProducts(searchTerm: string, sortBy: string, divisions: string[] = []) {
  const query = new URLSearchParams();
  if (searchTerm) query.append('searchTerm', searchTerm);
  if (sortBy) query.append('sortBy', sortBy);
  divisions.forEach((division) => query.append('division', division));

  console.log(query);
  const { data, error, isLoading } = useSWR(
    query.toString() ? `/api/v2/product/filter?${query.toString()}` : null,
    fetcher,
  );

  return {
    products: data?.data?.content || [],
    error,
    isLoading,
  };
}