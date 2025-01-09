// src/lib/users.ts

export interface User {
  id: number;
  email: string;
  password: string;
  name: string;
  phone: string;
  address: string;
}

export const users: User[] = [
  {
    id: 1,
    email: "test@example.com",
    password: "password123",
    name: "John",
    phone: "1234567890",
    address: "123 Main St",
  },
  {
    id: 2,
    email: "john.doe@example.com",
    password: "password123",
    name: "Doe",
    phone: "9876543210",
    address: "456 Side St",
  },
];
