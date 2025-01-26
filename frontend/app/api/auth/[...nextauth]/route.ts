import NextAuth, { NextAuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GoogleProvider from "next-auth/providers/google";
import {redis} from '@/lib/redis';
import { User } from '@/data/types/user';
import axios from 'axios';
import { axiosInstance } from '@/data/services/fetcher';
import  jwtDecode  from 'jwt-decode';


const refreshAccessToken = async ({accessToken, refreshToken, id}) => {
  try {
    console.log("Refreshing token...");
    const headers = {
      Authorization: `Bearer ${accessToken}`,
    }
    const res = await axiosInstance.post(`/api/v2/user/${id}/refresh-token/`,
      { refreshToken: refreshToken},
      { headers: headers });

     const  newRefreshedToken = res.data;

     console.log("New Refreshed Token:", newRefreshedToken);

    return newRefreshedToken;
  } catch (error) {
    console.error("Error refreshing access token:", error);
    return {
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
          if ( response.status === 200) {
            const res = response.data;

            console.log("Login successful:", res);

            const user = {
              id: res.id,
              email: res.email,
              name: res.name,
              image: res.image,
              accessToken: res.authToken.accessToken,
              refreshToken: res.authToken.refreshToken,
            }

            return user;
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

    async jwt({ token, user, account }) {
      if (user && account) {
        const token = {
          id: user.id,
          email: user.email,
          name: user.name,
          refreshToken: user?.refreshToken,
          accessToken: user?.accessToken,
          picture: user.image,
          accessTokenExpires: Date.now() + 30 * 1000,
        };
        await redis.set(
          `jwt:${token.id}`,
          JSON.stringify(token),
          "EX",
          30
        );

        return token;
      }

      if (Date.now() < (token.accessTokenExpires as number)) {
        return token;
      }

        const redisToken = await redis.get(`jwt:${token.id}`);

        const currentToken = JSON.parse(redisToken);
        const refreshedToken = await refreshAccessToken({
          accessToken: currentToken?.accessToken,
          refreshToken: currentToken?.refreshToken,
          id:  token.id,
        });


        if (refreshedToken) {

        const retrievedToken = JSON.parse(refreshedToken);

        const newToken = {
          id: user.id,
          email: user.email,
          name: user.name,
          refreshToken: retrievedToken.refreshToken,
          accessToken: retrievedToken.accessToken,
          picture: user.image,
          accessTokenExpires: Date.now() + 30 * 1000,
        };

        await redis.set(
            `jwt:${newToken.id}`,
            JSON.stringify(newToken),
            "EX",
            30
          );
        return newToken;
      }
    },
    async session({ session, token }) {
      session.user = {
        token: token.accessToken,
        id: token.id,
        email: token.email,
        name: token.name,
        image: token.picture || null,
      };
      console.log("Session:", session);
      return session;
    },
  },
};

const handler = NextAuth(options);

export { handler as GET, handler as POST };
