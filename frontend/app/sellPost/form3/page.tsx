import React from 'react';
import {Button} from "@/components/ui/button";
import Link from "next/link";

export default function sellPostForm3() {
    return (
        <div>
            form3
            <Link href={'/sellPost/form4'}>
                <Button>
                    Sell Post
                </Button>
            </Link>
        </div>
    )
}