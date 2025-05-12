import request from '@/utils/request'

// 定义后端返回的响应结构
interface ApiResponse<T = any> {
  code: number
  message: string
  data: T | null
}

// 添加登录响应类型
interface LoginResponse {
  token: string
  userInfo?: {
    username: string
    id: number
    [key: string]: any
  }
}

export default {
  async login(username: string, password: string): Promise<ApiResponse<LoginResponse>> {
    try {
      // 注意：request拦截器已经通过as any转换了类型
      const res = await request.post<ApiResponse<LoginResponse>>('/api/user/login', {
        username,
        password
      })
      
      console.log('登录响应处理:', res)
      
      // 检查响应状态码
      if (res.code !== 200) {
        throw new Error(res.message || '登录失败')
      }
      
      return res as ApiResponse<LoginResponse>
    } catch (error) {
      console.error('登录请求异常:', error)
      throw error
    }
  },
  
  async register(username: string, password: string, confirmPassword: string): Promise<ApiResponse<any>> {
    try {
      const res = await request.post<ApiResponse>('/api/user/register', {
        username,
        password,
        confirmPassword
      })
      
      // 检查响应状态码
      if (res.code !== 200) {
        throw new Error(res.message || '注册失败')
      }
      
      return res as ApiResponse<any>
    } catch (error) {
      console.error('注册请求异常:', error)
      throw error
    }
  },
  
  async logout(): Promise<ApiResponse<any>> {
    try {
      const res = await request.post<ApiResponse>('/api/user/logout')
      
      // 检查响应状态码
      if (res.code !== 200) {
        throw new Error(res.message || '退出登录失败')
      }
      
      return res as ApiResponse<any>
    } catch (error) {
      console.error('退出登录请求异常:', error)
      throw error
    }
  }
} 