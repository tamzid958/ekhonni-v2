'use client';
import React from 'react';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { zodResolver } from '@hookform/resolvers/zod';
import { Checkbox } from '@/components/ui/checkbox';
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Button } from '@/components/ui/button';

const items = [
  { id: 'Revive', label: 'REVIVE' },
  { id: 'Fair', label: 'FAIR' },
  { id: 'Good', label: 'GOOD' },
  { id: 'Very_Good', label: 'VERY_GOOD' },
  { id: 'Like_New', label: 'LIKE_NEW' },
] as const;

const FormSchema = z.object({
  items: z.array(z.string()).optional(),
});

export function CheckboxReactHookFormMultipleCondition({ setSelectedConditions, selectedConditions }: {
  setSelectedConditions: (conditions: string[]) => void, selectedConditions: string[]
}) {
  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: { items: selectedConditions },
  });

  function onSubmit(data: z.infer<typeof FormSchema>) {
    setSelectedConditions(data.items || []); // Update parent with selected checkboxes
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
                <FormLabel className="text-base">FILTER BY CONDITION</FormLabel>
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
