"use client"

import * as React from "react"

import {Button} from "@/components/ui/button"
import {Command, CommandEmpty, CommandGroup, CommandItem, CommandList,} from "@/components/ui/command"
import {Popover, PopoverContent, PopoverTrigger,} from "@/components/ui/popover"

type Status = {
    value: string
    label: string
}

const statuses: Status[] = [
    {
        value: "Default",
        label: "Default",
    },
    {
        value: "Price Low to High",
        label: "Price Low to High",
    },
    {
        value: "Price High to Low",
        label: "Price High to Low",
    },
]

export function ComboboxPopover() {
    const [open, setOpen] = React.useState(false)
    const [selectedStatus, setSelectedStatus] = React.useState<Status | null>(
        null
    )

    return (
        <div className="flex items-center space-x-4">
            <p className="text-sm text-muted-foreground">Filter</p>
            <Popover open={open} onOpenChange={setOpen}>
                <PopoverTrigger asChild>
                    <Button variant="custom" className="w-[150px] justify-start">
                        {selectedStatus ? <>{selectedStatus.label}</> : <>+ SORT BY PRICE</>}
                    </Button>
                </PopoverTrigger>
                <PopoverContent className="p-0 -mt-[51px]" side="bottom" align="start">
                    <Command>
                        <CommandList className="w-[150px]">
                            <CommandEmpty>No results found.</CommandEmpty>
                            <CommandGroup>
                                {statuses.map((status) => (
                                    <CommandItem
                                        key={status.value}
                                        value={status.value}
                                        onSelect={(value) => {
                                            setSelectedStatus(
                                                statuses.find((priority) => priority.value === value) ||
                                                null
                                            )
                                            setOpen(false)
                                        }}
                                    >
                                        {status.label}
                                    </CommandItem>
                                ))}
                            </CommandGroup>
                        </CommandList>
                    </Command>
                </PopoverContent>
            </Popover>
        </div>
    )
}
