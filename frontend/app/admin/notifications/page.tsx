'use client';

import React from 'react';
import { zodResolver } from '@hookform/resolvers/zod';
import { useForm } from 'react-hook-form';
import { z } from 'zod';
import { toast, Toaster } from 'sonner';
import { Button } from '@/components/ui/button';
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { useSession } from 'next-auth/react';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import NotificationsList from '../components/NotificationList';

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

  return (
    <>
      <Toaster position="bottom-right" richColors />
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="flex flex-col justify-center items-center pt-20  w-[90vw] space-y-4"
        >
          <h1 className="flex items-center justify-center text-2xl font-bold mb-4">Create User Notifications</h1>
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

          <FormField
            control={form.control}
            name="RedirectURL"
            render={({ field }) => (
              <FormItem>
                <FormLabel>Give the Redirect URL for the notification</FormLabel>
                <FormControl>
                  <Input className={'text-lg py-3 px-4 w-96'} placeholder="URL" {...field} />
                </FormControl>
                {/*<FormDescription>*/}
                {/*  This is a public URL.*/}
                {/*</FormDescription>*/}
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

          <Button type="submit">Create Notification</Button>
        </form>
      </Form>

      <hr className="mt-8" />

      <NotificationsList />
    </>
  );
}
