"use client"

import * as React from "react"
import * as SliderPrimitive from "@radix-ui/react-slider"

import {cn} from "@/lib/utils"

const Slider = React.forwardRef<
    React.ElementRef<typeof SliderPrimitive.Root>,
    React.ComponentPropsWithoutRef<typeof SliderPrimitive.Root>
>(({className, ...props}, ref) => (
    <SliderPrimitive.Root
        ref={ref}
        className={cn(
            "relative flex w-full touch-none select-none items-center",
            className
        )}
        {...props}
    >
        <SliderPrimitive.Track className="relative h-2 w-full grow overflow-hidden rounded-full bg-secondary">
            <SliderPrimitive.Range className="absolute h-full bg-primary"/>
        </SliderPrimitive.Track>
        {props.defaultValue?.map((_, index) => (
            <SliderPrimitive.Thumb
                key={index}
                className="block h-5 w-5 rounded-full border-2 border-primary bg-background ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50"
            />
        ))}
    </SliderPrimitive.Root>
))
Slider.displayName = SliderPrimitive.Root.displayName

type SliderProps = React.ComponentProps<typeof Slider>

export function SliderDemo({className, ...props}: SliderProps) {
    // Explicitly type the state as a tuple of two numbers
    const [value, setValue] = React.useState<[number, number]>([0, 100000])

    const handleValueChange = (newValue: [number, number]) => {
        setValue(newValue)
    }

    return (
        <div className="relative w-full">
            <Slider
                value={value}
                onValueChange={handleValueChange}
                min={0}
                max={100000}
                step={1} // Adjust step for more granularity
                defaultValue={[0, 100000]} // Initial min and max
                className={cn("w-[60%]", className)}
                {...props}
            />

            {/* Displaying the numbers with BDT */}
            <div className="flex justify-between w-[60%] mt-2">
                <span>{value[0]} BDT</span> {/* Min value */}
                <span>{value[1]} BDT</span> {/* Max value */}
            </div>
        </div>
    )
}
