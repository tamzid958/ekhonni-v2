async function checkout(button) {
    const productId = button.getAttribute('data-product-id');
    const productName = button.getAttribute('data-product-name');

    const payload = {
        productId: productId,
        productName: productName
    };

    try {
        const response = await fetch('/ekhonni-v2/products/test/payment-initiate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        });

        console.log(response);

        if (response.ok) {
            const result = await response.json();
        
            if (result.gatewayPageURL) {
                window.location.href = result.gatewayPageURL;
            } else {
                alert('Failed to retrieve payment URL.');
            }
        } else {
            alert('Failed to checkout. Please try again.');
        }
    } catch (error) {
        console.error('Error during checkout:', error);
        alert('An error occurred. Please try again.');
    }
}
