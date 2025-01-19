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
import { Trash2 } from 'lucide-react';

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

const shipping = [
  'Standard shipping: Small to medium items',
  'Freight: Oversized items',
  'Local pickup only: Sell to buyers near you',
] as const;

const units = [
  'Gram',
  'Kilogram',
] as const;

const lengthUnits = [
  'cm',
  'meter',
  'inch',
  'feet',
] as const;

const formSchema = z
  .object({
    productName: z.string(),
    productDescription: z.string().max(255).optional(),
    productCategory: z.enum(categories),
    productSubCategory: z.enum(categories),
    productCondition: z.enum(conditions),
    productLocation: z.enum(locations),

    basePrice: z.number()
      .min(0, { message: 'Price must be a positive number' })  // Ensures positive price
      .refine(value => value === Math.round(value * 100) / 100, {
        message: 'Price must have up to two decimal places',
      }),
    delievery: z.boolean().default(false).optional(),

    shippingMethod: z.enum(shipping),
    packageWeight: z
      .number()
      .min(0, { message: 'Weight must be a positive number' })  // Ensures positive price
      .refine(value => value === Math.round(value * 100) / 100, {
        message: 'Price must have up to two decimal places',
      }),
    WeightUnit: z.enum(units),
    packageLength: z.number(),
    packageWidth: z.number(),
    packageHeight: z.number(),
    lengthUnit: z.enum(lengthUnits),

    images: z
      .array(z.any()) // Accepts any type (files in this case)
      .min(5, 'Please upload all images')
      .optional(),
  });
// .refine(val => !isNaN(parseFloat(val)) && val.trim() !== '', {
//   message: 'Please enter a valid number',
// })
// .transform(val => parseFloat(val));

