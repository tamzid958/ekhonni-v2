import NextAuth, { NextAuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GoogleProvider from "next-auth/providers/google";
import {redis} from '@/lib/redis';
import { User } from '@/data/types/user';
import axios from 'axios';
import { axiosInstance } from '@/data/services/fetcher';
import  jwtDecode  from 'jwt-decode';


const refreshAccessToken = async (token: any) => {
  try {
    console.log("Refreshing token...");

    const refreshedToken = {
      ...token,
      accessToken: `refreshed-${token.accessToken}`,
      accessTokenExpires: Date.now() + 30 * 1000,
    };

    await redis.set(
      `jwt:${token.id}`,
      JSON.stringify(refreshedToken),
      "EX",
      30
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
      },
      async authorize(credentials) {
        const { email, password } =
          credentials as {
            email: string;
            password: string;
          };

        if (email && password) {
          const response = await axiosInstance.post("/api/v2/auth/sign-in", {
              email,
              password,
            });
          console.log("Response from sign-in:", response);
          if (response.status === 200 && response.data.accessToken) {
            const token = response.data;

            return {
              // id: id,
              // email: email,
              // name: name,
              accessToken: token.accessToken,
              refreshToken: token.refreshToken,
            }
          }
          else{
            const errorData = response.data;
            console.error("Login failed:", errorData);
            throw new Error(errorData['message'] || "Login failed.");
          }
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

  },
  pages: {
    signIn: "/auth/login",
  },
  callbacks: {
    async redirect({ url, baseUrl }) {
      return baseUrl;
    },
    async signIn({ user, account, profile, email, credentials }) {
      return true
    },
    async jwt({ token, user, account }) {
      if (user && account) {
        const jwtToken = {
          ...token,
          id: user.id,
          email: user.email,
          name: user.name,
          accessToken: account.access_token || "default-access-token",
          refreshToken: account.refreshToken || "default-refresh-token",
          accessTokenExpires: Date.now() + 30 * 1000,
        };

        await redis.set(
          `jwt:${user.id}`,
          JSON.stringify(jwtToken),
          "EX",
          30
        );
        await redis.set(
          `refresh:${user.id}`,
          jwtToken.refreshToken,
          "EX",
          7 * 24 * 60 * 60 // 7 days in seconds
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
