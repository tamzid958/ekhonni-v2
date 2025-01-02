import { fetchAllProducts, fetchProductById, fetchProductsByCategory } from '../../_data/products';

export async function GET(req: Request) {
  const { searchParams } = new URL(req.url);
  const category = searchParams.get('category');
  const id = searchParams.get('id');

  try {
    // Fetch products by ID
    if (id) {
      const product = fetchProductById(id);
      if (!product) {
        return new Response('Product not found', { status: 404 });
      }
      return new Response(JSON.stringify(product), {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
      });
    }

    // Fetch products by category
    if (category) {
      const products = fetchProductsByCategory(category);
      return new Response(JSON.stringify(products), {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
      });
    }

    // Fetch all products
    const allProducts = fetchAllProducts();
    return new Response(JSON.stringify(allProducts), {
      status: 200,
      headers: { 'Content-Type': 'application/json' },
    });
  } catch (error) {
    console.error('Error handling API request:', error);
    return new Response('Internal Server Error', { status: 500 });
  }
}
