import { useFormContext } from 'react-hook-form';
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { conditions, locations } from '@/schemas/formSchema';
import { Button } from '@/components/ui/button';

export default function FormPage1({ nextStep }: { nextStep: () => void }) {
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
                placeholder="Add Product Name"
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
        ccontrol={control}
        name="productCondition"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Current Condition of your Product</FormLabel>
            <Select onValueChange={field.onChange}
                    value={field.value}>
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
        control={control}
        name="productLocation"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Location of your Product</FormLabel>
            <Select onValueChange={field.onChange} value={field.value}>
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
    </div>
  );
}
