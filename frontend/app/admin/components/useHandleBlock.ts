import { axiosInstance } from '@/data/services/fetcher';
import { toast } from 'sonner';

export const handleBlcokUser = async (userId, userToken) => {
  try{
    const response = await axiosInstance.post(
      'api/v2/admin/user/block',
      {id: userId},
      {
        headers: {
          Authorization: `Bearer ${userToken}`,
        }
      },

    );

    if(response.status == 200)
    {
      console.log("User blocked successfully", response.data);
      toast("User blocked successfully");
    }
    else
    {
      console.log("Failed to block user", response.data);
      toast("Failed to block user");
    }
  }
  catch (error) {
    console.error('Error blocking user:', error);
  }
}
export const handleUnblcokUser = async (userId, userToken) => {
  try{
    const response = await axiosInstance.post(
      'api/v2/admin/user/unblock',
      {id: userId},
      {
        headers: {
          Authorization: `Bearer ${userToken}`,
        }
      },

    );

    if(response.status == 200)
    {
      console.log("User Unblocked Successfully", response.data);
      toast("User Unblocked Successfully");
    }
    else
    {
      console.log("Failed to unblock user", response.data);
      toast("Failed to unblock user");
    }
  }
  catch (error) {
    console.error('Error unblocking user:', error);
  }
}