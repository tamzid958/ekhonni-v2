//backup for form.tsx

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
    productName: z
      .string()
      .min(1, { message: 'Product name is required' })
      .max(100, { message: 'Product name must not exceed 100 characters' }),
    productDescription: z
      .string()
      .max(1000, { message: 'Product description must not exceed 1000 characters' })
      .optional(),
    productCategory: z.enum(categories, {
      required_error: 'Product category is required',
    }),
    productSubCategory: z.enum(categories, {
      required_error: 'Product subcategory is required',
    }),
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

    delievery: z
      .boolean()
      .default(false)
      .optional()
      .refine((value) => typeof value === 'boolean', {
        message: 'Delivery must be a boolean value',
      }),
    shippingMethod: z.enum(shipping, {
      required_error: 'Shipping method is required',
    }),
    packageWeight: z
      .number()
      .min(0, { message: 'Weight must be a positive number' })
      .max(1000000, { message: 'Weight must not exceed 1000000 units' }),

    weightUnit: z.enum(units, {
      required_error: 'Weight unit is required',
    }),
    packageLength: z
      .number()
      .min(0, { message: 'Length must be a positive number' })
      .max(1000000, { message: 'Length must not exceed 1000000 units' }),
    packageWidth: z
      .number()
      .min(0, { message: 'Width must be a positive number' })
      .max(1000000, { message: 'Width must not exceed 1000000 units' }),
    packageHeight: z
      .number()
      .min(0, { message: 'Height must be a positive number' })
      .max(1000000, { message: 'Height must not exceed 1000000 units' }),
    lengthUnit: z.enum(lengthUnits, {
      required_error: 'Length unit is required',
    }),
    images: z
      .array(z.any())
      .min(1, { message: 'Please upload at least one image' })
      .max(7, { message: 'You can upload up to 7 images' }),
  });

export default function Home() {
  const [step, setStep] = useState(1); // Track the current step
  const form = useForm<z.infer<typeof formSchema>>({
    resolver: zodResolver(formSchema),
    defaultValues: {
      productName: '',
      productDescription: '',
      // productCategory: '',
      // productSubCategory: '',
      // productCondition: '',
      // productLocation: '',
      // basePrice: 0,
      delievery: false,
      // shippingMethod: '',
      // packageWeight: 0,
      // weightUnit: 'Gram',
      // packageLength: 0, // Default to 0 length
      // packageWidth: 0, // Default to 0 width
      // packageHeight: 0, // Default to 0 height
      // lengthUnit: '',
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
    //2: ['basePrice', 'packageWeight'],
    2: ['basePrice', 'delievery', 'shippingMethod', 'packageWeight', 'weightUnit'],
    3: ['images'],
  };


  const productCategory = form.watch('productCategory');
  const productSubCategory = form.watch('productSubCategory');
  const productLocation = form.watch('productLocation');

  const handleSubmit = (values: z.infer<typeof formSchema>) => {
    console.log('brrrrrrrr');
    console.log(values);
    // console.log(form.getValues()); // Logs current form values
    // console.log(form.formState.errors); // Logs validation errors
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


              <div className="flex items-center space-x-4">
                {/* Package Weight Field */}
                <div className="w-1/2"> {/* 50% width */}
                  <FormField
                    control={form.control}
                    name="packageWeight"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Weight of your package</FormLabel>
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
                </div>

                {/* Weight Unit Field */}
                <div className="w-1/2">
                  <FormField
                    control={form.control}
                    name="weightUnit"
                    render={({ field }) => (
                      <FormItem>
                        <FormLabel>Weight Unit</FormLabel>
                        <Select onValueChange={field.onChange}>
                          <FormControl>
                            <SelectTrigger>
                              <SelectValue placeholder="Select the Weight Unit" />
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
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>
              </div>


              <FormItem>
                <FormLabel>Product Dimensions</FormLabel>
                <div className="flex items-center space-x-2">
                  <FormField
                    control={form.control}
                    name="packageLength"
                    render={({ field }) => (
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
                    )}
                  />
                  <span>x</span>
                  <FormField
                    control={form.control}
                    name="packageWidth"
                    render={({ field }) => (
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
                    )}
                  />
                  <span>x</span>
                  <FormField
                    control={form.control}
                    name="packageHeight"
                    render={({ field }) => (
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
                <Button type="submit" className="w-full" onClick={handleSubmit}>
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
