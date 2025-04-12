// src/utils/request.ts
import axios, { type AxiosRequestConfig, type AxiosResponse } from 'axios'



const request = <T = any>(config: AxiosRequestConfig): Promise<AxiosResponse<T>> => {
  return axios(config)
}
const service = axios.create({
  baseURL: 'https://api.example.com', // 你的接口前缀
  timeout: 5000,
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 添加 token 等逻辑
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器
service.interceptors.response.use(
  response => response.data,
  error => Promise.reject(error)
)

export default service
