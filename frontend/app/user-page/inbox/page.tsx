'use client';

import React, { useState, useEffect, useRef } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import { useSession } from "next-auth/react";
import axios from 'axios';

export default function Chat() {
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;

  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState<{ senderId: string; content: string }[]>([]);
  const [text, setText] = useState('');
  const [chatRooms, setChatRooms] = useState<{ receiverId: string; receiverName: string }[]>([]);
  const [selectedChatRoom, setSelectedChatRoom] = useState<string | null>(null);
  const [selectedReceiverName, setSelectedReceiverName] = useState<string | null>(null); // Store receiver's name
  const messagesEndRef = useRef<HTMLDivElement | null>(null);

  // Connect to WebSocket when user is logged in
  useEffect(() => {
    if (userId && userToken) {
      connect();
    }
  }, [userId, userToken]);

  // Fetch chat rooms
  useEffect(() => {
    if (userId) {
      axios.get(`http://localhost:8080/api/v2/user/${userId}/chat/rooms`, {
        headers: {
          Authorization: `Bearer ${userToken}`,
        },
      })
          .then((response) => {
            setChatRooms(response.data);
          })
          .catch((error) => {
            console.error('Error fetching chat rooms:', error);
          });
    }
  }, [userId, userToken]);

  // Cleanup WebSocket connection when component unmounts
  useEffect(() => {
    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, [stompClient]);

  // Scroll to the bottom of messages
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  // Connect to WebSocket
  const connect = () => {
    const socket = new SockJS('http://localhost:8080/chat');
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: () => {
        setConnected(true);
        console.log('Connected to WebSocket');

        // Subscribe to global messages and chat room updates
        client.subscribe(`/user/${userId}/queue/messages`, (messageOutput) => {
          const newMessage = JSON.parse(messageOutput.body);
          setMessages((prev) => [...prev, newMessage]);
        });

        client.subscribe('/user/queue/chat-rooms', (chatRoomUpdate) => {
          const newChatRoom = JSON.parse(chatRoomUpdate.body);
          setChatRooms((prev) => [...prev, newChatRoom]); // Update state dynamically
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


  // Send message to the current chat
  const sendMessage = () => {
    if (!stompClient || !text.trim() || !userId || !selectedChatRoom) return;

    const message = { senderId: userId, receiverId: selectedChatRoom, content: text }; // Include receiverId
    stompClient.publish({
      destination: '/app/chat',
      body: JSON.stringify(message),
    });

    setText('');
  };

  // Handle selecting a chat room
  const selectChatRoom = (receiverId: string, receiverName: string) => {
    setSelectedChatRoom(receiverId);
    setSelectedReceiverName(receiverName);  // Store the receiver's name
    setMessages([]); // Clear previous messages
  };

  return (
      <div className="flex items-center justify-center min-h-screen bg-brand-bright p-6">
        <div className="w-full max-w-4xl bg-white rounded-lg shadow-lg p-6 flex gap-4">

          {/* Chat Rooms Section */}
          <div className="w-1/3 bg-gray-100 p-4 rounded-lg shadow-md">
            <h3 className="text-lg font-semibold mb-4">Chat Rooms</h3>
            <div className="h-80 overflow-y-auto border rounded-lg p-2">
              {chatRooms.length === 0 ? (
                  <div>No chat rooms available</div>
              ) : (
                  chatRooms.map((room, index) => (
                      <div
                          key={index}
                          className="p-3 bg-gray-200 rounded-lg mb-2 cursor-pointer hover:bg-gray-300 transition duration-200"
                          onClick={() => selectChatRoom(room.receiverId, room.receiverName)} // Select the chat room
                      >
                        {room.receiverName}
                      </div>
                  ))
              )}
            </div>
          </div>

          {/* Chat Section (Visible only if a chat room is selected) */}
          <div className="w-2/3">
            {selectedChatRoom && selectedReceiverName && (
                <>
                  <div className="flex justify-between items-center mb-4">
                    <h2 className="text-lg font-semibold">Chat with {selectedReceiverName}</h2>
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
                </>
            )}
          </div>
        </div>
      </div>
  );
}
