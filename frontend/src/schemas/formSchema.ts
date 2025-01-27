import { z } from 'zod';

export const conditions = [
  'Revive',
  'Fair',
  'Good',
  'Very Good',
  'Like New',
] as const;

export const locations = [
  'Inside Dhaka',
  'Outside Dhaka',
  'Outside Bangladesh',
] as const;

const shipping = [
  'Standard shipping: Small to medium items',
  'Freight: Oversized items',
  'Local pickup only: Sell to buyers near you',
] as const;

export const formSchema = z.object({
  productName: z.string().min(1).max(100),
  productDescription: z.string().max(1000).optional(),
  productCondition: z.enum(conditions, {
    required_error: 'Product condition is required',
  }),
  productLocation: z.enum(locations, {
    required_error: 'Product location is required',
  }),
  basePrice: z
    .number()
    .min(0, { message: 'Price must be a positive number' })
    .max(1000000, { message: 'Price must not exceed 1000000' }),
  images: z
    .array(z.any())
    .min(1, { message: 'Please upload at least one image' })
    .max(7, { message: 'You can upload up to 7 images' }),
});