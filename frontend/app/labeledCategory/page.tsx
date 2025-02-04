import LabeledCategoryRenderer from './labeledCategoryRenderer';

interface Data {
  id: number;
  title: string;
  description: string;
  img: string;
  price: number;
}

interface Props {
  searchParams: {
    category?: string;
    totalPages?: number;
    currentPage?: number;
  };
}

export default async function LabeledCategory({ searchParams }: Props) {
  const category = searchParams.category || 'All';
  const totalPages = searchParams.totalPages || 10;
  const currentPage = searchParams.currentPage || 1;

  const url =
    category === 'All'
      ? `http://localhost:8080/api/v2/product/filter?page=${currentPage}`
      : `http://localhost:8080/api/v2/product/filter?categoryName=${encodeURIComponent(category)}&page=${currentPage}`;

  let products: Data[] = [];
  try {
    const response = await fetch(url, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    const json = await response.json();
    products = json.data.content;
  } catch (error) {
    console.error('Error fetching products:', error);
  }

  return (
    <LabeledCategoryRenderer
      products={products}
      category={category}
      totalPages={totalPages}
      currentPage={currentPage}
    />
  );
}
