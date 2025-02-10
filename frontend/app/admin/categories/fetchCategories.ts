interface CategoryNode {
  name: string;
  subCategories: string[];
  chainCategories: string[];
}

export async function fetchCategories(category: string = 'All', token: string): Promise<CategoryNode[]> {
  const url =
    category === 'All'
      ? 'http://localhost:8080/api/v2/category/all'
      : `http://localhost:8080/api/v2/category/${encodeURIComponent(category)}/subcategories`;

  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      cache: 'no-store',
    });

    console.log(response.body);

    if (!response.ok) {
      throw new Error('Failed to fetch categories');
    }

    const json = await response.json();
    return category === 'All' ? json.data : [json.data];
  } catch (error) {
    console.error('Error fetching categories:', error);
    return []; // Return an empty array on error
  }
}
