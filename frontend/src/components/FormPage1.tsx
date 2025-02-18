import React from 'react';
import { useFormContext } from 'react-hook-form';
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { locations } from '@/schemas/formSchema';
import { Button } from '@/components/ui/button';

export default function FormPage1({ nextStep, prevStep }: { nextStep: () => void; prevStep: () => void }) {
  const { control } = useFormContext();

  return (
    <div className={'space-y-4'}>
      <h1 className="font-bold text-center text-3xl">Product Data</h1>
      <FormField
        control={control}
        name="productName"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Product's Name</FormLabel>
            <FormControl>
              <Input
                placeholder="Add Your Product Name"
                type="text"
                {...field}
              />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
      <FormField
        control={control}
        name="productSubTitle"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Sub-title of the Product</FormLabel>
            <FormControl>
              <Input
                placeholder="Add Product Sub-title Here"
                type="text"
                {...field}
              />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />
      <FormField
        control={control}
        name="productDescription"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Product's Description</FormLabel>
            <FormControl>
              <Input
                placeholder="Add Your Product's Description Here"
                type="text"
                {...field}
              />
            </FormControl>
            <FormMessage />
          </FormItem>
        )}
      />

      <FormField
        control={control}
        name="productLocation"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Current Division of your Product</FormLabel>
            <Select onValueChange={field.onChange} value={field.value}>
              <FormControl>
                <SelectTrigger>
                  <SelectValue placeholder="Select the Sender's Location Division" />
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
      <FormField
        control={control}
        name="productLocationDescription"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Product's Current Address</FormLabel>
            <FormControl>
              <Input
                placeholder="Add Your Product's Current Address Here"
                type="text"
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
        <Button
          onClick={nextStep}
          type="button"
          className="w-full"
        >
          Next
        </Button>

      </div>
    </div>
  );
}