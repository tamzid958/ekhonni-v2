import NextAuth, { NextAuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GoogleProvider from "next-auth/providers/google";
import {redis, redlock} from '@/lib/redis';

export interface User {
  id: number;
  email: string;
  password: string;
  firstName: string;
  lastName: string;
  phone: string;
  address: string;
}

export const users: User[] = [
  {
    id: 1,
    email: "test@example.com",
    password: "password123",
    firstName: "John",
    lastName: "Doe",
    phone: "1234567890",
    address: "123 Main St",
  },
];

let userIdCounter = 2;

const refreshAccessToken = async (token: any) => {
  try {
    console.log("Refreshing token...");

    const refreshedToken = {
      ...token,
      accessToken: `refreshed-${token.accessToken}`,
      accessTokenExpires: Date.now() + 60,
    };

    await redis.set(
      `jwt:${token.id}`,
      JSON.stringify(refreshedToken),
      "EX",
      60
    );

    return refreshedToken;
  } catch (error) {
    console.error("Error refreshing access token:", error);
    return {
      ...token,
      error: "RefreshAccessTokenError",
    };
  }
};

const options: NextAuthOptions = {
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        email: { label: "Email", type: "email" },
        password: { label: "Password", type: "password" },
        firstName: { label: "First Name", type: "text", optional: true },
        lastName: { label: "Last Name", type: "text", optional: true },
        phone: { label: "Phone", type: "text", optional: true },
        address: { label: "Address", type: "text", optional: true },
      },
      async authorize(credentials) {
        const { email, password, firstName, lastName, phone, address } =
          credentials as {
            email: string;
            password: string;
            firstName?: string;
            lastName?: string;
            phone?: string;
            address?: string;
          };

        if (firstName && lastName) {
          const existingUser = users.find((u) => u.email === email);
          if (existingUser) {
            throw new Error("User already exists.");
          }

          const newUser: User = {
            id: userIdCounter++,
            email,
            password,
            firstName,
            lastName,
            phone: phone || "",
            address: address || "",
          };

          users.push(newUser);
          console.log("New user signed up:", newUser);
          return {
            id: newUser.id,
            email: newUser.email,
            name: `${newUser.firstName} ${newUser.lastName}`,
          };
        } else {
          const user = users.find(
            (u) => u.email === email && u.password === password
          );
          if (!user) {
            throw new Error("Invalid email or password.");
          }

          console.log("User logged in:", user);

          return {
            id: user.id,
            email: user.email,
            name: `${user.firstName} ${user.lastName}`,
          };
        }
      },
    }),
    GoogleProvider({
      clientId: process.env.GOOGLE_CLIENT_ID!,
      clientSecret: process.env.GOOGLE_CLIENT_SECRET!,
    }),
  ],
  session: {
    strategy: "jwt",
  },
  jwt: {
    secret: process.env.JWT_SECRET || "supersecret",
    maxAge: 60,
  },
  pages: {
    signIn: "/auth/login",
  },
  callbacks: {
    async redirect({ url, baseUrl }) {
      return baseUrl;
    },
    async jwt({ token, user, account }) {
      if (user && account) {
        const jwtToken = {
          ...token,
          id: user.id,
          email: user.email,
          name: user.name,
          accessToken: user.accessToken || "default-access-token",
          refreshToken: user.refreshToken || "default-refresh-token",
          accessTokenExpires: Date.now() + 60,
        };

        await redis.set(
          `jwt:${user.id}`,
          JSON.stringify(jwtToken),
          "EX",
          60
        );
        return jwtToken;
      }

      const storedToken = await redis.get(`jwt:${token.id}`);
      if (storedToken) {
        return JSON.parse(storedToken);
      }

      if (Date.now() < (token.accessTokenExpires as number)) {
        return token;
      }
      return await refreshAccessToken(token);
    },
    async session({ session, token }) {
      session.user = {
        id: token.id as number,
        email: token.email as string,
        name: token.name as string,
        image: token.picture || null,
      };
      session.accessToken = token.accessToken;
      session.error = token.error;
      return session;
    },
  },
};

const handler = NextAuth(options);

export { handler as GET, handler as POST };
