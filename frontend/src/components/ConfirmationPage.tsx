import React from 'react';
import { Button } from '@/components/ui/button';

export default function ConfirmationPage({ formValues, prevStep, handleSubmit }: {
  formValues: any;
  prevStep: () => void;
  handleSubmit: () => void
}) {
  return (
    <div className={'space-y-4'}>
      <div className="w-full max-w-2xl mx-auto bg-white p-6 rounded-lg ">
        <h1 className="font-bold text-center text-3xl mb-6 text-gray-800">
          Confirmation Page
        </h1>
        <div className="space-y-4">
          {Object.entries(formValues).map(([key, value]) => (
            <div
              key={key}
              className="flex justify-between items-center bg-gray-100 p-4 rounded-md shadow-sm"
            >
          <span className="font-medium text-gray-700 capitalize">
            {key.replace(/([A-Z])/g, ' $1')}:
          </span>
              <span className="text-gray-900">
            {Array.isArray(value)
              ? value.length > 0
                ? `${value.length} item(s) uploaded`
                : 'No items'
              : value?.toString() || 'N/A'}
          </span>
            </div>
          ))}
        </div>
        <div className="flex gap-4 pt-4">
          <Button onClick={prevStep} type="button" className="w-full">
            Back
          </Button>
          <Button type="submit" className="w-full" onClick={handleSubmit}>
            Submit
          </Button>
        </div>
      </div>
    </div>
  );
}
