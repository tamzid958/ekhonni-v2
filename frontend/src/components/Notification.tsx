'use server';

export async function NotificationGetter(userID: string, userToken: string, lastFetchTime: string) {
  console.info('the last fetch time is:::');
  //lastFetchTime = '2025-02-01T08:06:41';
  console.log(lastFetchTime);
  try {
    if (!lastFetchTime) {
      throw new Error('lastFetchTime is required and must be in the format: yyyy-MM-ddTHH:mm:ss');
    }
    // Construct the URL with the formatted lastFetchTime
    const response = await fetch(`http://localhost:8080/api/v2/user/${userID}/notifications?lastFetchTime=${lastFetchTime}`, {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${userToken}`,
        'Content-Type': 'application/json',
      },
    });


    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Failed to fetch notifications: ${errorText}`);
    }
    const data = await response.json();

    // if(data.message.length > 0) {
    //
    // }

    return {
      success: true,
      message: 'Notification fetched successfully!',
      data: data.data,
      lastFetchTime: new Date().toISOString().split('.')[0], // Assuming API returns a new lastFetchTime
    };
  } catch (error) {
    console.error('Error fetching notification:', error);
    return { success: false, message: error.message };
  }
}