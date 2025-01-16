'use client';
import * as z from 'zod';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { Checkbox } from '@/components/ui/checkbox';

const categories = [
  'Automotive',
  'Beauty',
  'Books',
  'Clothing',
  'Electronics',
  'Furniture',
  'Groceries',
  'Health',
  'Home Appliances',
  'Jewelry',
  'Musical Instruments',
  'Shoes',
  'Sports',
  'Stationery',
  'Toys',
] as const;

const conditions = [
  'Revive',
  'Fair',
  'Good',
  'Very Good',
  'Like New',
] as const;

const locations = [
  'Inside Dhaka',
  'Outside Dhaka',
  'Outside Bangladesh',
] as const;


const formSchema = z
  .object({
    productName: z.string().min(3),
    productDescription: z.string().max(255).optional(),
    productCategory: z.enum(categories),
    productSubCategory: z.enum(categories),
    productCondition: z.enum(conditions),
    productLocation: z.enum(locations),

    basePrice: z.number(),
    mobile: z.boolean().default(false).optional(),
  })
  .refine(
    (data) => data.password === data.passwordConfirm,
    { message: 'Passwords do not match', path: ['passwordConfirm'] },
  );

export default function Home() {
  const [step, setStep] = useState(1); // Track the current step
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      productName: '',
      productDescription: '',
      mobile: true,
    },
  });
  const stepFields = {
    1: [
      // 'productName',
      'productDescription',
      // 'productCategory',
      // 'productSubCategory',
      // 'productCondition',
      // 'productLocation',
    ],
    2: ['basePrice'],
  };

  const productCategory = form.watch('productCategory');
  const productSubCategory = form.watch('productSubCategory');
  const productLocation = form.watch('productLocation');

  const handleSubmit = (values: z.infer<typeof formSchema>) => {
    console.log('Form submitted:', values);
  };

  const nextStep = async () => {
    const fieldsToValidate = stepFields[step]; // Get fields for the current step
    const isValid = await form.trigger(fieldsToValidate); // Validate only those fields

    console.log('Validation result:', isValid);
    if (isValid) {
      setStep((prev) => prev + 1); // Move to the next step
      console.log('Moving to next step:', step + 1);
    }
  };
  const prevStep = () => {
    setStep((prev) => Math.max(prev - 1, 1));
  };

  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(handleSubmit)}
          className="max-w-md w-full flex flex-col gap-4"
        >
          {step === 1 && (
            <>
              <h1 className="font-bold text-center text-3xl">Product Data</h1>
              <FormField
                control={form.control}
                name="productName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Product's Name</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="e.g : Samsung Galaxy A4"
                        type="text"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="productDescription"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Product's Description</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Add your product's description here"
                        type="text"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="productCategory"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Category of your Product</FormLabel>
                    <Select onValueChange={field.onChange}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select a category" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {categories.map((category) => (
                          <SelectItem key={category} value={category}>
                            {category}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="productSubCategory"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Sub-Category of your Product</FormLabel>
                    <Select onValueChange={field.onChange}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select a sub-category" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {categories.map((category) => (
                          <SelectItem key={category} value={category}>
                            {category}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="productCondition"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Current Condition of your Product</FormLabel>
                    <Select onValueChange={field.onChange}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select the condition" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {conditions.map((condition) => (
                          <SelectItem key={condition} value={condition}>
                            {condition}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="productLocation"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Location of your Product</FormLabel>
                    <Select onValueChange={field.onChange}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select the product's Location" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {locations.map((location) => (
                          <SelectItem key={location} value={location}>
                            {location}
                          </SelectItem>
                        ))}
                      </SelectContent>
                    </Select>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <Button
                onClick={nextStep}
                type="button"
                className="w-full"
              >
                Next
              </Button>
            </>
          )}
          {step === 2 && (
            <>
              <h1 className="font-bold text-center text-3xl">PRICING</h1>
              <FormField
                control={form.control}
                name="basePrice"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Base Price</FormLabel>
                    <FormControl>
                      <Input
                        placeholder=""
                        type="number"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="mobile"
                render={({ field }) => (
                  <FormItem>
                    <FormControl>
                      <Checkbox
                        checked={field.value}
                        onCheckedChange={field.onChange}
                      />
                    </FormControl>
                    <div className="space-y-1 leading-none">
                      <FormLabel>
                        Use different settings for my mobile devices
                      </FormLabel>
                    </div>
                  </FormItem>
                )}
              />
              {/*<Button type="submit">Submit</Button>*/}
              <div className="flex gap-4">
                <Button onClick={prevStep} type="button" className="w-full">
                  Back
                </Button>
                <Button type="submit" className="w-full">
                  Submit
                </Button>
              </div>
            </>
          )}

        </form>
      </Form>
    </main>
  );
}
