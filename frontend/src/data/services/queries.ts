import useSWR  from 'swr';

import {User} from '@/data/types/user';


export function useUser()
{
  return useSWR<User>("/user");
}
