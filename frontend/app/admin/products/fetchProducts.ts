export interface Data {
  id: string;
  price: number;
  title: string;
  subTitle: string;
  description: string;
  status: string;
  seller: {
    id: string;
    name: string;
  };
  condition: string;
  category: {
    id: number;
    name: string;
  };
  images: {
    imagePath: string;
  }[];
  label: string;
}

export async function fetchProducts(page: number = 1): Promise<Data[]> {
  try {
    const url = `http://localhost:8080/api/v2/admin/product/filter?page=${encodeURIComponent(page)}`;
    const response = await fetch(url, { cache: 'no-store' });
    if (!response.ok) {
      throw new Error('Failed to fetch products');
    }
    const json = await response.json();
    return json.data.content;
  } catch (error) {
    console.error('Error fetching products:', error);
    return [];
  }
}
