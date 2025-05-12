// src/utils/request.ts
import axios from 'axios'

// 定义后端返回的响应结构
interface ApiResponse<T = any> {
  code: number
  message: string
  data: T | null
}

// 创建axios实例
const service = axios.create({
  baseURL: '', // 你的接口前缀
  timeout: 5000,
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    // 从localStorage获取token并添加到请求头
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    console.log('发送请求:', config.url, config.data)
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    console.log('接收原始响应:', response.config.url, response.data)
    // 返回响应数据
    return response.data as any
  },
  error => {
    console.error('响应错误:', error.config?.url, error.message)
    return Promise.reject(error)
  }
)

export default service
