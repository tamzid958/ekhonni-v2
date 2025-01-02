import { NextResponse } from "next/server";

export async function GET() {
  return NextResponse.json({
    location: "Bangladesh",
    memberSince: "2024-11-18",
    profilePicture: "/prova.jpeg",
  });
}
