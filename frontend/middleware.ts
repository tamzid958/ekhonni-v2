import { NextRequest, NextResponse } from "next/server";
type Route = string;
type Role = 'SUPER_ADMIN' | 'ADMIN' | 'USER' | 'GUEST';

const PUBLIC_ROUTES: Route[] = [
  "/",
  "/auth/register",
  "/auth/forget-password",
  "/auth/reset-password",
  "/auth/reset-password/update",
  "/auth/verify-email",
  "/categoryProducts",
  "/labeledCategory",
  "/productDetails",
  "/search",
  "/seller-page",
] as const;

const AUTH_ROUTES = [
  "/auth/login",
  "/auth/logout",
] as const;

const ACCESS_LIST: Record<Role, Route[]> = {
  SUPER_ADMIN: ["*"],
  ADMIN: ["/admin"],
  USER: [
    "/myProducts",
    "/myProducts/bidList",
    "/productDetails",
    "/seller-page",
    "/user-page"
  ],
  GUEST: PUBLIC_ROUTES,
};

const isPublicRoute = (pathname: string): boolean => {
  return PUBLIC_ROUTES.includes(pathname as Route);
};

const hasAccess = (allowedPaths: Route[], pathname: string): boolean => {
  return (
    allowedPaths.includes(pathname as Route) ||
    allowedPaths.includes("*") ||
    allowedPaths.some((path) => pathname === path || pathname.startsWith(`${path}/`))
  );
};

const getSession = async (request: NextRequest) => {
  try {
    const response = await fetch(`${request.nextUrl.origin}/api/auth/session`, {
      headers: {
        cookie: request.headers.get("cookie") || "",
      },
    });

    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    console.error("Session fetch error:", error);
    return null;
  }
};

export async function middleware(request: NextRequest) {
  const pathname = request.nextUrl.pathname;
  const origin = request.nextUrl.origin;

  if (isPublicRoute(pathname)) {
    return NextResponse.next();
  }

  const session = await getSession(request);
  const userRole = session?.user?.role || "GUEST";
  const allowedPaths = ACCESS_LIST[userRole as Role] || [];

  if (pathname === "/auth/login") {
    return session ?
      NextResponse.redirect(`${origin}/`) :
      NextResponse.next();
  }

  if (!session || !session.user || !session.user.role) {
    return NextResponse.redirect(`${origin}/auth/login`);
  }

  switch (userRole) {
    case "SUPER_ADMIN":
      return NextResponse.next();

    case "ADMIN":
      return hasAccess(allowedPaths, pathname) ?
        NextResponse.next() :
        NextResponse.redirect(`${origin}/admin`);

    case "USER":
      return hasAccess(allowedPaths, pathname) ?
        NextResponse.next() :
        NextResponse.redirect(`${origin}/`);

    case "GUEST":
      return hasAccess(allowedPaths, pathname) ?
        NextResponse.next() :
        NextResponse.redirect(`${origin}/auth/login`);

    default:
      return NextResponse.redirect(`${origin}/`);
  }
}

export const config = {
  matcher: [
    "/((?!api|_next/static|_next/image|favicon.ico|.*\\.(?:svg|png|jpg|jpeg|gif|webp)$|auth/login|auth/logout|not-found).*)",
  ],
};