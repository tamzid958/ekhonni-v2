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
import { submitProduct } from './submitProduct';
import { useSession } from 'next-auth/react';

export default function Home() {
  const [step, setStep] = useState(0); // Start with step 0 for category selection
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [selectedSubCategory, setSelectedSubCategory] = useState(null);

//for notification console
  const { data: session, status } = useSession();
  const userId = session?.user?.id;
  const userToken = session?.user?.token;
  // fetch(`http://localhost:8080/api/v2/user/${userId}/notifications`, {
  //   method: 'GET',
  //   headers: {
  //     'Authorization': `Bearer ${userToken}`,
  //     'Content-Type': 'application/json',
  //   },
  // })
  //   .then(response => response.json())
  //   .then(data => console.log(data)) // Log the fetched notifications
  //   .catch(error => console.error('Error fetching notifications:', error));
  // console.log(userId);
  // console.log(userToken);
// for notification console


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
      basePrice: '',
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

  const handleSubmit = form.handleSubmit(async (values) => {
    console.log('Form Submitted:', values);


    const formData = new FormData();
    formData.append('title', values.productName);
    formData.append('subTitle', values.productSubTitle);
    formData.append('description', values.productDescription);
    formData.append('price', values.basePrice);
    formData.append('division', values.productLocation);
    formData.append('address', values.productLocationDescription);
    formData.append('condition', values.productCondition);
    formData.append('conditionDetails', values.productConditionDescription);
    formData.append('category', values.category);

    values.images.forEach((file) => {
      formData.append('images', file);
    });

    const result = await submitProduct(formData, userToken);

    if (result.success) {
      console.log(result.message);
      window.location.href = '/';
    } else {
      console.error(result.message);
    }
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