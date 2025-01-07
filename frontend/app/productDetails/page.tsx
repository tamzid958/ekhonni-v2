"use client"
import React, {useEffect, useState} from 'react';
import {Button} from "@/components/ui/button";

export default function productDetails() {
    const [count, setCount] = useState(0);

    useEffect(() => {
        console.log('mounted');
    }, [count])

    return (
        <div className="space-y-6 container mx-auto my-4 px-4">
            PRODUCT DETAILS
            <Button onClick={() => {
                setCount(count => count + 1)
            }}>click it {count}</Button>
        </div>
    );
}
