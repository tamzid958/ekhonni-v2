import { z } from 'zod';

export const conditions = [
  'Revive',
  'Fair',
  'Good',
  'Very Good',
  'Like New',
] as const;

export const locations = [
  'DHAKA',
  'SYLHET',
  'CHITTAGONG',
  'KHULNA',
  'RAJSHAHI',
  'BARISHAL',
  'RANGPUR',
  'MYMENSINGH',
] as const;

const shipping = [
  'Standard shipping: Small to medium items',
  'Freight: Oversized items',
  'Local pickup only: Sell to buyers near you',
] as const;

export const formSchema = z.object({
  category: z.string().nonempty('Category is required'),
  subCategory: z.string().nonempty('Subcategory is required'),
  productName: z.string().min(1).max(100),
  productSubTitle: z.string().min(1).max(255),
  productDescription: z.string().max(1000),
  productLocation: z.enum(locations, {
    required_error: 'Product location is required',
  }),
  productLocationDescription: z.string().min(1).max(255),
  basePrice: z
    .number()
    .min(0, { message: 'Price must be a positive number' })
    .max(1000000, { message: 'Price must not exceed 1000000' }),
  productCondition: z.enum(conditions, {
    required_error: 'Product condition is required',
  }),
  productConditionDescription: z.string().min(1).max(255),
  images: z
    .array(z.any())
    .min(1, { message: 'Please upload at least one image' })
    .max(7, { message: 'You can upload up to 7 images' }),
});