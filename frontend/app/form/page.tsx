'use client';
import { useState } from 'react';
import { FormProvider, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { formSchema } from '@/schemas/formSchema';
import FormPage1 from '@/components/FormPage1';
import FormPage2 from '@/components/FormPage2';
import FormPage3 from '@/components/FormPage3';
import ConfirmationPage from '@/components/ConfirmationPage';
import CategorySelector from '@/components/CategorySelector';
import { useSession } from 'next-auth/react';

export default function Home() {
  const [step, setStep] = useState(0); // Start with step 0 for category selection
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedSubCategory, setSelectedSubCategory] = useState(null);

  const { data: session, status } = useSession();
  // console.log(session.user.token);

  const form = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      productName: '',
      productSubTitle: '',
      productLocationDescription: '',
      productConditionDescription: '',
      productDescription: '',
      category: '',
      subCategory: '',
    },
  });

  const nextStep = async () => {
    const stepFields: Record<number, string[]> = {
      0: ['category', 'subCategory'], // Validate category and subcategory for step 0
      1: ['productName', 'productSubTitle', 'productDescription', 'productLocation', 'productLocationDescription'],
      2: ['basePrice', 'productCondition', 'productConditionDescription'],
      3: ['images'],
    };
    const currentStepFields = stepFields[step];
    const isValid = await form.trigger(currentStepFields);

    if (isValid) {
      setStep((prev) => prev + 1);
    }
  };

  const prevStep = () => setStep((prev) => Math.max(prev - 1, 0));

  const handleSubmit = form.handleSubmit((values) => {
    console.log('Form Submitted:', values);
  });

  return (
    <main className="flex min-h-screen flex-col items-center justify-between p-24 bg-brand-bright">
      <FormProvider {...form}>
        <form
          onSubmit={handleSubmit}
          className="max-w-xl w-full flex flex-col gap-4 bg-white p-8"
        >
          {step === 0 && (
            <CategorySelector
              onCategorySelect={(value) => form.setValue('category', value)}
              onSubCategorySelect={(value) => form.setValue('subCategory', value)}
              nextStep={nextStep}
            />
          )}
          {step === 1 && <FormPage1 nextStep={nextStep} prevStep={prevStep} />}
          {step === 2 && <FormPage2 nextStep={nextStep} prevStep={prevStep} />}
          {step === 3 && <FormPage3 nextStep={nextStep} prevStep={prevStep} />}
          {step === 4 &&
            <ConfirmationPage formValues={form.getValues()} prevStep={prevStep} handleSubmit={handleSubmit} />}
        </form>
      </FormProvider>
    </main>
  );
}