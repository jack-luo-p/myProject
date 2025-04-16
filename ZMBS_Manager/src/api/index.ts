import request from '@/utils/request'

/**
 * 用户相关接口
 */
export const userApi = {
  /**
   * 用户登录
   * @param username 用户名
   * @param password 密码
   */
  login: (username: string, password: string) => {
    return request({
      url: '/api/user/login',
      method: 'post',
      data: { username, password }
    })
  },
  
  /**
   * 用户注册
   * @param username 用户名
   * @param password 密码
   */
  register: (username: string, password: string) => {
    return request({
      url: '/api/register',
      method: 'post',
      data: { username, password }
    })
  }
}

// 导出所有API
export default {
  user: userApi
} 
