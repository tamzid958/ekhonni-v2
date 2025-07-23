'use client';

import * as React from 'react';
import * as SliderPrimitive from '@radix-ui/react-slider';
import { cn } from '@/lib/utils';

const Slider = React.forwardRef<
  React.ElementRef<typeof SliderPrimitive.Root>,
  React.ComponentPropsWithoutRef<typeof SliderPrimitive.Root>
>(({ className, ...props }, ref) => (
  <SliderPrimitive.Root
    ref={ref}
    className={cn('relative flex w-full touch-none select-none items-center', className)}
    {...props}
  >
    <SliderPrimitive.Track className="relative h-2 w-full grow overflow-hidden rounded-full bg-secondary">
      <SliderPrimitive.Range className="absolute h-full bg-primary" />
    </SliderPrimitive.Track>
    {props.value?.map((_, index) => (
      <SliderPrimitive.Thumb
        key={index}
        className="block h-5 w-5 rounded-full border-2 border-primary bg-background ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50"
      />
    ))}
  </SliderPrimitive.Root>
));
Slider.displayName = SliderPrimitive.Root.displayName;

type SliderProps = React.ComponentProps<typeof Slider>;

export function SliderDemo({
                             className,
                             value,
                             setValue,
                             onFilter,
                             ...props
                           }: SliderProps & {
  value: [number, number];
  setValue: React.Dispatch<React.SetStateAction<[number, number]>>;
  onFilter: (value: [number, number]) => void;
}) {
  const [tempValue, setTempValue] = React.useState<[number, number]>(value);

  const handleValueChange = (newValue: [number, number]) => {
    setTempValue(newValue); // Update temp value while sliding
  };

  const handleFilterClick = () => {
    setValue(tempValue); // Apply selected values
    onFilter(tempValue);
  };

  return (
    <div className="relative w-full">
      <Slider
        value={tempValue}
        onValueChange={handleValueChange}
        min={0}
        max={100000}
        step={1}
        className={cn('w-[100%]', className)}
        {...props}
      />

      {/* Show temp values while sliding */}
      <div className="flex justify-between w-[100%] mt-2 text-sm font-semibold text-gray-800">
        <span>Minimum : {tempValue[0]} BDT</span>
        <span>Maximum : {tempValue[1]} BDT</span>
      </div>


      {/* Filter Button */}
      <button
        onClick={handleFilterClick}
        className="mt-4 bg-primary text-white p-2 rounded"
      >
        Filter by Price
      </button>
    </div>
  );
}
