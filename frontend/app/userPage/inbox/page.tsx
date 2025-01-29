'use client';

import React, { useState } from "react";
import Image from "next/image";

const ChatApp = () => {
  const users = [
    { id: 1, name: "Jisan Ahmed", profilePicture: "/jisan.jpeg" },
    { id: 2, name: "Namira Zaman", profilePicture: "/prapti.jpeg" },
    { id: 3, name: "Samiul Islam Mugdha", profilePicture: "/mugdha.jpeg" },
    { id: 4, name: "Prova Sadia", profilePicture: "/prova.jpeg" },
    { id: 5, name: "Sadman Hafiz", profilePicture: "/Hafiz.jpeg" },
    { id: 6, name: "Shahriar Alvi", profilePicture: "/alvi.jpeg" },
  ];

  const [activeChat, setActiveChat] = useState(null);
  const [messages, setMessages] = useState([
    { id: 1, text: "Hi there!", sender: "other", timestamp: "12:00 PM" },
    { id: 2, text: "Hello! How can I help you?", sender: "me", timestamp: "12:01 PM" },
  ]);
  const [newMessage, setNewMessage] = useState("");

  const handleSendMessage = () => {
    if (newMessage.trim() !== "") {
      const timestamp = new Date().toLocaleTimeString([], { hour: "2-digit", minute: "2-digit" });
      setMessages([...messages, { id: Date.now(), text: newMessage, sender: "me", timestamp }]);
      setNewMessage("");
    }
  };

  return (

    <div className="bg-[#FAF7F0]">
      <div className="bg-[#FAF7F0]">
        <div className="flex max-w-5xl mx-auto min-h-screen border rounded-lg overflow-hidden">
          <div className="w-1/3 bg-gray-100 p-4 border-r">
            <h1 className="text-2xl font-bold mb-4">Inbox</h1>
            <div className="space-y-4">
              {users.map((user) => (
                <div
                  key={user.id}
                  onClick={() => setActiveChat(user)}
                  className={`flex items-center p-4 bg-gray-200 rounded cursor-pointer hover:bg-gray-300 ${
                    activeChat?.id === user.id ? "bg-gray-300" : ""
                  }`}
                >
                  <Image
                    src={user.profilePicture}
                    alt={user.name}
                    width={50}
                    height={50}
                    className="rounded-full"
                  />
                  <span className="ml-4 text-lg">{user.name}</span>
                </div>
              ))}
            </div>
          </div>


          <div className="w-2/3 p-6">
            {activeChat ? (
              <div>
                {/*<button*/}
                {/*  onClick={() => setActiveChat(null)}*/}
                {/*  className="flex items-center text-black hover:text-gray-600 mb-4"*/}
                {/*>*/}
                {/*  <FiArrowLeft className="mr-2" size={20} />*/}
                {/*  Back to Inbox*/}
                {/*</button>*/}

                <h1 className="text-2xl font-bold mb-4">Chat with {activeChat.name}</h1>

                <div className="h-[60vh] bg-gray-100 p-4 rounded-lg overflow-y-auto">
                  {messages.map((message) => (
                    <div
                      key={message.id}
                      className={`p-2 my-2 max-w-xs ${
                        message.sender === "me"
                          ? "ml-auto bg-blue-500 text-white"
                          : "mr-auto bg-gray-300"
                      } rounded-lg`}
                    >
                      <div className="flex justify-between">
                        <p>{message.text}</p>
                        <span className="text-xs text-gray-500">{message.timestamp}</span>
                      </div>
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
            ) : (
              <div className="flex items-center justify-center h-full">
                <p className="text-gray-500">Select a conversation to start chatting</p>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>


  );
};

export default ChatApp;
