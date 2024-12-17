import Image from 'next/image';
import { Button } from '@/components/ui/button';
import { Alert, AlertDescription, AlertTitle } from '@/components/ui/alert';

import React from 'react';

export default function Home() {
  return (
    <div className="flex mt-6 justify-center align-middle min-h-screen">
      This is Homepage
      {/*<Button>Hello</Button>*/}
      {/*<Alert> New Alert</Alert>*/}
    </div>
  );
}
