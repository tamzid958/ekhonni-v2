import {Button} from "./ui/button"
import {Input} from "./ui/input"
import {Bell, Search, ShoppingCart, User} from "lucide-react"
import React from "react";

type Props = {
    placeholder?: string,
}

export function NavBar({placeholder}: Props) {
    return (
        <nav className="flex justify-between p-4 text-2xl bg-brand-dark h-[120px] poppins.classname">
            <div className="font-bold ml-16 mt-2">
                <img src="frame.png" alt="logo" className="h-[75px]"/>
            </div>
            <div className="w-[700px] flex justify-center items-center">
                <Input
                    type="text"
                    placeholder={placeholder}
                    className="border-none placeholder:font-bold text-4xl leading-tight p-6"
                />
                <Button variant="custom" size="icon2">
                    <Search/>
                    <span className="sr-only">Search Button</span>
                </Button>
            </div>
            <div className="flex gap-4 mr-28 mt-4">
                <Button variant="custom" size="icon2" className="rounded-full"><ShoppingCart/></Button>
                <Button variant="custom" size="icon2" className="rounded-full"><Bell/></Button>
                <Button variant="custom" size="icon2" className="rounded-full"><User/></Button>
            </div>
        </nav>
    )
}