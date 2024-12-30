'use client';
import React, { useState } from "react";
import { useRouter } from "next/navigation";
import { FiArrowLeft } from "react-icons/fi";

interface ChatPageProps {
  params: {
    id: string;
  };
}

const ChatPage = ({ params }: ChatPageProps) => {
  const router = useRouter();
  const { id } = params;

  const [messages, setMessages] = useState([
    { id: 1, text: "Hi there!", sender: "other" },
    { id: 2, text: "Hello! How can I help you?", sender: "me" },
  ]);
  const [newMessage, setNewMessage] = useState("");

  const handleSendMessage = () => {
    if (newMessage.trim() !== "") {
      setMessages([...messages, { id: Date.now(), text: newMessage, sender: "me" }]);
      setNewMessage("");
    }
  };

  return (
    <div className="p-6 max-w-4xl mx-auto min-h-screen">

      <button
        onClick={() => router.back()}
        className="flex items-center text-black-100 hover:text-gray-300 mb-4"
      >
        <FiArrowLeft className="mr-2" size={20} />
        Back
      </button>

      <h1 className="text-2xl font-bold mb-4">Chat with User {id}</h1>
      <div className="h-[60vh] bg-gray-100 p-4 rounded-lg overflow-y-auto">
        {messages.map((message) => (
          <div
            key={message.id}
            className={`p-2 my-2 max-w-xs ${
              message.sender === "me" ? "ml-auto bg-blue-500 text-white" : "mr-auto bg-gray-300"
            } rounded-lg`}
          >
            {message.text}
          </div>
        ))}
      </div>
      <div className="mt-4 flex">
        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          placeholder="Type a message..."
          className="flex-grow p-2 border rounded-l-lg focus:outline-none"
        />
        <button
          onClick={handleSendMessage}
          className="p-2 bg-blue-500 text-white rounded-r-lg hover:bg-blue-600"
        >
          Send
        </button>
      </div>
    </div>
  );
};

export default ChatPage;
