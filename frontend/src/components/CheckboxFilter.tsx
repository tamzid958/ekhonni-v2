'use client';
import React from 'react';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { Checkbox } from '@/components/ui/checkbox';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Button } from '@/components/ui/button';

const items = [
  { id: 'DHAKA', label: 'DHAKA' },
  { id: 'SYLHET', label: 'SYLHET' },
  { id: 'CHITTAGONG', label: 'CHITTAGONG' },
  { id: 'KHULNA', label: 'KHULNA' },
  { id: 'RAJSHAHI', label: 'RAJSHAHI' },
  { id: 'BARISHAL', label: 'BARISHAL' },
  { id: 'RANGPUR', label: 'RANGPUR' },
  { id: 'MYMENSINGH', label: 'MYMENSINGH' },
] as const;

const FormSchema = z.object({
  items: z.array(z.string()).optional(),
});

export function CheckboxReactHookFormMultiple({ setSelectedDivisions, selectedDivisions }: {
  setSelectedDivisions: (divisions: string[]) => void, selectedDivisions: string[];
}) {
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: { items: selectedDivisions },
  });

  function onSubmit(data: z.infer<typeof FormSchema>) {
    setSelectedDivisions(data.items || []);
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="items"
          render={() => (
            <FormItem>
              <div className="mb-4">
                <FormLabel className="text-base">FILTER BY LOCATION</FormLabel>
              </div>
              {items.map((item) => (
                <FormField
                  key={item.id}
                  control={form.control}
                  name="items"
                  render={({ field }) => (
                    <FormItem className="flex flex-row items-start space-x-3 space-y-0">
                      <FormControl>
                        <Checkbox
                          checked={field.value?.includes(item.id)}
                          onCheckedChange={(checked) => {
                            return checked
                              ? field.onChange([...field.value, item.id])
                              : field.onChange(field.value?.filter((value) => value !== item.id));
                          }}
                        />
                      </FormControl>
                      <FormLabel className="text-sm font-normal">{item.label}</FormLabel>
                    </FormItem>
                  )}
                />
              ))}
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit" variant="custom">Filter</Button>
      </form>
    </Form>
  );
}
