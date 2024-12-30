import Link from "next/link";
import Image from "next/image";
import React from "react";

const InboxPage = () => {
  // Dummy data for users
  const users = [
    { id: 1, name: "Jisan Ahmed", profilePicture: "/jisan.jpeg" },
    { id: 2, name: "Namira Zaman", profilePicture: "/prapti.jpeg" },
    { id: 3, name: "Samiul Islam Mugdha", profilePicture: "/mugdha.jpeg" },
    { id: 4, name: "Prova Sadia", profilePicture: "/prova.jpeg" },
    { id: 5, name: "Sadman Hafiz", profilePicture: "/Hafiz.jpeg" },
    { id: 6, name: "Shahriar Alvi", profilePicture: "/alvi.jpeg" },

  ];

  return (
    <div className="p-6 max-w-4xl mx-auto min-h-screen">
      <h1 className="text-2xl font-bold mb-4">Inbox</h1>
      <div className="space-y-4">
        {users.map((user) => (
          <Link
            key={user.id}
            href={`/chat/${user.id}`}
            className="flex items-center p-4 bg-gray-100 rounded hover:bg-gray-200"
          >
            <Image
              src={user.profilePicture}
              alt={user.name}
              width={50}
              height={50}
              className="rounded-full"
            />
            <span className="ml-4 text-lg">{user.name}</span>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default InboxPage;
