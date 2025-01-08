import React from 'react';
import {Button} from "@/components/ui/button";
import Link from "next/link";

export default function sellPostForm1() {
    return (
        <div>
            form1
            <Link href={'/sellPost/form2'}>
                <Button>
                    Sell Post
                </Button>
            </Link>
        </div>
    )
}