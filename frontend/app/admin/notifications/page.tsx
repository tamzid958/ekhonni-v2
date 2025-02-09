'use client';

import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { toast, Toaster } from 'sonner';
import { Button } from '@/components/ui/button';
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { useSession } from 'next-auth/react';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';

const conditions = [
  'OFFER',
  // 'MESSAGE',
  // 'ALERT',
] as const;

// Fetcher function for SWR
const fetcher = (url: string) =>
  fetch(`http://localhost:8080${url}`).then((res) => res.json());

// Schema for the form data
const FormSchema = z.object({
  username: z.string().min(2, {
    message: 'Notification must be at least 2 characters.',
  }),
  RedirectURL: z.string().optional(),
  type: z.string().optional(),
});

export default function NotificationPage() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;

  // Fetch available notification types using SWR
  // const { data: notificationTypes, error: notificationTypesError } = useSWR(
  //   userId ? `/api/v2/admin/${userId}/notifications/types` : null,
  //   fetcher,
  // );

  const form = useForm<z.infer<typeof FormSchema>>({
    resolver: zodResolver(FormSchema),
    defaultValues: {
      username: '',
      RedirectURL: '',
      type: '',
    },
  });

  const onSubmit = async (data: z.infer<typeof FormSchema>) => {
    const currentTime = new Date().toLocaleString();

    const notificationData = {
      type: data.type || null,
      message: data.username,
      redirectUrl: data.RedirectURL || null,
    };

    try {
      // Post data to the API
      const response = await fetch(
        `http://localhost:8080/api/v2/admin/${userId}/notifications/create`,
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${userToken}`,
          },
          body: JSON.stringify(notificationData),
        },
      );

      if (response.ok) {
        toast('Notification has been created', {
          description: currentTime,
        });
      } else {
        toast('Failed to create notification', {
          description: 'There was an error creating the notification.',
          variant: 'destructive',
        });
      }
    } catch (error) {
      console.error(error);
      toast('Failed to create notification', {
        description: 'Network or server error.',
        variant: 'destructive',
      });
    }
  };

  // if (notificationTypesError) {
  //   return <div>Error loading notification types.</div>;
  // }

  return (
    <>
      <Toaster position="bottom-right" richColors />

      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="flex flex-col justify-center items-center pt-20 h-[50%] w-[90vw] space-y-4"
        >
          {/* Username Field */}
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Create Notification</FormLabel>
                <FormControl>
                  <Input className={'w-96'} placeholder="Notification" {...field} />
                </FormControl>
                <FormDescription>
                  This is a public notification.
                </FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />

          {/* Redirect URL Field */}
          <FormField
            control={form.control}
            name="RedirectURL"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Give the Redirect URL for the notification</FormLabel>
                <FormControl>
                  <Input className={'text-lg py-3 px-4 w-96'} placeholder="URL" {...field} />
                </FormControl>
                <FormDescription>
                  This is a public URL.
                </FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />

          <FormField
            ccontrol={form.control}
            name="type"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Type of your notification</FormLabel>
                <Select onValueChange={field.onChange}
                        value={field.value}
                >
                  <FormControl className={'w-96'}>
                    <SelectTrigger>
                      <SelectValue placeholder="Select the notification type" />
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

          {/*/!* Notification Type Field *!/*/}
          {/*<FormField*/}
          {/*  control={form.control}*/}
          {/*  name="type"*/}
          {/*  render={({ field }) => (*/}
          {/*    <FormItem>*/}
          {/*      <FormLabel>Notification Type</FormLabel>*/}
          {/*      <FormControl>*/}
          {/*        <select*/}
          {/*          className="text-lg py-3 px-4 w-96"*/}
          {/*          {...field}*/}
          {/*          defaultValue=""*/}
          {/*        >*/}
          {/*          <option value="" disabled>Select Type</option>*/}
          {/*          {notificationTypes?.map((type: string) => (*/}
          {/*            <option key={type} value={type}>*/}
          {/*              {type}*/}
          {/*            </option>*/}
          {/*          ))}*/}
          {/*        </select>*/}
          {/*      </FormControl>*/}
          {/*      <FormDescription>*/}
          {/*        Select the type of notification.*/}
          {/*      </FormDescription>*/}
          {/*      <FormMessage />*/}
          {/*    </FormItem>*/}
          {/*  )}*/}
          {/*/>*/}

          <Button type="submit">Create Notification</Button>
        </form>
      </Form>
    </>
  );
}
