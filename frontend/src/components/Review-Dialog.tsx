import { Dialog, DialogTrigger, DialogContent, DialogFooter, DialogHeader, DialogTitle } from '@/components/ui/dialog';
import { Star } from 'lucide-react';
import React, { useState, useEffect } from 'react';
import { Button } from '@/components/ui/button';
import { toast } from 'sonner';
import { useSession } from 'next-auth/react';

interface ReviewDialogProps {
  bidId: number;
  productId: string;
  ruleFor: 'buyer' | 'seller'; // New property to distinguish between buyer and seller reviews
}

export function ReviewDialog({ productId, bidId, ruleFor }: ReviewDialogProps) {
  const [reviewText, setReviewText] = useState<string>('');
  const [rating, setRating] = useState<number>(0);
  const [previousReview, setPreviousReview] = useState<{ timestamp: string } | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [dialogOpen, setDialogOpen] = useState<boolean>(false);
  const { data: session } = useSession();
  const token = session?.user?.token;

  // UseEffect to check for the saved review on component mount
  useEffect(() => {
    const savedReview = localStorage.getItem(`review_${productId}_${ruleFor}`);
    if (savedReview) {
      const reviewData = JSON.parse(savedReview);
      setPreviousReview(reviewData);
      setReviewText(reviewData.description);
      setRating(reviewData.rating);
      setLoading(false);
    } else {
      fetchReview();
    }
  }, [productId, ruleFor]);

  const fetchReview = async () => {
    if (!token) return;
    try {
      const response = await fetch(`http://localhost:8080/api/v2/review/${ruleFor}/product/${productId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        setLoading(false);
        return;
      }
      const data = await response.json();
      if (data.success && data.data) {
        const reviewDate = new Date(data.data.createdAt);
        reviewDate.setHours(reviewDate.getHours() + 6);
        setPreviousReview({
          timestamp: reviewDate.toLocaleString(),
        });
        setReviewText(data.data.description);
        setRating(data.data.rating);
        localStorage.setItem(`review_${productId}_${ruleFor}`, JSON.stringify({
          description: data.data.description,
          rating: data.data.rating,
          timestamp: reviewDate.toLocaleString(),
        }));
      }
    } catch (error) {
      console.error('Error fetching previous review:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleReviewSubmit = async () => {
    if (!token) {
      toast('You must be logged in to leave a review');
      return;
    }
    try {
      const response = await fetch(`http://localhost:8080/api/v2/review/${ruleFor}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          bidId,
          rating,
          description: reviewText,
        }),
      });
      const data = await response.json();
      if (!response.ok) {
        toast('Failed to submit review');
        console.error(data.message || 'An error occurred');
        return;
      }
      toast('Review submitted successfully');
      const timestamp = new Date().toLocaleString();
      setPreviousReview({ timestamp });
      setDialogOpen(false);

      // Save the review to local storage
      localStorage.setItem(`review_${productId}_${ruleFor}`, JSON.stringify({
        description: reviewText,
        rating,
        timestamp,
      }));
    } catch (error) {
      console.error('Error submitting review:', error);
      toast('An error occurred while submitting the review');
    }
  };

  const handleStarClick = (star: number) => {
    setRating(star);
  };

  return (
    <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
      <DialogTrigger asChild>
        <div className="relative group">
          <Button variant="outline">
            {previousReview ? 'Update Your Review' : 'Leave a Review'}
          </Button>

          {previousReview && (
            <div className="absolute top-5 left-14  mt-2 w-max bg-gray-100 text-sm text-gray-800 p-2 rounded shadow-md opacity-0 group-hover:opacity-100 transition-opacity">
              Last Reviewed: {previousReview.timestamp}
            </div>
          )}
        </div>
      </DialogTrigger>





      <DialogContent>
        <DialogHeader>
          <DialogTitle>{previousReview ? 'Update Your Review' : ruleFor === 'seller' ? 'Leave a Review for Seller' : 'Leave a Review for Buyer'}</DialogTitle>
        </DialogHeader>
        <div className="space-y-4">
          <textarea
            className="w-full p-2 border rounded-md"
            placeholder="Write your review here..."
            rows={4}
            value={reviewText}
            onChange={(e) => setReviewText(e.target.value)}
          />
          <div className="flex items-center space-x-2">
            {[1, 2, 3, 4, 5].map((star) => (
              <Star
                key={star}
                className={`h-5 w-5 ${rating >= star ? 'text-yellow-500' : 'text-gray-300'} cursor-pointer`}
                onClick={() => handleStarClick(star)}
              />
            ))}
          </div>
        </div>
        <DialogFooter>
          <Button variant="outline" onClick={() => setDialogOpen(false)}>Cancel</Button>
          <Button onClick={handleReviewSubmit}>{previousReview ? 'Update Review' : 'Submit Review'}</Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}
