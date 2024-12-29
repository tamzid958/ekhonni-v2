import {Button} from "./ui/button";
import {Bell, Search, ShoppingCart, User} from "lucide-react";
import * as React from "react";
import {cn} from "@/lib/utils";
import Link from 'next/link';
import {Select, SelectContent, SelectGroup, SelectItem, SelectLabel, SelectTrigger,} from "@/components/ui/select"

type Props = {
    placeholder?: string;
}

export function NavBar({placeholder}: Props) {
    return (
        <nav className="flex justify-between p-4 text-2xl bg-brand-dark h-[120px]">
            <div className="font-bold ml-16 mt-2">
                <Link href="/">
                    <img src="frame.png" alt="logo" className="h-[75px]"/>
                </Link>
            </div>

            <div className="w-[680px] flex justify-center items-center">
                <div className="w-full relative">
                    <input
                        type="text"
                        placeholder={placeholder}
                        className={cn(
                            "flex h-12 w-full rounded-md border border-input bg-background py-2 px-4 text-xl ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus:outline-none focus-visible:outline-none focus-visible:ring-0 focus-visible:ring-offset-0 disabled:cursor-not-allowed disabled:opacity-50",
                            "pr-6"
                        )}
                    />
                    <div className="absolute left-[92%] top-1/2 transform -translate-y-1/2">
                        <Button variant="custom2" size="customSize" className="w-[50%] h-[95%] rounded-xl">
                            <Search className="text-muted-foreground" size={18}/>
                            <span className="sr-only">Search Button</span>
                        </Button>
                    </div>
                </div>
            </div>
            <div className="flex gap-4 mr-28 mt-4">
                <Link href="/cart">
                    <Button variant="custom" size="icon2" className="rounded-full">
                        <ShoppingCart/>
                    </Button>
                </Link>
                {/*<Button variant="custom" size="icon2" className="rounded-full">*/}
                {/*        <Bell/>*/}
                {/*</Button>*/}

                <Select>
                    <SelectTrigger
                        className="text-primary bg-brand-mid hover:bg-brand-light h-12 w-12 px-3 rounded-full focus:ring-0 focus:outline-none active:ring-0 active:outline-none focus-visible:ring-0 focus-visible:outline-none ring-0 [&_svg.h-4]:hidden">
                        <Bell className="w-5 h-5"/>
                    </SelectTrigger>
                    <SelectContent>
                        <SelectGroup>
                            <SelectLabel>Notifications</SelectLabel>
                            <SelectItem value="update">System Update Available</SelectItem>
                            <SelectItem value="message">New Message from John</SelectItem>
                            <SelectItem value="reminder">Meeting Reminder: 2 PM</SelectItem>
                            <SelectItem value="offer">Exclusive Offer: 20% Off</SelectItem>
                            <SelectItem value="alert">Security Alert: Unusual Login Attempt</SelectItem>
                        </SelectGroup>
                    </SelectContent>
                </Select>

                <Button variant="custom" size="icon2" className="rounded-full"><User/></Button>

            </div>
        </nav>
    );
}
