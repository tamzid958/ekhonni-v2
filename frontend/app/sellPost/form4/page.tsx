import React from 'react';
import {Button} from "@/components/ui/button";
import Link from "next/link";

export default function sellPostForm4() {
    return (
        <div>
            form4
            (confirmation page)
            <Link href={'/'}>
                <Button>
                    Home Page
                </Button>
            </Link>
        </div>
    )
}