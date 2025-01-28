import { useFormContext } from 'react-hook-form';
import { FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';

export default function FormPage2({ nextStep, prevStep }: { nextStep: () => void; prevStep: () => void }) {
  const { control } = useFormContext();

  return (
    <div className={'space-y-4'}>
      <h1 className="font-bold text-center text-3xl">PRICING & SHIPPING</h1>
      <FormField
        control={control}
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
      {/*<FormField*/}
      {/*  ccontrol={control}*/}
      {/*  name="productCondition"*/}
      {/*  render={({ field }) => (*/}
      {/*    <FormItem>*/}
      {/*      <FormLabel>Current Condition of your Product</FormLabel>*/}
      {/*      <Select onValueChange={field.onChange}*/}
      {/*              value={field.value}>*/}
      {/*        <FormControl>*/}
      {/*          <SelectTrigger>*/}
      {/*            <SelectValue placeholder="Select the condition" />*/}
      {/*          </SelectTrigger>*/}
      {/*        </FormControl>*/}
      {/*        <SelectContent>*/}
      {/*          {conditions.map((condition) => (*/}
      {/*            <SelectItem key={condition} value={condition}>*/}
      {/*              {condition}*/}
      {/*            </SelectItem>*/}
      {/*          ))}*/}
      {/*        </SelectContent>*/}
      {/*      </Select>*/}
      {/*      <FormMessage />*/}
      {/*    </FormItem>*/}
      {/*  )}*/}
      {/*/>*/}
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
