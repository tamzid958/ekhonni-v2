import { NextRequest, NextResponse } from "next/server";
import { allRolesList } from "@/hooks/useRoles";

console.log("🛡️ Middleware Loaded"); // Check if middleware is running

const publicRoutes = [
  "/",
  "/auth/register",
  "/auth/login",
  "/auth/forget-password",
  "/auth/reset-password",
  "/auth/reset-password/update",
  "/auth/verify-email",
  "/categoryProducts",
  "/labeledCategory",
  "/productDetails",
  "/search",
  "/seller-page",
];

// ✅ Fix: Allow all roles to access their pages dynamically
const accessList = {
  USER: ["/profile", "/bids", "/guest-page", "/seller-profile"],
  ADMIN: ["/admin", "/profile", "/guest-page", "/seller-profile"],
  GUEST: ["/guest-page", "/seller-profile"],
  SUPER_ADMIN: ["*"], // ✅ Fix: SUPER_ADMIN should access all pages
};

// ✅ Normalize roles
const normalizedAccessList = allRolesList.reduce((acc, role) => {
  acc[role] = accessList[role] || [];
  return acc;
}, {});

export async function middleware(request: NextRequest) {
  console.log("🛡️ Middleware function triggered for:", request.nextUrl.pathname);

  const pathname = request.nextUrl.pathname;

  // ✅ Allow public routes
  if (publicRoutes.some((route) => pathname.startsWith(route))) {
    console.log("✅ Public route:", pathname);
    return NextResponse.next();
  }

  // ✅ Fetch user session with error handling
  let session = null;
  try {
    const response = await fetch(`${request.nextUrl.origin}/api/auth/session`, {
      headers: { cookie: request.headers.get("cookie") || "" },
    });

    if (response.ok) {
      session = await response.json();
    } else {
      console.warn("⚠️ Failed to fetch session:", response.status);
    }
  } catch (error) {
    console.error("❌ Error fetching session:", error);
  }
  console.log("Middleware session", session)

  const userRole = session?.user?.role || "GUEST";
  console.log("Detected Role:", userRole);

  const allowedPaths = normalizedAccessList[userRole] || [];

  switch (true) {
    case userRole === "SUPER_ADMIN":
      console.log("✅ SUPER_ADMIN Access Granted!");
      return NextResponse.next();

    case allowedPaths.includes("*") || allowedPaths.some((path) => pathname.startsWith(path)):
      console.log("✅ Access Granted for:", userRole);
      return NextResponse.next();

    default:
      console.log("❌ Unauthorized. Redirecting...");
      return NextResponse.redirect(new URL(allowedPaths[0] || "/login", request.url));
  }
}

// ✅ Fix: Ensure middleware does not block public routes & images
export const config = {
  matcher: [
    "/((?!api|_next/static|_next/image|favicon.ico|public/|auth/|categoryProducts|labeledCategory|productDetails|search|seller-page).*)",
  ],
};
