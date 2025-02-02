import React, { useEffect, useState } from 'react';
import { fetchBids } from './biddings'; // Assuming you already have this function

interface BiddingPageProps {
  productId: number;
  token: string;
}

const BidsPage: React.FC<BiddingPageProps> = ({ productId, token }) => {
  const [bids, setBids] = useState<any[]>([]); // Replace with proper type
  const [loading, setLoading] = useState<boolean>(true);

  useEffect(() => {
    const getBids = async () => {
      try {
        const fetchedBids = await fetchBids(productId, token);
        setBids(fetchedBids);
      } catch (error) {
        console.error('Error fetching bids:', error);
      } finally {
        setLoading(false);
      }
    };

    getBids();
  }, [productId, token]);

  if (loading) return <p>Loading...</p>;

  return (
    <div>
      {bids.length === 0 ? (
        <p>No bids available.</p>
      ) : (
        <div>
          <table>
            <thead>
            <tr>
              <th>Bidder</th>
              <th>Amount</th>
              <th>Status</th>
            </tr>
            </thead>
            <tbody>
            {bids.map((bid, index) => (
              <tr key={index}>
                <td>{bid.bidderId}</td>
                <td>{bid.amount}</td>
                <td>{bid.status}</td>
              </tr>
            ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );
};

export default BidsPage;
