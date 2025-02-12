import NextAuth, { NextAuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GoogleProvider from "next-auth/providers/google";
import { axiosInstance } from "@/data/services/fetcher";

const refreshAccessToken = async ({ accessToken, refreshToken, id }) => {
  try {
    console.log("Refreshing token...");
    const headers = {
      Authorization: `Bearer ${accessToken}`,
      "Content-Type": "application/json",
    };

    const res = await axiosInstance.post(
      `/api/v2/user/${id}/refresh-token`,
      { refreshToken },
      { headers }
    );
    console.log("New Refreshed Token:", res.data);

    if (!res.data.data || !res.data.data.accessToken || !res.data.data.refreshToken) {
      throw new Error("Invalid response from token refresh endpoint");
    }

    console.log("New Refreshed Token:", res.data);

    return res.data.data;
  } catch (error) {
    console.error("Error refreshing access token:", error);
    return { error: "RefreshAccessTokenError" };
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
        const { email, password } = credentials;

        if (email && password) {
          try {
            const response = await axiosInstance.post("/api/v2/auth/sign-in", {
              email,
              password,
            });
            if (response.status === 200) {
              const res = response.data;
              console.log("Login successful:", res);

              return {
                id: res.id,
                role: res.role,
                accessToken: res.authToken.accessToken,
                refreshToken: res.authToken.refreshToken,
              };
            }
          } catch (error) {
          if (error?.status === 404) {
            console.error("Email Not Verified, Please verify.");
            throw new Error("Email Not Verified, Please verify.");
            }
          else{
            console.error("Error logging in:", error);
            throw new Error("Invalid email or password");
          }}
        }

        return null;
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
    async jwt({ token, user, account }) {
      if (user && account) {
        return {
          id: user.id,
          role: user.role,
          refreshToken: user.refreshToken,
          accessToken: user.accessToken,
          accessTokenExpires: Date.now() + 30 * 1000, // 30 seconds
        };
      }
      if (Date.now() < token.accessTokenExpires - 5 * 1000) {
        console.log("Access token is still valid.");
        return token;
      }

      console.log("Access token is about to expire, refreshing...");
      const refreshedToken = await refreshAccessToken({
        accessToken: token.accessToken,
        refreshToken: token.refreshToken,
        id: token.id,
      });

      if (refreshedToken?.error) {
        console.error("Failed to refresh token:", refreshedToken.error);
        return { ...token, error: refreshedToken.error };
      }

      return {
        id: token.id,
        role: token.role,
        refreshToken: refreshedToken.refreshToken,
        accessToken: refreshedToken.accessToken,
        accessTokenExpires: Date.now() + 30 * 1000, // 30 seconds
      };
    },

    async session({ session, token }) {
      session.user.id = token.id;
      session.user.role = token.role;
      session.user.token = token.accessToken;
      return session;
    }
  },
};

const handler = NextAuth(options);

export { handler as GET, handler as POST };
