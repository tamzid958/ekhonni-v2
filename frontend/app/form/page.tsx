'use client';
import * as z from 'zod';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

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

const formSchema = z
  .object({
    productName: z.string().min(3),
    productDescription: z.string().max(255),
    productCategory: z.enum(categories),
    productSubCategory: z.enum(categories),
    productCondition: z.enum(conditions),
    emailAddress: z.string().email(),
    password: z.string().min(3),
    passwordConfirm: z.string(),
    companyName: z.string().optional(),
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
      emailAddress: '',
      password: '',
      passwordConfirm: '',
      companyName: '',
    },
  });

  const productCategory = form.watch('productCategory');
  const productSubCategory = form.watch('productSubCategory');
  // const productCategory = form.watch('productCategory');

  const handleSubmit = (values: z.infer<typeof formSchema>) => {
    console.log('Form submitted:', values);
  };

  const nextStep = () => setStep((prev) => prev + 1);
  const prevStep = () => setStep((prev) => prev - 1);

  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24">
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(handleSubmit)}
          className="max-w-md w-full flex flex-col gap-4"
        >
          {step === 1 && (
            <>
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
              <Button onClick={nextStep} type="button" className="w-full">
                Next
              </Button>
            </>
          )}
          {step === 2 && (
            <>
              <FormField
                control={form.control}
                name="password"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Password</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Password"
                        type="password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="passwordConfirm"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Confirm Password</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Confirm password"
                        type="password"
                        {...field}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
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
