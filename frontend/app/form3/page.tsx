'use client';
import { useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { formSchema } from '@/schemas/formSchema';
import FormPage1 from '@/components/FormPage1';
import FormPage2 from '@/components/FormPage2';
import FormPage3 from '@/components/FormPage3';
import ConfirmationPage from '@/components/ConfirmationPage';

export default function Home() {
  const [step, setStep] = useState(1);
  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      productName: '',
      productDescription: '',
    },
  });
  const formValues = form.getValues();

  const nextStep = async () => {
    const stepFields: Record<number, string[]> = {
      1: ['productName',
        'productDescription',
        'productCondition',
        'productLocation'],
      2: ['basePrice'],
      3: ['images'],
      // Add more steps and their corresponding fields as needed
    };
    const currentStepFields = stepFields[step];
    const isValid = await form.trigger(currentStepFields);

    if (isValid) {
      setStep((prev) => prev + 1);
    }
  };

  const prevStep = () => setStep((prev) => Math.max(prev - 1, 1));
  const handleSubmit = form.handleSubmit((values) => console.log('Form Submitted:', values));

  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24 bg-brand-bright">
      <FormProvider {...form}>
        <form
          onSubmit={form.handleSubmit(handleSubmit)}
          className="max-w-xl w-full flex flex-col gap-4 bg-white p-8"
        >
          {step === 1 && <FormPage1 nextStep={nextStep} />}
          {step === 2 && <FormPage2 nextStep={nextStep} prevStep={prevStep} />}
          {step === 3 && <FormPage3 nextStep={nextStep} prevStep={prevStep} />}
          {step === 4 && <ConfirmationPage formValues={formValues} prevStep={prevStep} handleSubmit={handleSubmit} />}
        </form>
      </FormProvider>
    </main>
  );
}
