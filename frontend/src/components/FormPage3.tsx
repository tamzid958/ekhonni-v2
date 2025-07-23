import { useFormContext } from 'react-hook-form';
import { FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Trash2 } from 'lucide-react';
import { Button } from '@/components/ui/button';

export default function FormPage2({ nextStep, prevStep }: { nextStep: () => void; prevStep: () => void }) {
  const { control } = useFormContext();

  return (
    <>
      <h1 className="font-bold text-center text-3xl">Images </h1>

      <FormField
        control={control}
        name="images"
        render={({ field }) => (
          <FormItem>
            <FormLabel>Product Images</FormLabel>
            <div className="grid grid-cols-3 gap-4">
              {Array.from({ length: 7 }).map((_, index) => {
                const names = ['Front', 'Back', 'Left Side', 'Right side', 'Details', 'Damage', 'Other'];
                const name = names[index];

                return (
                  <div
                    key={index}
                    className={`${
                      index === 0 ? 'col-span-3' : 'col-span-1'
                    } border border-dashed border-gray-300 rounded-md p-2 flex items-center justify-center`}
                  >
                    <div className="relative w-full h-32">
                      <Input
                        type="file"
                        accept="image/*"
                        onChange={(e) => {
                          const files = e.target.files;
                          if (files?.[0]) {
                            const updatedImages = [...(field.value || [])];
                            updatedImages[index] = files[0];
                            field.onChange(updatedImages);
                          }
                        }}
                        className="absolute inset-0 cursor-pointer w-full h-full opacity-0 z-10"
                        ref={(el) => {
                          if (el && !field.value?.[index]) {
                            el.value = '';
                          }
                        }}
                      />
                      {field.value?.[index] && (
                        <>
                          <img
                            src={URL.createObjectURL(field.value[index])}
                            alt={`Preview ${index + 1}`}
                            className="absolute inset-0 object-cover w-full h-full rounded-md z-20"
                          />
                          <button
                            type="button"
                            onClick={() => {
                              const updatedImages = [...(field.value || [])];
                              updatedImages[index] = null;
                              field.onChange(updatedImages);
                            }}
                            className="absolute top-2 right-2 p-1 bg-white rounded-full shadow-md z-20"
                          >
                            <Trash2 size={20} />
                          </button>
                        </>
                      )}
                      <div className="absolute inset-0 flex items-center justify-center z-0">
                        <span className="text-gray-500 ">{name}</span> {/* Display custom name */}
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>
            <FormMessage />
          </FormItem>
        )}
      />
      <div className="flex gap-4">
        <Button onClick={prevStep} type="button" className="w-full">
          Back
        </Button>
        <Button
          onClick={nextStep}
          type="button"
          className="w-full"
        >
          Next
        </Button>

      </div>
    </>
  );
}
