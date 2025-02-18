import { axiosInstance } from '@/data/services/fetcher';
import { toast } from 'sonner';

export const handleBlockUser = async (userId,reason, blockPolicy,  userToken) => {
  try{
    const response = await axiosInstance.post(
      'api/v2/admin/user/block',
      {
        id: userId,
        reason: reason,
        blockPolicy: blockPolicy
      },
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
      console.log("Failed to block users", response.data);
      toast("Failed to block users");
    }
  }
  catch (error) {
    console.error('Error blocking users:', error);
  }
}
export const handleUnblockUser = async (userId,  userToken) => {
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
      console.log("Failed to unblock users", response.data);
      toast("Failed to unblock users");
    }
  }
  catch (error) {
    console.error('Error unblocking users:', error);
  }
}