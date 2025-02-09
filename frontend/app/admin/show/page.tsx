'use client';

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

export default function ShowNotifications() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const { data, error } = useSWR(
    userId && userToken ? [`/api/v2/admin/${userId}/notifications`, userToken] : null,
    ([url, token]) => fetcherWithToken(url, token),
  );
  if (error) return <div className="text-red-500">Failed to load notifications.</div>;
  if (!data) return <div>Loading...</div>;
  console.log(data);

  return (
    <div className="p-6">
      <h1 className="text-2xl font-bold mb-4">Notifications</h1>
      <table className="w-full border-collapse border border-gray-300">
        <thead>
        <tr className="bg-gray-200">
          <th className="border p-2">Date</th>
          <th className="border p-2">Message</th>
          <th className="border p-2">Type</th>
          <th className="border p-2">Link</th>
        </tr>
        </thead>
        <tbody>
        {data.data.map((notification: any) => (
          <tr key={notification.id} className="border">
            <td className="border p-2">{new Date(notification.createdAt).toLocaleString()}</td>
            <td className="border p-2">{notification.message}</td>
            <td className="border p-2">{notification.type}</td>
            <td className="border p-2">
              {notification.redirectUrl ? (
                <a href={notification.redirectUrl} className="text-blue-500 underline" target="_blank"
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
