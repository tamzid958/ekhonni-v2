'use client';

import React, { useState, useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useSession } from "next-auth/react";

export default function Chat() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const role = session?.user?.role;

  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState<{ senderId: string; content: string }[]>([]);
  const [text, setText] = useState('');
  const messagesEndRef = useRef<HTMLDivElement | null>(null);

  useEffect(() => {
    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, [stompClient]);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const connect = () => {
    const socket = new SockJS('http://localhost:8080/chat');
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        setConnected(true);
        console.log('Connected to WebSocket');

        client.subscribe('/topic/messages', (messageOutput) => {
          const newMessage = JSON.parse(messageOutput.body);
          setMessages((prev) => [...prev, newMessage]);
        });
      },
      onDisconnect: () => {
        setConnected(false);
        console.log('Disconnected');
      },
    });

    client.activate();
    setStompClient(client);
  };

  const disconnect = () => {
    if (stompClient) {
      stompClient.deactivate();
    }
    setConnected(false);
  };

  const sendMessage = () => {
    if (!stompClient || !text.trim() || !userId) return;

    const message = { senderId: userId, content: text }; // Correct structure
    stompClient.publish({
      destination: '/app/chat',
      body: JSON.stringify(message),
    });

    setText('');
  };


  return (
      <div className="flex items-center justify-center min-h-screen bg-brand-bright p-6">
        <div className="w-full max-w-4xl bg-white rounded-lg shadow-lg p-6 flex gap-4">
          {/* Chat Rooms Column */}
          <div className="w-1/3 bg-gray-100 p-4 rounded-lg shadow-md">
            <h3 className="text-lg font-semibold mb-4">Chat Rooms</h3>
            <div className="h-80 overflow-y-auto border rounded-lg p-2">
              {/* Chat rooms will be dynamically loaded here in the future */}
              <div className="p-3 bg-gray-200 rounded-lg mb-2">Room 1</div>
              <div className="p-3 bg-gray-200 rounded-lg mb-2">Room 2</div>
              <div className="p-3 bg-gray-200 rounded-lg mb-2">Room 3</div>
            </div>
          </div>

          {/* Chat Section Column */}
          <div className="w-2/3">
            <div className="flex justify-between items-center mb-4">
              <h2 className="text-lg font-semibold">Ekhonni Chat</h2>
              <div className="flex gap-3">
                <button
                    onClick={connect}
                    disabled={connected}
                    className="px-4 py-2 text-sm bg-blue-500 text-white rounded-lg hover:bg-blue-600 transition disabled:bg-gray-400"
                >
                  Connect
                </button>
                <button
                    onClick={disconnect}
                    disabled={!connected}
                    className="px-4 py-2 text-sm bg-red-500 text-white rounded-lg hover:bg-red-600 transition disabled:bg-gray-400"
                >
                  Disconnect
                </button>
              </div>
            </div>
            <div className="h-96 overflow-y-auto border rounded-lg p-4 bg-gray-50">
              {messages.map((msg, index) => (
                  <div key={index} className={`flex ${msg.senderId === userId ? 'justify-end' : 'justify-start'} mb-3`}>
                    <div className={`p-2 max-w-md text-sm rounded-xl shadow ${msg.senderId === userId ? 'bg-blue-500 text-white' : 'bg-gray-200 text-black'}`}>
                      {msg.content}
                    </div>
                  </div>
              ))}
              <div ref={messagesEndRef}></div>
            </div>
            {connected && (
                <div className="mt-4 flex gap-3">
                  <input
                      type="text"
                      placeholder="Type a message..."
                      value={text}
                      onChange={(e) => setText(e.target.value)}
                      className="flex-1 p-2 text-sm border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-400"
                  />
                  <button
                      onClick={sendMessage}
                      className="px-4 py-2 text-sm bg-green-500 text-white rounded-lg hover:bg-green-600 transition"
                  >
                    Send
                  </button>
                </div>
            )}
          </div>
        </div>
      </div>
  );
}