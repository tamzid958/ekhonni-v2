import axios from 'axios';

export const axiosInstance = axios.create({
  baseURL: '192.168.68.217:9090',
  headers: {
    'Content-Type': 'application/json',
  },
});

const fetcher = (url:string) => axiosInstance.get(url).then((res) => res.data);

export default fetcher;




