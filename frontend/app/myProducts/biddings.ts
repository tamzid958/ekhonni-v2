import { BidData } from './Components/columns';

export async function fetchBids(id: number, token: string): Promise<BidData[]> {

  const url = `http://localhost:8080/api/v2/bid/seller/product/${id}`;

  try {
    const response = await fetch(url, {
      method: 'GET', // or 'POST', depending on your API
      headers: {
        'Authorization': `Bearer ${token}`, // Replace with your actual token
        // Add any other headers your API requires
      },
      cache: 'no-store',
    });

    if (!response.ok) {
      throw new Error('Failed to fetch bid details');
    }

    const json = await response.json();
    console.log('API Response Data:', json.data.content);
    return json.data.content;
  } catch (error) {
    console.error('Error fetching bid details:', error);
    return [];
  }
}