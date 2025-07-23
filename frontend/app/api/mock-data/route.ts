import {
  fetchAllProducts,
  fetchProductById,
  fetchProductsByCategory,
  fetchProductsByLabel,
} from '../../_data/products';

export async function GET(req: Request) {
  const { searchParams } = new URL(req.url);
  const category = searchParams.get('category');
  const label = searchParams.get('label');
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

    if (category && label) {
      const filteredProducts =
        fetchProductsByCategory(category).filter(
          product => product.label === label,
        );

      return new Response(JSON.stringify(filteredProducts), {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
      });
    }

    // Fetch products by categoryProducts
    if (category) {
      const products = fetchProductsByCategory(category);
      return new Response(JSON.stringify(products), {
        status: 200,
        headers: { 'Content-Type': 'application/json' },
      });
    }

    if (label) {
      const productsByLabel = fetchProductsByLabel(label);
      return new Response(JSON.stringify(productsByLabel), {
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
