'use client';

import React from 'react';
import useSWR from 'swr';
import { useSession } from 'next-auth/react';

const fetcherWithToken = async (url: string, token: string) => {
  const res = await fetch(`http://localhost:8080${url}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    },
  });

  if (!res.ok) throw new Error('Failed to fetch data');
  return res.json();
};

export default function NotificationsList() {
  const { data: session } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;

  const { data, error } = useSWR(
    userId && userToken ? [`/api/v2/admin/${userId}/notifications`, userToken] : null,
    ([url, token]) => fetcherWithToken(url, token),
  );

  if (error) return <div className="text-red-500">Failed to load notifications.</div>;
  if (!data) return <div>Loading...</div>;

  return (
    <div className="p-6 flex-grow overflow-auto">
      <h1 className="flex items-center justify-center text-2xl font-bold mb-4">Previous Notifications</h1>
      <table className="w-full border-collapse border border-gray-300">
        <thead>
        <tr className="bg-brand-dark">
          <th className="border p-2 w-1/8">Date</th>
          <th className="border p-2 w-1/2">Message</th>
          <th className="border p-2 w-1/4">Type</th>
          <th className="border p-2 w-1/8">Link</th>
        </tr>
        </thead>
        <tbody className={'p-6 '}>
        {data.data.slice().reverse().map((notification: any) => (
          <tr key={notification.id} className="border">
            <td className="border p-2 text-center">{new Date(notification.createdAt).toLocaleString()}</td>
            <td className="border p-2 text-center">{notification.message}</td>
            <td className="border p-2 text-center">{notification.type}</td>
            <td className="border p-2 text-center">
              {notification.redirectUrl ? (
                <a href={notification.redirectUrl} className="underline" target="_blank"
                   rel="noopener noreferrer">
                  Open Link
                </a>
              ) : (
                'No Link'
              )}
            </td>
          </tr>
        ))}
        </tbody>
      </table>
    </div>
  );
}
