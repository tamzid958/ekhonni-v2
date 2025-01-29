
'use client';

import  React from 'react';

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { signIn } from 'next-auth/react';
import { FcGoogle } from 'react-icons/fc';
import { useRouter } from "next/navigation";
import { z } from 'zod';
import { SubmitHandler, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';


const loginSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(6, "Password must be at least 6 characters"),
});

type LoginFormValues = z.infer<typeof loginSchema>;


export function LoginForm({ className, ...props }: React.ComponentProps<"div">) {


  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  });

  const onSubmit: SubmitHandler<LoginFormValues> = async (data) => {
    try {
      const result = await signIn("credentials", {
        redirect: false,
        email: data.email,
        password: data.password,
      });

      if (result?.ok) {
        alert("Login successful!");

        router.push("/");
      } else {
        alert(result?.error || "Invalid email or password");
      }
    } catch (err) {
      console.error("Login error:", err);
      alert("Something went wrong. Please try again.");
    }
  };

  const router = useRouter();
  return (

    <div className={cn("flex flex-col gap-6 ",  className)} {...props}>
      <Card className="overflow-hidden shadow-3xl border-gray-500">
        <CardContent className="grid p-0 md:grid-cols-2">
          <form onSubmit={handleSubmit(onSubmit)} className="p-6 md:p-8">
            <div className="flex flex-col gap-6">
              <div className="flex flex-col items-center text-center">
                <h1 className="text-2xl font-bold">Welcome back</h1>
                <p className="text-balance text-muted-foreground">Login to Ekhonni.com</p>
              </div>
              <div className="grid gap-2">
                <Label htmlFor="email">Email</Label>
                <Input
                  type="email"
                  {...register("email")}
                  placeholder="Enter your email"
                />
                {errors.email && (
                  <p className="text-sm text-red-600">{errors.email.message}</p>
                )}
              </div>
              <div className="grid gap-2">
                <div className="flex items-center">
                  <Label htmlFor="password">Password</Label>
                  <div onClick={() => {
                    router.push('/auth/forget-password')
                  }} className="ml-auto text-sm underline underline-offset-2 cursor-pointer hover:text-blue-600 transition">
                    <p> Forget Password? </p>
                  </div>
                </div>
                <Input
                  type="password"
                  {...register("password")}
                  placeholder="Enter your password"

                />
                {errors.password && (
                  <p className="text-sm text-red-600">{errors.password.message}</p>
                )}
              </div>
              <Button type="submit" className="w-full">
                Login
              </Button>
              <div className="relative text-center text-sm after:absolute after:inset-0 after:top-1/2 after:z-0 after:flex after:items-center after:border-t after:border-border">
                <span className="relative z-10 bg-background px-2 text-muted-foreground">Or</span>
              </div>
              <Button
                variant="outline"
                className="mt-4 w-full  flex items-center justify-center gap-2"
                onClick={() => signIn("google")}
                aria-label="Sign in with Google"
              >
                <FcGoogle className="text-xl" />
                Sign in with Google
              </Button>

              <div className="text-center text-sm">
                Don&apos;t have an account?{" "}
                <span onClick={() => router.push('/auth/register')} className="ml-auto text-sm underline underline-offset-2 cursor-pointer hover:text-blue-600 transition">
                  Sign up
                </span>
              </div>
            </div>
          </form>
          <div className="relative hidden bg-muted md:block">
            <img
              src="/auction.jpg"
              alt="Image"
              className="absolute inset-0 h-full w-full object-cover dark:brightness-[0.2] dark:grayscale"
            />
          </div>
        </CardContent>
      </Card>
      <div className="text-balance text-blue-950 text-center text-xs text-muted-foreground [&_a]:underline [&_a]:underline-offset-4 hover:[&_a]:text-primary">
        By clicking continue, you agree to our <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a>.
      </div>
    </div>
  )
}

