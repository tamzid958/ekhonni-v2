import Image from 'next/image';
import { Button } from '@/components/ui/button';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert';

import React from 'react';
import CustomErrorBoundary from '@/components/ErrorBoundary';


export default function Home() {
  return (
    <div className="flex-col items-center justify-center min-h-screen">

      <div className="flex align-item justify-center ">
        <CustomErrorBoundary>

            This is Homepage

        </CustomErrorBoundary>
      </div>
    </div>
  );
}
