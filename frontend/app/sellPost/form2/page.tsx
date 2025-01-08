import React from 'react';
import {Button} from "@/components/ui/button";
import Link from "next/link";

export default function sellPostForm2() {
    return (
        <div>
            form2
            <Link href={'/sellPost/form3'}>
                <Button>
                    Sell Post
                </Button>
            </Link>
        </div>
    )
}