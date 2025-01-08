"use client"

import {Button} from '@/components/ui/button';
import Link from "next/link";
import React, {useState} from 'react';

export default function sellPost() {
    const [formstate, setFormstate] = useState(0);
    return (
        <div>
            <Link href={'/sellPost/form1'}>
                <Button>
                    Sell Post
                </Button>
            </Link>
        </div>
    )
}