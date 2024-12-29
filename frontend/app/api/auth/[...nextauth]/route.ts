import NextAuth from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import GoogleProvider from "next-auth/providers/google";

const users = [
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

const handler = NextAuth({
  providers: [
    CredentialsProvider({
      name: "Credentials",
      credentials: {
        email: { label: "Email", type: "email" },
        password: { label: "Password", type: "password" },
      },
      async authorize(credentials) {
        const user = users.find(
          (u) => u.email === credentials?.email && u.password === credentials?.password
        );
        if (!user) {
          throw new Error("Invalid email or password.");
        }
        return {
          id: user.id,
          email: user.email,
          name: `${user.firstName} ${user.lastName}`,
        };
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
    signIn: "/auth/login", // Use your custom login page
  },
  callbacks: {

    async redirect({ url, baseUrl }) {

      return baseUrl;
    },

    async jwt({ token, user }) {
      if (user) {
        token.id = user.id;
        token.email = user.email;
        token.name = user.name;
      }

      return token;
    },
    async session({ session, token }) {

      session.user = {
        id: token.id,
        email: token.email,
        name: token.name,
        image: token.picture,
      };

      return session;
    },
  },
});

export { handler as GET, handler as POST };
