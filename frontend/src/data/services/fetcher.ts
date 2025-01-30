
import axios from "axios";


export const axiosInstance = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL || "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
});


const fetcher = (url: string, token?: string) => {

  return axiosInstance.get(url, {
    headers: token ? { Authorization: `Bearer ${token}` } : {},
  }).then((res) => res.data);
};

export default fetcher;
