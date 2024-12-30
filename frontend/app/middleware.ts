import { NextResponse } from "next/server";
import { getToken } from "next-auth/jwt";


const protectedUrls = [
  {
    url: "/",
    access: ["Buyer/Seller","Guest", "Admin"],
  },
  {
    url: "/admin",
    access: ["Admin"],
  },
  {
    url: "/profile",
    access: ["Buyer/Seller", "Admin"],
  },
  {
    url: "/orders",
    access: ["Buyer/Seller"],
  },
  {
    url: "/guest-page",
    access: ["Guest", "Buyer/Seller", "Admin"],
  },
  {
    url: "/about",
    access: ["Guest", "Buyer/Seller", "Admin"],
  },
];

const checkAccess = (pathname, userRole) => {
  const foundRoute = protectedUrls.find((route) => pathname.startsWith(route.url));

  if (!foundRoute) {
    return false;
  }

  return foundRoute.access.includes(userRole);
};

export async function middleware(request) {
  const pathname = request.nextUrl.pathname;

  const token = await getToken({
    req: request,
    secret: process.env.NEXTAUTH_SECRET,
  });

  let userRole = "Guest";
  if (token) {
    userRole = token?.user?.role || "Unauthorized";
  }

  if (pathname === "/") {
    if (userRole === "Guest") {
      return NextResponse.redirect(new URL("/", request.url), { status: 303 });
    }
    return NextResponse.redirect(new URL("/dashboard", request.url), { status: 303 });
  }

  if (checkAccess(pathname, userRole)) {
    return NextResponse.next();
  }

  return NextResponse.redirect(new URL(`/not-found`, request.url), { status: 303 });
}

export const config = {
  matcher: [
    // Protect all routes except static assets, API routes, and public pages
    "/((?!api|_next/static|_next/image|favicon.ico|auth/login|auth/logout|not-found).*)",
  ],
};
