'use server';

export async function submitProduct(formData: FormData, userToken: string) {
  try {
    const response = await fetch('http://localhost:8080/api/v2/product', {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${userToken}`,
      },
      body: formData,
    });
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Failed to submit product: ${errorText}`);
    }

    return { success: true, message: 'Product submitted successfully!' };
  } catch (error) {
    console.error('Error submitting product:', error);
    return { success: false, message: error.message };
  }
}
