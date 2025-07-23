import useSWR from 'swr';

const fetcher = (url: string) => fetch(`http://localhost:8080${url}`).then(res => res.json());

export function useFilterProducts(searchTerm: string, sortBy: string, divisions: string[] = [], conditions: string[] = [], value: [number, number]) {
  const query = new URLSearchParams();
  if (searchTerm) query.append('searchTerm', searchTerm);
  if (sortBy) query.append('sortBy', sortBy);
  if (value && value[0] !== null && value[1] !== null) {
    query.append('minPrice', value[0]);
    query.append('maxPrice', value[1]);
  }
  divisions.forEach((division) => query.append('division', division));
  conditions.forEach((condition) => query.append('condition', condition));

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