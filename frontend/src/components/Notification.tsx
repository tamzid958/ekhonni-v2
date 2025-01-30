'use server';

export async function Notification(userID: string, userToken: string) {
  try {
    const response = await fetch(`http://localhost:8080/api/v2/user/${userID}/notifications`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${userToken}`,
      },
    });
    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Failed to fetch notifications: ${errorText}`);
    }
    const data = await response.json();
    return { success: true, message: 'Notification fetched successfully!', data };
  } catch (error) {
    console.error('Error fetching notification:', error);
    return { success: false, message: error.message };
  }
}
