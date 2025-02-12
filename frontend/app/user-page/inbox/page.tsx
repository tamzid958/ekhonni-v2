'use client';

import React from 'react';
import { useState, useEffect } from 'react';
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';
import {useSession} from "next-auth/react";
import Loading from "@/components/Loading";

export default function Chat() {


  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  const role = session?.user?.role;

  const [stompClient, setStompClient] = useState<Client | null>(null);
  const [connected, setConnected] = useState(false);
  const [messages, setMessages] = useState([]);
  const [from, setFrom] = useState('');
  const [text, setText] = useState('');


  useEffect(() => {
    return () => {
      if (stompClient) {
        stompClient.deactivate();
      }
    };
  }, [stompClient]);

  const connect = () => {
    const socket = new SockJS('http://localhost:8080/chat');
    const client = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      onConnect: (frame) => {
        setConnected(true);
        console.log('Connected: ' + frame);
        client.subscribe('/topic/messages', (messageOutput) => {
          setMessages((prev) => [...prev, JSON.parse(messageOutput.body)]);
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
  };

  const sendMessage = () => {
    if (stompClient && from && text) {
      stompClient.publish({
        destination: '/app/chat',
        body: JSON.stringify({ from, text }),
      });
      setText('');
    }
  };

  return (
      <div className="p-4 max-w-lg mx-auto">
        <input
            type="text"
            placeholder="Choose a nickname"
            value={from}
            onChange={(e) => setFrom(e.target.value)}
            className="p-2 border rounded w-full mb-2"
        />
        <div className="flex gap-2 mb-4">
          <button
              onClick={connect}
              disabled={connected}
              className="p-2 bg-blue-500 text-white rounded disabled:bg-gray-400"
          >
            Connect
          </button>
          <button
              onClick={disconnect}
              disabled={!connected}
              className="p-2 bg-red-500 text-white rounded disabled:bg-gray-400"
          >
            Disconnect
          </button>
        </div>
        {connected && (
            <div>
              <input
                  type="text"
                  placeholder="Write a message..."
                  value={text}
                  onChange={(e) => setText(e.target.value)}
                  className="p-2 border rounded w-full mb-2"
              />
              <button
                  onClick={sendMessage}
                  className="p-2 bg-green-500 text-white rounded"
              >
                Send
              </button>
              <div className="mt-4 border p-2 rounded">
                {messages.map((msg, index) => (
                    <p key={index} className="break-words">
                      <strong>{msg.from}:</strong> {msg.text}
                    </p>
                ))}
              </div>
            </div>
        )}
      </div>
  );
}
