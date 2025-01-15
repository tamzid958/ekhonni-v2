"use client";
import React, { useEffect } from 'react';

export default function MyBuggyComponent() {
  useEffect(() => {
    throw new Error('Error thrown in useEffect');
  }, []);

  return <div>Hello, I will throw an error right after mounting</div>;
}