export default function Home() {
  const [step, setStep] = useState(1); // Track the current step
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      productName: '',
      productDescription: '', // Optional field, can be an empty string
      productCategory: '', // Choose a default from your categories enum
      productSubCategory: '', // Choose a default from your categories enum
      productCondition: '', // Choose a default from your conditions enum
      productLocation: '', // Choose a default from your locations enum
      // basePrice: 0, // Assuming default value of 0 for base price
      delievery: false, // Default value is false for delievery
      shippingMethod: '', // Choose a default from your shipping enum
      // packageWeight: 0, // Default to 0 weight
      WeightUnit: '', // Choose a default from your units enum
      // packageLength: 0, // Default to 0 length
      // packageWidth: 0, // Default to 0 width
      // packageHeight: 0, // Default to 0 height
      lengthUnit: '', // Choose a default from your lengthUnits enum
    },
  });
  const stepFields = {
    1: [
      'productName',
      'productDescription',
      'productCategory',
      'productSubCategory',
      'productCondition',
      'productLocation',
    ],
    2: ['basePrice'],
    3: ['images'],
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
              <h1 className="font-bold text-center text-3xl">PRICING & SHIPPING</h1>
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
                        value={field.value || ''}
                        onChange={(e) => {
                          const inputValue = e.target.value;
                          const numberValue = parseFloat(inputValue);

                          if (!isNaN(numberValue) && inputValue.trim() !== '') {
                            field.onChange(numberValue);
                          } else if (inputValue === '') {
                            field.onChange(null);
                          }
                        }}
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="delievery"
                render={({ field }) => (
                  <FormItem>
                    <div className="flex items-center space-x-3">
                      <FormControl>
                        <Checkbox
                          checked={field.value}
                          onCheckedChange={field.onChange}
                        />
                      </FormControl>
                      <FormLabel>
                        Free Delievery
                      </FormLabel>
                    </div>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="shippingMethod"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Shipping Method</FormLabel>
                    <Select onValueChange={field.onChange}>
                      <FormControl>
                        <SelectTrigger>
                          <SelectValue placeholder="Select a Shipping method" />
                        </SelectTrigger>
                      </FormControl>
                      <SelectContent>
                        {shipping.map((method) => (
                          <SelectItem key={method} value={method}>
                            {method}
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
                name="packageWeight"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Package Weight</FormLabel>
                    <div className="flex items-center space-x-4">
                      <FormControl>
                        <Input
                          placeholder=""
                          type="number"
                          value={field.value || ''}
                          onChange={(e) => {
                            const inputValue = e.target.value;
                            const numberValue = parseFloat(inputValue);

                            if (!isNaN(numberValue) && inputValue.trim() !== '') {
                              field.onChange(numberValue);
                            } else if (inputValue === '') {
                              field.onChange(null);
                            }
                          }}
                        />
                      </FormControl>
                      <FormField
                        control={form.control}
                        name="weightUnits"
                        render={({ field }) => (
                          <FormControl>
                            <Select onValueChange={field.onChange}>
                              <FormControl>
                                <SelectTrigger>
                                  <SelectValue placeholder="Select an unit" />
                                </SelectTrigger>
                              </FormControl>
                              <SelectContent>
                                {units.map((unit) => (
                                  <SelectItem key={unit} value={unit}>
                                    {unit}
                                  </SelectItem>
                                ))}
                              </SelectContent>
                            </Select>
                          </FormControl>
                        )}
                      />
                    </div>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormItem>
                <FormLabel>Product Dimensions</FormLabel>
                <div className="flex items-center space-x-2">
                  <FormField
                    control={form.control}
                    name="packageLength"
                    render={({ field }) => (
                      <FormControl>
                        <Input
                          placeholder="Length"
                          type="number"
                          {...field}
                        />
                      </FormControl>
                    )}
                  />
                  <span>x</span>
                  <FormField
                    control={form.control}
                    name="packageWidth"
                    render={({ field }) => (
                      <FormControl>
                        <Input
                          placeholder="Width"
                          type="number"
                          {...field}
                        />
                      </FormControl>
                    )}
                  />
                  <span>x</span>
                  <FormField
                    control={form.control}
                    name="packageHeight"
                    render={({ field }) => (
                      <FormControl>
                        <Input
                          placeholder="Height"
                          type="number"
                          {...field}
                        />
                      </FormControl>
                    )}
                  />
                  <FormField
                    control={form.control}
                    name="lengthUnit"
                    render={({ field }) => (
                      <Select onValueChange={field.onChange}>
                        <FormControl>
                          <SelectTrigger>
                            <SelectValue placeholder="Unit" />
                          </SelectTrigger>
                        </FormControl>
                        <SelectContent>
                          {lengthUnits.map((unit) => (
                            <SelectItem key={unit} value={unit}>
                              {unit}
                            </SelectItem>
                          ))}
                        </SelectContent>
                      </Select>
                    )}
                  />
                </div>
                <FormMessage />
              </FormItem>

              <div className="flex gap-4">
                <Button onClick={prevStep} type="button" className="w-full">
                  Back
                </Button>
                <Button
                  onClick={nextStep}
                  type="button"
                  className="w-full"
                >
                  Next
                </Button>

              </div>
            </>
          )}

          {step === 3 && (
            <>
              <h1 className="font-bold text-center text-3xl">Images </h1>
              {/* Image Upload Section */}
              <FormField
                control={form.control}
                name="images"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>Product Images</FormLabel>
                    <div className="grid grid-cols-3 gap-4">
                      {Array.from({ length: 7 }).map((_, index) => {
                        const names = ['Front', 'Back', 'Left Side', 'Right side', 'Details', 'Damage', 'Other']; // Random names list
                        const name = names[index]; // Get name based on index

                        return (
                          <div
                            key={index}
                            className={`${
                              index === 0 ? 'col-span-3' : 'col-span-1'
                            } border border-dashed border-gray-300 rounded-md p-2 flex items-center justify-center`}
                          >
                            <div className="relative w-full h-32">
                              <Input
                                type="file"
                                accept="image/*"
                                onChange={(e) => {
                                  const files = e.target.files;
                                  if (files?.[0]) {
                                    const updatedImages = [...(field.value || [])];
                                    updatedImages[index] = files[0];
                                    field.onChange(updatedImages);
                                  }
                                }}
                                className="absolute inset-0 cursor-pointer w-full h-full opacity-0 z-10" // Hide file input behind container
                                ref={(el) => {
                                  if (el && !field.value?.[index]) {
                                    el.value = ''; // Reset file input when image is removed
                                  }
                                }}
                              />
                              {field.value?.[index] && (
                                <>
                                  {/* Image preview */}
                                  <img
                                    src={URL.createObjectURL(field.value[index])}
                                    alt={`Preview ${index + 1}`}
                                    className="absolute inset-0 object-cover w-full h-full rounded-md z-20"
                                  />
                                  {/* Trash icon button */}
                                  <button
                                    type="button"
                                    onClick={() => {
                                      const updatedImages = [...(field.value || [])];
                                      updatedImages[index] = null; // Set the image slot to null (not removing it)
                                      field.onChange(updatedImages); // Update the field value with the modified array
                                    }}
                                    className="absolute top-2 right-2 p-1 bg-white rounded-full shadow-md z-20"
                                  >
                                    <Trash2 size={20} />
                                  </button>
                                </>
                              )}
                              <div className="absolute inset-0 flex items-center justify-center z-0">
                                {/*{index < 5 && <><span className="text-red-500">*</span>&nbsp;</>}*/}
                                <span className="text-gray-500 ">{name}</span> {/* Display custom name */}
                              </div>
                            </div>
                          </div>
                        );
                      })}
                    </div>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <div className="flex gap-4">
                <Button onClick={prevStep} type="button" className="w-full">
                  Back
                </Button>
                {/*<Button*/}
                {/*  onClick={nextStep}*/}
                {/*  type="button"*/}
                {/*  className="w-full"*/}
                {/*>*/}
                {/*  Next*/}
                {/*</Button>*/}
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
